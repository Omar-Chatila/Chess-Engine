package chessModel;

import java.util.ArrayList;
import java.util.List;


public class Game {
    public static List<String> moveList = new ArrayList<>();
    public static List<byte[][]> playedPositions = new ArrayList<>();

    public static byte[][] board = new byte[8][8];
    static int QUEEN_VALUE = 9;
    static int ROOK_VALUE = 5;
    static int BISHOP_VALUE = 4;
    static int KNIGHT_VALUE = 3;
    public static boolean drawClaimable;
    private static int fiftyMoveRule;
    private static byte materialDifference;

    private static int findKingPosition(boolean white, byte[] board) {
        for (int i = 0; i < 64; i++) {
            if (board[i] == (white ? 100 : -100)) {
                return i;
            }
        }
        return -1; // King not found, handle appropriately in your code
    }

    public static boolean kingChecked(boolean white, byte[] board) {
        int kingPosition = findKingPosition(white, board);
        int kingFile = kingPosition & 0x07;
        int kingRank = kingPosition >> 3;
        if (isThreatInDirection(white, board, kingFile, kingRank, 0, 1, QUEEN_VALUE, ROOK_VALUE) ||
                isThreatInDirection(white, board, kingFile, kingRank, 0, -1, QUEEN_VALUE, ROOK_VALUE) ||
                isThreatInDirection(white, board, kingFile, kingRank, 1, 0, QUEEN_VALUE, ROOK_VALUE) ||
                isThreatInDirection(white, board, kingFile, kingRank, -1, 0, QUEEN_VALUE, ROOK_VALUE)) {
            return true;
        }
        if (isThreatInDirection(white, board, kingFile, kingRank, -1, 1, QUEEN_VALUE, BISHOP_VALUE) ||
                isThreatInDirection(white, board, kingFile, kingRank, -1, -1, QUEEN_VALUE, BISHOP_VALUE) ||
                isThreatInDirection(white, board, kingFile, kingRank, 1, 1, QUEEN_VALUE, BISHOP_VALUE) ||
                isThreatInDirection(white, board, kingFile, kingRank, 1, -1, QUEEN_VALUE, BISHOP_VALUE)) {
            return true;
        }

        if (isPawnThreat(white, board, kingFile, kingRank)) {
            return true;
        }

        return isKnightThreat(white, board, kingFile, kingRank);

    }

    private static boolean isThreatInDirection(boolean white, byte[] board, int startFile, int startRank,
                                               int fileDirection, int rankDirection, int rookOrQueenValue, int queenValue) {
        int file = startFile + fileDirection;
        int rank = startRank + rankDirection;

        while (file >= 0 && file < 8 && rank >= 0 && rank < 8) {
            int piece = board[file + rank * 8];
            if (piece != 0) {
                if ((white && piece == -rookOrQueenValue) || (!white && piece == rookOrQueenValue)) {
                    return true;
                } else if ((white && piece == -queenValue) || (!white && piece == queenValue)) {
                    return true;
                } else {
                    break;
                }
            }

            file += fileDirection;
            rank += rankDirection;
        }

        return false;
    }

    private static boolean isPawnThreat(boolean white, byte[] board, int kingFile, int kingRank) {
        int direction = white ? -1 : 1;
        int leftFile = kingFile - 1;
        int rightFile = kingFile + 1;

        if (kingRank + direction >= 0 && kingRank + direction < 8) {
            return (leftFile >= 0 && board[leftFile + ((kingRank + direction) << 3)] == direction) ||
                    (rightFile < 8 && board[rightFile + ((kingRank + direction) << 3)] == direction); // King is in check
        }
        return false;
    }

    private static boolean isKnightThreat(boolean white, byte[] board, int kingFile, int kingRank) {
        int[] knightMoves = {-17, -15, -10, -6, 6, 10, 15, 17};

        for (int move : knightMoves) {
            int targetPosition = kingFile + (move & 7) + ((kingRank + (move >> 3)) << 3);

            if (targetPosition >= 0 && targetPosition < 64) {
                int piece = board[targetPosition];
                if ((white && piece == -KNIGHT_VALUE) || (!white && piece == KNIGHT_VALUE)) {
                    return true;
                }
            }
        }

        return false;
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

    public static boolean hasNoMoves(byte[] board, boolean white) {
        boolean hasMoves = false;
        if (white) {
            for (byte index = 0; index < 64; index++) {
                switch (board[index]) {
                    case 9 -> hasMoves = !QueenMoveTracker.possibleMovesLogic(board, index, true).isEmpty();
                    case 4 -> hasMoves = !BishopMoveTracker.possibleMovesLogic(board, index, true).isEmpty();
                    case 3 -> hasMoves = !KnightMoveTracker.possibleMovesLogic(board, index, true).isEmpty();
                    case 5 -> hasMoves = !RookMoveTracker.possibleMovesLogic(board, index, true).isEmpty();
                    case 100 -> hasMoves = !KingMoveTracker.possibleMovesLogic(board, index, true).isEmpty();
                    case 1 -> hasMoves = !PawnMoveTracker.possibleMovesLogic(board, index, true).isEmpty();
                    default -> {

                    }
                }
                if (hasMoves) return false;
            }
            return true;
        } else {
            for (byte index = 0; index < 64; index++) {
                switch (board[index]) {
                    case -9 -> hasMoves = !QueenMoveTracker.possibleMovesLogic(board, index, false).isEmpty();
                    case -4 -> hasMoves = !BishopMoveTracker.possibleMovesLogic(board, index, false).isEmpty();
                    case -3 -> hasMoves = !KnightMoveTracker.possibleMovesLogic(board, index, false).isEmpty();
                    case -5 -> hasMoves = !RookMoveTracker.possibleMovesLogic(board, index, false).isEmpty();
                    case -100 -> hasMoves = !KingMoveTracker.possibleMovesLogic(board, index, false).isEmpty();
                    case -1 -> hasMoves = !PawnMoveTracker.possibleMovesLogic(board, index, false).isEmpty();
                    default -> {

                    }
                }
                if (hasMoves) return false;
            }
        }
        return true;
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
