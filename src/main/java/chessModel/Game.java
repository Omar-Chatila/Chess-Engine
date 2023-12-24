package chessModel;

import java.util.ArrayList;
import java.util.List;

import static chessModel.GameHelper.copyBoard;
import static chessModel.GameHelper.print;
import static chessModel.PawnMoveTracker.*;


@SuppressWarnings("CallToPrintStackTrace")
public class Game {
    private static final Graveyard whiteGraveyard = new Graveyard(true);
    private static final Graveyard blackGraveyard = new Graveyard(false);
    public static List<String> moveList = new ArrayList<>();
    public static List<byte[][]> playedPositions = new ArrayList<>();
    public static byte[][] board = new byte[8][8];
    public static boolean drawClaimable;
    private static int fiftyMoveRule;
    private static byte materialDifference;

    private static byte[][] movePieces(String move, boolean white) throws IllegalStateException {
        boolean legal = true;
        resetEnpassant();
        if (move.equals("O-O") || move.equals("O-O-O")) {
            legal = KingMoveTracker.validateKing(board, move, white);
        } else {
            String currentPiece = Character.toString(move.charAt(0));
            if (!currentPiece.matches("[NKBRQ]")) {
                legal = movePawn(board, move, white);
            } else if (currentPiece.matches("N")) {
                legal = KnightMoveTracker.validateKnight(board, move, white);
            } else if (currentPiece.matches("R")) {
                legal = RookMoveTracker.validateRook(board, move, white);
            } else if (currentPiece.matches("B")) {
                legal = BishopMoveTracker.validateBishop(board, move, white);
            } else if (currentPiece.matches("Q")) {
                legal = QueenMoveTracker.validateQueen(board, move, white);
            } else if (currentPiece.matches("K")) {
                legal = KingMoveTracker.validateKing(board, move, white);
            }
        }
        if (!legal && white) {
            print(board);
            throw new IllegalStateException();
        }
        return board;
    }


    public static boolean isAmbiguousMove(String move, boolean white, IntIntPair destinationSquare) {
        byte file = (byte) destinationSquare.column();
        byte row = (byte) destinationSquare.row();
        if (!white) {
            file = (byte) (7 - file);
            row = (byte) (7 - row);
        }
        int count = 0;
        for (byte r = 0; r < 8; r++) {
            for (byte f = 0; f < 8; f++) {
                switch (move.charAt(0)) {
                    case 'R' -> {
                        List<List<String>> allRookMoves = new ArrayList<>();
                        if (board[r][f] == ((white) ? 5 : -5)) {
                            allRookMoves.add(RookMoveTracker.possibleMovesLogic(board, r, f, white));
                        }
                        for (List<String> rookMoves : allRookMoves) {
                            if (rookMoves.contains(row + "" + file)) {
                                count++;
                            }
                        }
                    }
                    case 'N' -> {
                        List<List<String>> allKnightMoves = new ArrayList<>();
                        if (board[r][f] == ((white) ? 3 : -3)) {
                            allKnightMoves.add(KnightMoveTracker.possibleMovesLogic(board, r, f, white));
                        }
                        for (List<String> knightMoves : allKnightMoves) {
                            if (knightMoves.contains(row + "" + file)) {
                                count++;
                            }
                        }
                    }
                    case 'Q' -> {
                        List<List<String>> allQueenMoves = new ArrayList<>();
                        if (board[r][f] == ((white) ? 9 : -9)) {
                            allQueenMoves.add(QueenMoveTracker.possibleMovesLogic(board, r, f, white));
                        }
                        for (List<String> queenMoves : allQueenMoves) {
                            if (queenMoves.contains(row + "" + file)) {
                                count++;
                            }
                        }
                    }
                }
            }
        }
        return count > 1;
    }

    public static boolean pieceOnSameFile(String move, boolean white, IntIntPair destinationSquare, IntIntPair startingSquare) {
        byte file = (byte) destinationSquare.column();
        byte row = (byte) destinationSquare.row();
        byte startFile = (byte) startingSquare.column();
        if (!white) {
            row = (byte) (7 - row);
            file = (byte) (7 - file);
            startFile = (byte) (7 - startFile);
        }
        int found = 0;
        char piece = move.charAt(0);
        List<List<String>> allMoves = new ArrayList<>();
        for (byte r = 0; r < 8; r++) {
            switch (piece) {
                case 'R' -> {
                    if (board[r][startFile] == (white ? 5 : -5)) {
                        allMoves.add(RookMoveTracker.possibleMovesLogic(board, r, startFile, white));
                    }
                }
                case 'N' -> {
                    if (board[r][startFile] == ((white) ? 4 : -4)) {
                        allMoves.add(KnightMoveTracker.possibleMovesLogic(board, r, startFile, white));
                    }
                }
                case 'Q' -> {
                    if (board[r][startFile] == ((white) ? 9 : -9)) {
                        allMoves.add(QueenMoveTracker.possibleMovesLogic(board, r, startFile, white));
                    }
                }
            }
        }
        for (List<String> moveList : allMoves) {
            if (moveList.contains(row + "" + file)) {
                found++;
            }
        }
        return found > 1;
    }

