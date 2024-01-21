package chessModel;

import app.c_e.engine.Engine;
import app.c_e.themes.StandardTheme;
import app.c_e.themes.SwagTheme;
import app.c_e.themes.Theme;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static chessModel.KingMoveTracker.*;

public class ChessboardController {
    @FXML
    public GridPane chessboardGrid;
    @FXML
    public Button whiteKingButton;
    @FXML
    public Button blackKingButton;
    @FXML
    public ImageView whiteKing;
    @FXML
    public ImageView blackKing;
    public Button selectedPiece;
    public int pawnFile;
    public int pawnRank;
    public static String movedPiece;
    public static String move;
    public IntIntPair startingSquare;
    public IntIntPair destinationsSquare;
    public boolean myTurn;
    public StackPane lastStart;
    public StackPane lastEnd;
    public Theme theme = new StandardTheme();
    public List<Integer> possibleSquares;
    public static boolean animations = true;

    @FXML
    public void initialize() {
        this.myTurn = true;
//        new SoundPlayer().playGameStartSound();
        for (Node node : chessboardGrid.getChildren()) {
            if (node instanceof StackPane current) {
                setSquareTxtNStyle(current);
                for (Node button : current.getChildren()) {
                    if (button instanceof Button currentButton) {
                        setButtonListeners(currentButton);
                        if (((Button) button).getGraphic() instanceof ImageView imv) {
                            applyPieceTheme(imv, currentButton);
                        }
                    }
                }
            }
        }
    }

    @FXML
    public void close() {
        Platform.exit();
    }

