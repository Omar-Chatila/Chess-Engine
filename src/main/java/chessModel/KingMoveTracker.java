package chessModel;

import java.util.ArrayList;
import java.util.List;

import static chessModel.GameHelper.copyBoard;

public class KingMoveTracker {
    private static final int[] off = {-9, 9, 7, -7, 8, -8, 1, -1};
    public static final int W_SHORT_CASTLE = 420;
    public static final int W_LONG_CASTLE = 69;
    public static final int B_SHORT_CASTLE = 691;
    public static final int B_LONG_CASTLE = 690;


    public static boolean whiteKingHasMoved;
    public static boolean blackKingHasMoved;

    private static boolean reachedRim(int d, int f, int r) {
        return (f == 0 && d == 7 || f == 7 && d == 6 || r == 0 && d == 5 || r == 7 && d == 4
                || ((f == 7 || r == 0) && d == 3) || ((f == 0 || r == 7) && d == 2) ||
                ((f == 7 || r == 7) && d == 1) || ((f == 0 || r == 0) && d == 0));
    }

    private static boolean inKingTerritory(byte[] copy, int index) {
        for (int i = index - 8; i <= index + 8; i += 8) {
            for (int j = -1; j <= 1; j++) {
                if (isValidSquare(i + j)) {
                    if (i + j == index) continue;
                    if (copy[i + j] == 100 || copy[i + j] == -100) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean hasShortCastlingRight(boolean white, byte[] board) {
        if (white && board[60] == 100 || !white && board[4] == -100) {
            byte[] copy1 = new byte[64];
            byte[] copy2 = new byte[64];
            if (white) {
                copy1 = copyBoard(board);
                copy1[60] = 0;
                copy1[61] = 100;
            } else {
                copy2 = copyBoard(board);
                copy2[4] = 0;
                copy2[5] = -100;
            }
            boolean freeSpace = white ? board[60] == 100 && board[61] == 0 && board[62] == 0 && board[63] == 5
                    : board[4] == -100 && board[5] == 0 && board[6] == 0 && board[7] == -5;
            if (white) {
                if (!whiteKingHasMoved && freeSpace) {
                    boolean castleTroughCheck = Game.kingChecked(true, copy1);
                    if (!castleTroughCheck) {
                        copy1[61] = 0;
                        copy1[62] = 100;
                        return !Game.kingChecked(true, copy1);
                    }
                    return false;
                }
            } else {
                if (!blackKingHasMoved && freeSpace) {
                    boolean castleTroughCheck = Game.kingChecked(false, copy2);
                    if (!castleTroughCheck) {
                        copy2[5] = 0;
                        copy2[6] = -100;
                        return !Game.kingChecked(false, copy2);
                    }
                    return false;
                }
            }
        }
        return false;
    }

    private static boolean hasLongCastlingRight(boolean white, byte[] board) {
        if (white) {
            byte[] copy1 = copyBoard(board);
            copy1[60] = 0;
            copy1[58] = 100;
            boolean castleTroughCheck2 = Game.kingChecked(true, copy1);
            if (!castleTroughCheck2) {
                copy1[58] = 0;
                copy1[59] = 100;
                boolean castleTroughCheck3 = Game.kingChecked(true, copy1);
                if (castleTroughCheck3) return false;
            } else {
                return false;
            }
        } else {
            byte[] copy2 = copyBoard(board);
            copy2[4] = 0;
            copy2[2] = -100;
            boolean castleTroughCheck2 = Game.kingChecked(false, copy2);
            if (!castleTroughCheck2) {
                copy2[2] = 0;
                copy2[3] = -100;
                boolean castleTroughCheck3 = Game.kingChecked(false, copy2);
                if (castleTroughCheck3) return false;
            } else {
                return false;
            }
        }
        boolean freeSpace = white ? board[60] == 100 && board[59] == 0 && board[58] == 0 && board[57] == 0 && board[56] == 5
                : board[4] == -100 && board[3] == 0 && board[2] == 0 && board[1] == 0 && board[0] == -5;
        if (white)
            return !whiteKingHasMoved && !Game.kingChecked(true, board) && freeSpace;
        else
            return !blackKingHasMoved && !Game.kingChecked(false, board) && freeSpace;
    }

    private static List<Integer> addCastlingMoves(boolean white, List<Integer> moves, byte[] board, boolean kingChecked) {
        if (kingChecked || (white && whiteKingHasMoved || !white && blackKingHasMoved)) return moves;
        if (white && hasLongCastlingRight(true, board)) {
            moves.add(W_LONG_CASTLE);
        }
        if (white && hasShortCastlingRight(true, board)) {
            moves.add(W_SHORT_CASTLE);
        }
        if (!white && hasLongCastlingRight(false, board)) {
            moves.add(B_LONG_CASTLE);
        }
        if (!white && hasShortCastlingRight(false, board)) {
            moves.add(B_SHORT_CASTLE);
        }
        return moves;
    }

    public static List<Integer> possibleMovesLogic(byte[] board, int index, boolean white) {
        boolean kingChecked = Game.kingChecked(white, board);
        List<Integer> moves = new ArrayList<>();
        byte[] copy = copyBoard(board);
        int f = index & 0B111;
        int r = index >> 3;
        for (int d = 0; d < 8; d++) {
            if (!reachedRim(d, f, r) && isValidSquare(index + off[d])) {
                int offset = index + off[d];
                byte squareContent = copy[offset];
                if (white && squareContent > 0 || !white && squareContent < 0) continue;
                copy[offset] = (byte) (white ? 100 : -100);
                copy[index] = 0;
                if (!Game.kingChecked(white, copy) && !inKingTerritory(copy, offset)) {
                    moves.add(offset);
                }
                copy = copyBoard(board);
            }
        }
        return addCastlingMoves(white, moves, board, kingChecked);
    }

    private static boolean isValidSquare(int index) {
        return index >= 0 && index < 64;
    }

}