    public static boolean kingChecked(boolean white, byte[][] board) {
        boolean checked = false;
        for (byte rank = 0; rank < 8; rank++) {
            for (byte file = 0; file < 8; file++) {
                if (white) {
                    switch (board[rank][file]) {
                        case -9 -> checked |= QueenMoveTracker.checksKing(board, rank, file, true);
                        case -4 -> checked |= BishopMoveTracker.checksKing(board, rank, file, true);
                        case -3 -> checked |= KnightMoveTracker.checksKing(board, rank, file, true);
                        case -5 -> checked |= RookMoveTracker.checksKing(board, rank, file, true);
                        case -1 -> checked |= checksKing(board, rank, file, true);
                    }
                } else {
                    switch (board[rank][file]) {
                        case 9 -> checked |= QueenMoveTracker.checksKing(board, rank, file, false);
                        case 4 -> checked |= BishopMoveTracker.checksKing(board, rank, file, false);
                        case 3 -> checked |= KnightMoveTracker.checksKing(board, rank, file, false);
                        case 5 -> checked |= RookMoveTracker.checksKing(board, rank, file, false);
                        case 1 -> checked |= checksKing(board, rank, file, false);
                    }
                }
            }
            if (checked) break;
        }
        return checked;
    }

    public static boolean kingChecked2(boolean white, byte[][] board) {
        int kingRank = -1;
        int kingFile = -1;

        // Find the king's position
        for (byte rank = 0; rank < 8; rank++) {
            for (byte file = 0; file < 8; file++) {
                if ((white && board[rank][file] == 100) || (!white && board[rank][file] == -100)) {
                    kingRank = rank;
                    kingFile = file;
                    break;
                }
            }
            if (kingRank != -1) break;
        }
        if (kingRank == -1 || kingFile == -1) return true;
        for (byte rank = 0; rank < 8; rank++) {
            for (byte file = 0; file < 8; file++) {
                byte piece = board[rank][file];

                if ((white && piece < 0) || (!white && piece > 0)) {
                    // Potential threat piece found

                    if (piece == -1 || piece == 1) {
                        // Check for pawn threats
                        int rankDiff = Math.abs(rank - kingRank);
                        int fileDiff = Math.abs(file - kingFile);
                        if (rankDiff == 1 && fileDiff == 1) {
                            return true; // Pawn threatens the king
                        }
                    } else if (piece == -3 || piece == 3) {
                        // Check for knight threats
                        int rankDiff = Math.abs(rank - kingRank);
                        int fileDiff = Math.abs(file - kingFile);
                        if ((rankDiff == 2 && fileDiff == 1) || (rankDiff == 1 && fileDiff == 2)) {
                            return true; // Knight threatens the king
                        }
                    } else {
                        for (int d = -1; d <= 1; d += 2) {
                            for (int i = 1; i < 8; i++) {
                                int shiftedRank = kingRank + d * i;
                                int shiftedFile = kingFile + d * i;

                                // Check if the target square is within the board
                                if (shiftedRank >= 0 && shiftedRank < 8 && shiftedFile >= 0 && shiftedFile < 8) {
                                    // Process the square on the diagonal
                                    if (board[shiftedRank][shiftedFile] == (white ? -4 : 4) || board[shiftedRank][shiftedFile] == (white ? -9 : 9))
                                        return true;
                                    // Process the square at

                                    if (board[kingRank][shiftedFile] == (white ? -5 : 5) || board[kingRank][shiftedFile] == (white ? -9 : 9))
                                        return true;
                                    // Process the squares to the side
                                    if (board[shiftedRank][kingFile] == (white ? -5 : 5) || board[shiftedRank][kingFile] == (white ? -9 : 9))
                                        return true;
                                } else {
                                    // The diagonal goes out of bounds
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean kingChecked(boolean white) {
        return kingChecked(white, Game.board);
    }

    public static boolean checkMated(boolean white) {
        boolean checkMate = false;
        if (kingChecked(white)) {
            checkMate = hasNoMoves(white);
        }
        GameStates.setGameOver(checkMate);
        return checkMate;
    }

    public static boolean checkMated(byte[][] board, boolean white) {
        boolean checkMate = false;
        if (kingChecked(white, board)) {
            checkMate = hasNoMoves(board, white);
        }
        return checkMate;
    }

    public static boolean stalemated(boolean white) {
        boolean stalemate = false;
        if (!kingChecked(white)) {
            stalemate = hasNoMoves(white);
        }
        GameStates.setGameOver(stalemate);
        return stalemate;
    }

    public static boolean stalemated(byte[][] board, boolean white) {
        boolean stalemate = false;
        if (!kingChecked(white, board)) {
            stalemate = hasNoMoves(board, white);
        }
        GameStates.setGameOver(stalemate);
        return stalemate;
    }

    private static boolean hasNoMoves(boolean white) {
        return hasNoMoves(board, white);
    }

    public static boolean hasNoMoves(byte[][] board, boolean white) {
        boolean hasMoves = false;
        for (byte rank = 0; rank < 8; rank++) {
            for (byte file = 0; file < 8; file++) {
                if (white) {
                    switch (board[rank][file]) {
                        case 9 -> hasMoves |= !QueenMoveTracker.possibleMovesLogic(board, rank, file, true).isEmpty();
                        case 4 -> hasMoves |= !BishopMoveTracker.possibleMovesLogic(board, rank, file, true).isEmpty();
                        case 3 -> hasMoves |= !KnightMoveTracker.possibleMovesLogic(board, rank, file, true).isEmpty();
                        case 5 -> hasMoves |= !RookMoveTracker.possibleMovesLogic(board, rank, file, true).isEmpty();
                        case 100 -> hasMoves |= !KingMoveTracker.possibleMovesLogic(board, rank, file, true).isEmpty();
                        case 1 -> hasMoves |= !PawnMoveTracker.possibleMovesLogic(board, rank, file, true).isEmpty();
                    }
                }
            }
        }
        for (byte rank = 0; rank < 8; rank++) {
            for (byte file = 0; file < 8; file++) {
                if (!white) {
                    switch (board[rank][file]) {
                        case -9 -> hasMoves |= !QueenMoveTracker.possibleMovesLogic(board, rank, file, false).isEmpty();
                        case -4 ->
                                hasMoves |= !BishopMoveTracker.possibleMovesLogic(board, rank, file, false).isEmpty();
                        case -3 ->
                                hasMoves |= !KnightMoveTracker.possibleMovesLogic(board, rank, file, false).isEmpty();
                        case -5 -> hasMoves |= !RookMoveTracker.possibleMovesLogic(board, rank, file, false).isEmpty();
                        case -100 ->
                                hasMoves |= !KingMoveTracker.possibleMovesLogic(board, rank, file, false).isEmpty();
                        case -1 -> hasMoves |= !PawnMoveTracker.possibleMovesLogic(board, rank, file, false).isEmpty();
                    }
                }
            }
        }
        return !hasMoves;
    }

    private static boolean isThreefoldRepetition() {
        byte tally = 1;
        for (byte[][] b : playedPositions) {
            if (GameHelper.boardEquals(board, b)) {
                tally++;
            }
        }
        return tally >= 3;
    }

    private static boolean reset50moveRule(String move) {
        return Character.toString(move.charAt(0)).matches("[a-h]") || move.contains("x");
    }

    private static boolean isInsufficientMaterial() {
        List<Byte> pieces = GameHelper.boardToList(board);
        byte whiteKnights = 0;
        byte blackKnights = 0;
        byte whiteBishop = 0;
        byte blackBishop = 0;
        for (byte piece : pieces) {
            byte p = (byte) Math.abs(piece);
            if (p == 1 || p == 5 || p == 9) {
                return false;
            } else if (piece == 3) {
                whiteKnights++;
            } else if (piece == -3) {
                blackKnights++;
            } else if (piece == 4) {
                whiteBishop++;
            } else if (piece == -4) {
                blackBishop++;
            }
        }
        return !((blackKnights > 0) && whiteBishop > 0 || (whiteKnights > 0) && blackBishop > 0) && (whiteKnights < 2 && blackKnights < 2 || whiteBishop < 2 && blackBishop < 2);
    }

    public static void executeMove(String move, boolean white) {
        drawClaimable = false;
        System.out.println("Current Move:  " + move);
        try {
            print(movePieces(move, white));
            if (stalemated(!white)) {
                GameStates.setGameOver(true);
            }
            if (isInsufficientMaterial()) {
                GameStates.setGameOver(true);
            }
            if (isThreefoldRepetition()) {
                drawClaimable = true;
            }
            if (fiftyMoveRule >= 100) {
                drawClaimable = true;
            }
            if (kingChecked(!white) && checkMated(!white)) {
                System.out.println("Game over! - " + (!white ? "Black won!" : "White won!"));
            }
            if (reset50moveRule(move)) {
                fiftyMoveRule = 0;
            } else {
                fiftyMoveRule++;
            }
            moveList.add(move);
            playedPositions.add(copyBoard(board));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Graveyard getBlackGraveyard() {
        return blackGraveyard;
    }

    public static Graveyard getWhiteGraveyard() {
        return whiteGraveyard;
    }

    public static byte getMaterialDifference() {
        return materialDifference;
    }
}