    private void applyPieceTheme(ImageView imv, Button currentButton) {
        String url = imv.getImage().getUrl().substring(imv.getImage().getUrl().lastIndexOf("/images"));
        String themeUrl = url.replaceAll("standard/", theme.getPiecesPath());
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(themeUrl)));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        if (theme instanceof SwagTheme) {
            imageView.setScaleX(0.95);
            imageView.setScaleY(0.95);
        }
        currentButton.setGraphic(imageView);
        currentButton.setContentDisplay(ContentDisplay.CENTER);
        currentButton.setAlignment(Pos.CENTER);
        currentButton.setPadding(new Insets(0, 0, 2, 0));
        Pattern pattern = Pattern.compile("[wb][RQPKNB]");
        Matcher matcher = pattern.matcher(themeUrl);
        if (matcher.find()) {
            currentButton.setId(matcher.group());
        }
    }

    public void setSquareTxtNStyle(StackPane current) {
        Integer rowIndexConstraint = GridPane.getRowIndex(current);
        Integer columnIndexConstraint = GridPane.getColumnIndex(current);
        String square = Character.toString('a' + Objects.requireNonNullElse(columnIndexConstraint, 0))
                + (8 - Objects.requireNonNullElse(rowIndexConstraint, 0));

        current.setAccessibleText(square);
        int r = Objects.requireNonNullElse(GridPane.getRowIndex(current), 0);
        int c = Objects.requireNonNullElse(GridPane.getColumnIndex(current), 0);
        if ((r + c) % 2 == 0) {
            current.setStyle(theme.getLightSquareStyle());
            current.getChildren().get(0).setStyle(theme.getLightSquareStyle());
        } else {
            current.setStyle(theme.getDarkSquareStyle());
            current.getChildren().get(0).setStyle(theme.getDarkSquareStyle());
        }
    }

    public void setButtonListeners(Button currentButton) {
        currentButton.setOnAction(event -> handleButtonClick(event, currentButton));
    }


    public void initMove(Button currentButton) {
        String imageUrl = currentButton.getId();
        System.out.println(imageUrl);
        boolean isWhitePiece;
        if (imageUrl != null) {
            String movedP = imageUrl.charAt(1) + "";
            isWhitePiece = Character.toString(imageUrl.charAt(0)).equals("w");
            movedPiece = movedP.equals("P") ? "" : movedP;
        } else {
            movedPiece = currentButton.getAccessibleText().charAt(1) + "";
            isWhitePiece = currentButton.getAccessibleText().charAt(0) == 'w';
        }
        if (movedPiece.isEmpty()) {
            this.pawnFile = startingSquare.column();
            this.pawnRank = startingSquare.row();
        }
        highlightPossibleSquares(movedPiece, isWhitePiece);
    }


    public void updateCheckStatus() {
        byte[] currentBoard = GameHelper.to1DBoard();
        if (Game.kingChecked(false, currentBoard) && !Game.checkMated(currentBoard, false)) {
            blackKing.setEffect(new Glow(0.7));
            blackKingButton.setStyle(theme.getKingCheckedStyle());
        } else if (Game.checkMated(currentBoard, false)) {
            blackKing.setEffect(new Glow(0.8));
            blackKingButton.setStyle(theme.getKingCheckedStyle());
        } else if (!Game.kingChecked(false, currentBoard)) {
            blackKingButton.setStyle("-fx-background-color: transparent;");
            blackKing.setEffect(null);
        }
        if (Game.kingChecked(true, currentBoard) && !Game.checkMated(currentBoard, true)) {
            whiteKing.setEffect(new Glow(0.4));
            whiteKingButton.setStyle(theme.getKingCheckedStyle());
        } else if (Game.checkMated(currentBoard, true)) {
            whiteKing.setEffect(new Glow(0.8));
            whiteKingButton.setStyle(theme.getKingCheckedStyle());
        } else if (!Game.kingChecked(true, currentBoard)) {
            whiteKingButton.setStyle("-fx-background-color: transparent;");
            whiteKing.setEffect(null);
        }
    }

    public StackPane getPaneFromCoordinate(IntIntPair rc) {
        StackPane result = null;
        for (Node node : chessboardGrid.getChildren()) {
            if (Objects.requireNonNullElse(GridPane.getRowIndex(node), 0) == rc.row() && Objects.requireNonNullElse(GridPane.getColumnIndex(node), 0) == rc.column()) {
                result = (StackPane) node;
            }
        }
        return result;
    }

    public void highlightPossibleSquares(String movedPiece, boolean isWhitePiece) {
        byte[] board = GameHelper.to1DBoard();
        int index = startingSquare.row() * 8 + startingSquare.column();
        List<Integer> list = null;
        if (movedPiece.matches("[bB]")) {
            list = BishopMoveTracker.possibleMovesLogic(board, index, isWhitePiece);
        } else if (movedPiece.matches("[nN]")) {
            list = KnightMoveTracker.possibleMovesLogic(board, index, isWhitePiece);
        } else if (movedPiece.matches("[qQ]")) {
            list = QueenMoveTracker.possibleMovesLogic(board, index, isWhitePiece);
        } else if (movedPiece.matches("[rR]")) {
            list = RookMoveTracker.possibleMovesLogic(board, index, isWhitePiece);
        } else if (movedPiece.matches("[kK]")) {
            list = KingMoveTracker.possibleMovesLogic(board, index, isWhitePiece);
        } else if (movedPiece.isEmpty() || movedPiece.matches("[pP]")) {
            list = PawnMoveTracker.possibleMovesLogic(board, index, isWhitePiece);
        }
        assert list != null;
        this.possibleSquares = list;
        System.out.print(possibleSquares);
        for (Integer coordinate : list) {
            IntIntPair c;
            if (coordinate == W_SHORT_CASTLE) {
                c = new IntIntPair(7, 6);
            } else if (coordinate == W_LONG_CASTLE) {
                c = new IntIntPair(7, 2);
            } else {
                c = new IntIntPair(coordinate / 8, coordinate % 8);
            }
            StackPane square = getPaneFromCoordinate(c);
            Button b = (Button) square.getChildren().get(0);
            String file = "transparent.png";
            if (Theme.isNoHighlighting()) {
                file = "fullytransparent.png";
            }
            Image highlight = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/" + file)));
            ImageView h = new ImageView(highlight);
            h.setOpacity(0.7);
            boolean found = false;
            for (Node n : b.getChildrenUnmodifiable()) {
                if (n instanceof StackPane k) {
                    found = true;
                    k.getChildren().add(h);
                }
            }
            if (!found) b.setGraphic(h);
        }
    }

    public void clearHighlighting() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                StackPane square = getPaneFromCoordinate(new IntIntPair(i, j));
                Button button = (Button) square.getChildren().get(0);
                if ((i + j) % 2 == 0) {
                    button.setStyle(theme.getLightSquareStyle());
                } else {
                    button.setStyle(theme.getDarkSquareStyle());
                }
                if (square.getChildren().size() > 1) {
                    Button movedPiece = (Button) square.getChildren().get(1);
                    movedPiece.getGraphic().setOpacity(1);
                }

                boolean found = false;
                for (Node n : button.getChildrenUnmodifiable()) {
                    if (n instanceof StackPane sp) {
                        sp.getChildren().removeIf(im -> im instanceof ImageView);
                        found = true;
                    }
                }
                if (!found) button.setGraphic(null);
            }
        }
        highlightLastMove(this.lastStart, this.lastEnd);
    }

    public void highlightLastMove(StackPane startCell, StackPane endCell) {
        if (startCell == null || endCell == null) return;
        int scRi = Objects.requireNonNullElse(GridPane.getRowIndex(startCell), 0);
        int scCi = Objects.requireNonNullElse(GridPane.getColumnIndex(startCell), 0);
        int ecRi = Objects.requireNonNullElse(GridPane.getRowIndex(endCell), 0);
        int ecCi = Objects.requireNonNullElse(GridPane.getColumnIndex(endCell), 0);
        String light = theme.getLastMoveLight();
        String dark = theme.getLastMoveDark();
        if ((scCi + scRi) % 2 == 0) { // white square
            startCell.getChildren().get(0).setStyle(startCell.getChildren().get(0).getStyle() + light);
        } else {
            startCell.getChildren().get(0).setStyle(startCell.getChildren().get(0).getStyle() + dark);
        }
        if ((ecRi + ecCi) % 2 == 0) { // white square
            endCell.getChildren().get(0).setStyle(endCell.getChildren().get(0).getStyle() + light);
        } else {
            endCell.getChildren().get(0).setStyle(endCell.getChildren().get(0).getStyle() + dark);
        }
        this.lastStart = startCell;
        this.lastEnd = endCell;
    }

    public void applyMoveToBoardAndUI(IntIntPair startingSquare, IntIntPair destinationsSquare, boolean animation) {
        byte piece = Game.board[startingSquare.row()][startingSquare.column()];
        if (piece == 100) whiteKingHasMoved = true;
        if (piece == -100) blackKingHasMoved = true;
        StackPane startPane = getPaneFromCoordinate(startingSquare);
        StackPane destPane = getPaneFromCoordinate(destinationsSquare);
        if (destPane.getChildren().size() == 2) {
            destPane.getChildren().remove(1);
        }
        Button toMove = (Button) startPane.getChildren().get(1);
        destPane.getChildren().add(toMove);
        byte temp = Game.board[startingSquare.row()][startingSquare.column()];
        Game.board[startingSquare.row()][startingSquare.column()] = 0;
        if (temp == 1 && destinationsSquare.row() == 0) {
            temp = 9;
            setPromotedPiece("wQ", toMove);
        }
        Game.board[destinationsSquare.row()][destinationsSquare.column()] = temp;

        //shortCastle
        if (temp == 100 && destinationsSquare.column() - startingSquare.column() == 2) {
            Game.board[7][7] = 0;
            Game.board[7][5] = 5;
            StackPane oldRookPos = getPaneFromCoordinate(new IntIntPair(7, 7));
            StackPane newRookPos = getPaneFromCoordinate(new IntIntPair(7, 5));
            newRookPos.getChildren().add(oldRookPos.getChildren().remove(1));
            // Long castle
        } else if (temp == 100 && destinationsSquare.column() - startingSquare.column() == -2) {
            Game.board[7][0] = 0;
            Game.board[7][3] = 5;
            StackPane oldRookPos = getPaneFromCoordinate(new IntIntPair(7, 0));
            StackPane newRookPos = getPaneFromCoordinate(new IntIntPair(7, 3));
            newRookPos.getChildren().add(oldRookPos.getChildren().remove(1));
        } else {
            startPane.getChildren().remove(selectedPiece);
        }

        if (animation) {
            playTransition(startPane, destPane, selectedPiece);
        }

        GameHelper.print(Game.board);
    }

    public void setPromotedPiece(String piece, Button selectedPiece) {
        Image promotion = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/" + theme.getPiecesPath() + piece + ".png")));
        ImageView h = new ImageView(promotion);
        h.setFitHeight(50);
        h.setFitWidth(50);
        selectedPiece.setGraphic(h);
        selectedPiece.setAccessibleText(piece);
        selectedPiece.setId(piece);
    }

    public void playTransition(StackPane startPane, StackPane endPane, Button button) {
        if (animations) {
            button.toFront();
            endPane.toFront();
            double deltaX = startPane.getBoundsInParent().getMinX() - endPane.getBoundsInParent().getMinX();
            double deltaY = startPane.getBoundsInParent().getMinY() - endPane.getBoundsInParent().getMinY();
            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            final int pixelsPerSecond = 450;
            double duration = distance / pixelsPerSecond;
            TranslateTransition transition = new TranslateTransition(Duration.seconds(duration), button);
            transition.setFromX(deltaX);
            transition.setFromY(deltaY);
            transition.setToX(0);
            transition.setToY(0);
            transition.play();
        }
    }

    public void handleButtonClick(ActionEvent event, Button currentButton) {
        clearHighlighting();
        if (myTurn) {
            Node button = (Node) event.getSource();
            StackPane square = (StackPane) button.getParent();
            int rank = Objects.requireNonNullElse(GridPane.getRowIndex(square), 0);
            int file = Objects.requireNonNullElse(GridPane.getColumnIndex(square), 0);
            boolean isWhite = true;
            if (!isWhite) {
                rank = 7 - rank;
                file = 7 - file;
            }
            boolean myPiece = Game.board[rank][file] > 0;
            if (myPiece) {
                // this.startingSquare = new IntIntPair(rank, file);
                this.selectedPiece = (Button) button;
                square.getChildren().getFirst().setStyle((rank + file) % 2 == 0 ? theme.getLastMoveLight() : theme.getLastMoveDark());
                startingSquare = new IntIntPair(Objects.requireNonNullElse(GridPane.getRowIndex(currentButton.getParent()), 0), Objects.requireNonNullElse(GridPane.getColumnIndex(currentButton.getParent()), 0));
                initMove(currentButton);
            } else {
                clearHighlighting();
                IntIntPair destinationSquare = new IntIntPair(Objects.requireNonNullElse(GridPane.getRowIndex(square), 0), Objects.requireNonNullElse(GridPane.getColumnIndex(square), 0));
                if (possibleSquares != null
                        && (possibleSquares.contains(destinationSquare.row() * 8 + destinationSquare.column())
                        || possibleSquares.contains(W_SHORT_CASTLE)
                        || possibleSquares.contains(W_LONG_CASTLE))) {
                    this.destinationsSquare = destinationSquare;
                    System.out.println(startingSquare + "_" + destinationSquare);
                    applyMoveToBoardAndUI(startingSquare, destinationSquare, true);
                    clearHighlighting();
                    myTurn = false;
                    selectedPiece = null;
                }
                updateCheckStatus();
                new Thread(() -> {
                    int move = Engine.playEngineMove(5);
                    System.out.println(move);
                    Platform.runLater(() -> {
                        StackPane dest;
                        Button toMove;
                        // TODO check selected piece = king
                        if (move == B_LONG_CASTLE || move == B_SHORT_CASTLE) {
                            blackKingHasMoved = true;
                            boolean lc = move == 690;
                            StackPane kingFrom = getPaneFromCoordinate(new IntIntPair(0, 4));
                            StackPane kingTo = getPaneFromCoordinate(new IntIntPair(0, lc ? 2 : 6));
                            StackPane rookFrom = getPaneFromCoordinate(new IntIntPair(0, lc ? 0 : 7));
                            StackPane rookTo = getPaneFromCoordinate(new IntIntPair(0, lc ? 3 : 5));
                            Button king = (Button) kingFrom.getChildren().remove(1);
                            Button rook = (Button) rookFrom.getChildren().remove(1);
                            kingTo.getChildren().add(king);
                            rookTo.getChildren().add(rook);
                        } else {
                            boolean pawnPromotion = false;
                            IntIntPair promitionSquare = null;
                            toMove = null;
                            for (int i = 0; i < 8; i++) {
                                for (int j = 0; j < 8; j++) {
                                    if (Game.board[i][j] == 0) {
                                        StackPane current = getPaneFromCoordinate(new IntIntPair(i, j));
                                        if (current.getChildren().size() == 2) {
                                            if (i == 0 && Game.board[i][j] == 1) {
                                                pawnPromotion = true;
                                                promitionSquare = new IntIntPair(i, j);
                                            }
                                            toMove = (Button) current.getChildren().get(1);
                                            break;
                                        }
                                    }
                                }
                            }
                            dest = getPaneFromCoordinate(new IntIntPair(move / 8, move % 8));
                            if (dest.getChildren().size() == 2) dest.getChildren().remove(1);
                            dest.getChildren().add(toMove);
                            if (pawnPromotion) {
                                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/standard/bQ.png")));
                                ImageView imageView = new ImageView(image);
                                imageView.setFitHeight(50);
                                imageView.setFitWidth(50);
                                toMove.setGraphic(imageView);
                                Game.board[promitionSquare.row()][promitionSquare.column()] = 9;
                            }
                        }
                        clearHighlighting();
                        updateCheckStatus();
                    });
                    myTurn = true;
                }).start();
            }
        }
    }
}
