package chessModel;

import java.util.ArrayList;
import java.util.List;

import static chessModel.PawnMoveTracker.checksKing;


public class Game {
    public static List<String> moveList = new ArrayList<>();
    public static List<byte[][]> playedPositions = new ArrayList<>();
    public static byte[][] board = new byte[8][8];
    public static boolean drawClaimable;
    private static int fiftyMoveRule;
    private static byte materialDifference;

    public static boolean kingChecked(boolean white, byte[] board) {
        boolean checked = false;
        for (byte index = 0; index < 64; index++) {
            if (white) {
                switch (board[index]) {
                    case -9 -> checked = QueenMoveTracker.checksKing(board, index, true);
                    case -4 -> checked = BishopMoveTracker.checksKing(board, index, true);
                    case -3 -> checked = KnightMoveTracker.checksKing(board, index, true);
                    case -5 -> checked = RookMoveTracker.checksKing(board, index, true);
                    case -1 -> checked = checksKing(board, index, true);
                }
            } else {
                switch (board[index]) {
                    case 9 -> checked = QueenMoveTracker.checksKing(board, index, false);
                    case 4 -> checked = BishopMoveTracker.checksKing(board, index, false);
                    case 3 -> checked = KnightMoveTracker.checksKing(board, index, false);
                    case 5 -> checked = RookMoveTracker.checksKing(board, index, false);
                    case 1 -> checked = checksKing(board, index, false);
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
        return kingChecked2(white, Game.board);
    }


    public static boolean checkMated(byte[] board, boolean white) {
        boolean checkMate = false;
        if (kingChecked(white, board)) {
            checkMate = hasNoMoves(board, white);
        }
        return checkMate;
    }

    public static boolean stalemated(byte[] board, boolean white) {
        boolean stalemate = false;
        if (!kingChecked(white, board)) {
            stalemate = hasNoMoves(board, white);
        }
        return stalemate;
    }

    private static boolean hasNoMoves(boolean white) {
        return hasNoMoves(GameHelper.to1DBoard(), white);
    }


    public static boolean hasNoMoves(byte[] board, boolean white) {
        boolean hasMoves = false;
        for (byte index = 0; index < 64; index++) {

            if (white) {
                switch (board[index]) {
                    case 9 -> hasMoves |= !QueenMoveTracker.possibleMovesLogic(board, index, true).isEmpty();
                    case 4 -> hasMoves |= !BishopMoveTracker.possibleMovesLogic(board, index, true).isEmpty();
                    case 3 -> hasMoves |= !KnightMoveTracker.possibleMovesLogic(board, index, true).isEmpty();
                    case 5 -> hasMoves |= !RookMoveTracker.possibleMovesLogic(board, index, true).isEmpty();
                    case 100 -> hasMoves |= !KingMoveTracker.possibleMovesLogic(board, index, true).isEmpty();
                    case 1 -> hasMoves |= !PawnMoveTracker.possibleMovesLogic(board, index, true).isEmpty();
                }
            }

        }
        for (byte index = 0; index < 64; index++) {

            if (!white) {
                switch (board[index]) {
                    case -9 -> hasMoves |= !QueenMoveTracker.possibleMovesLogic(board, index, false).isEmpty();
                    case -4 -> hasMoves |= !BishopMoveTracker.possibleMovesLogic(board, index, false).isEmpty();
                    case -3 -> hasMoves |= !KnightMoveTracker.possibleMovesLogic(board, index, false).isEmpty();
                    case -5 -> hasMoves |= !RookMoveTracker.possibleMovesLogic(board, index, false).isEmpty();
                    case -100 -> hasMoves |= !KingMoveTracker.possibleMovesLogic(board, index, false).isEmpty();
                    case -1 -> hasMoves |= !PawnMoveTracker.possibleMovesLogic(board, index, false).isEmpty();
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
}
