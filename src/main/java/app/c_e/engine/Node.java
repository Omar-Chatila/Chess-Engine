package app.c_e.engine;

import chessModel.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Node {
    public static long numberOfNodes;
    private final List<Node> children = new ArrayList<>();
    private final byte[][] currentBoard;


    private final String currentMove;
    private double value;
    private final boolean isWhite;

    public Node(byte[][] currentBoard, String currentMove, boolean isWhite) {
        this.currentBoard = currentBoard;
        this.currentMove = currentMove;
        this.isWhite = isWhite;
        setValue();
    }

    public String getCurrentMove() {
        return currentMove;
    }

    public void setValue() {
        this.value = Evaluation.getValue(this.currentBoard, this.isWhite, this.currentMove);
    }

    public double getValue() {
        return this.value;
    }

    public void setChildren(boolean white) {
        byte pawn = (byte) (white ? 1 : -1);
        byte knight = (byte) (white ? 3 : -3);
        byte bishop = (byte) (white ? 4 : -4);
        byte rook = (byte) (white ? 5 : -5);
        byte queen = (byte) (white ? 9 : -9);
        byte king = (byte) (white ? 100 : -100);
        for (byte i = 0; i < 8; i++) {
            for (byte j = 0; j < 8; j++) {
                if (currentBoard[i][j] == (pawn)) {
                    List<String> pMoves = PawnMoveTracker.possibleMovesLogic(currentBoard, i, j, white);
                    applyMoves(white, pawn, i, j, pMoves);
                } else if (currentBoard[i][j] == (bishop)) {
                    List<String> bMoves = BishopMoveTracker.possibleMovesLogic(currentBoard, i, j, white);
                    applyMoves(white, bishop, i, j, bMoves);
                } else if (currentBoard[i][j] == (knight)) {
                    List<String> nMoves = KnightMoveTracker.possibleMovesLogic(currentBoard, i, j, white);
                    applyMoves(white, knight, i, j, nMoves);
                } else if (currentBoard[i][j] == (rook)) {
                    List<String> rMoves = RookMoveTracker.possibleMovesLogic(currentBoard, i, j, white);
                    applyMoves(white, rook, i, j, rMoves);
                } else if (currentBoard[i][j] == (queen)) {
                    List<String> qMoves = QueenMoveTracker.possibleMovesLogic(currentBoard, i, j, white);
                    applyMoves(white, queen, i, j, qMoves);
                } else if (currentBoard[i][j] == (king)) {
                    List<String> kMoves = KingMoveTracker.possibleMovesLogic(currentBoard, i, j, white);
                    applyMoves(white, king, i, j, kMoves);
                }
            }
        }
    }

    private void applyMoves(boolean white, byte piece, int i, int j, List<String> pMoves) {
        for (String move : pMoves) {
            byte[][] newBoard = GameHelper.copyBoard(currentBoard);
            int moveRank;
            int moveFile;
            if (move.equals("lc")) {
                if (white) {
                    newBoard[7][0] = 0;
                    newBoard[7][1] = 0;
                    newBoard[7][2] = 100;
                    newBoard[7][3] = 5;
                    newBoard[7][4] = 0;
                    Node node = new Node(newBoard, move, true);
                    node.setValue();
                    this.children.add(node);
                } else {
                    newBoard[0][0] = 0;
                    newBoard[0][1] = 0;
                    newBoard[0][2] = -100;
                    newBoard[0][3] = -5;
                    newBoard[0][4] = 0;
                    Node node = new Node(newBoard, move, false);
                    node.setValue();
                    this.children.add(node);
                }
                numberOfNodes++;
                return;
            } else if (move.equals("sc")) {
                if (white) {
                    newBoard[7][4] = 0;
                    newBoard[7][5] = 5;
                    newBoard[7][6] = 100;
                    newBoard[7][7] = 0;
                    Node node = new Node(newBoard, move, true);
                    node.setValue();
                    this.children.add(node);
                } else {
                    newBoard[0][4] = 0;
                    newBoard[0][5] = -5;
                    newBoard[0][6] = -100;
                    newBoard[0][7] = 0;
                    Node node = new Node(newBoard, move, false);
                    node.setValue();
                    this.children.add(node);
                }
                numberOfNodes++;
                return;
            } else {
                moveRank = Character.getNumericValue(move.charAt(0));
                moveFile = Character.getNumericValue(move.charAt(1));
            }
            newBoard[i][j] = 0;
            if (Math.abs(piece) == 1 && (moveRank == 7 || moveRank == 0)) {
                // TODO: promote to all pieces by adding one node every promoted piece
                newBoard[moveRank][moveFile] = (byte) (white ? 9 : -9);
            } else {
                newBoard[moveRank][moveFile] = piece;
            }
            Node node = new Node(newBoard, move, white);
            this.children.add(node);
            numberOfNodes++;
        }
    }

    public List<Node> getChildren() {
        return children;
    }

    public byte[][] getCurrentBoard() {
        return currentBoard;
    }

    public Node getBestMove() {
        List<Node> children = getChildren();
        if (!children.isEmpty()) {
            return children.get(children.size() - 1); // Best move is at the end of the sorted list
        } else {
            return null; // No children available
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Double.compare(value, node.value) == 0 && isWhite == node.isWhite && Objects.equals(children, node.children) && GameHelper.boardEquals(currentBoard, node.currentBoard) && Objects.equals(currentMove, node.currentMove);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(children, currentMove, value, isWhite);
        result = 31 * result + Arrays.deepHashCode(currentBoard);
        return result;
    }

    @Override
    public String toString() {
        return (isWhite ? "White " : "Black: ") + this.currentMove + "\n" + " - Value: " + this.value + "\n" + GameHelper.boardToString(this.currentBoard);
    }
}
