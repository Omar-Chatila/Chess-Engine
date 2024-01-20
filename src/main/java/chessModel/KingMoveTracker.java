package chessModel;

import java.util.ArrayList;
import java.util.List;

import static chessModel.GameHelper.copyBoard;

public class KingMoveTracker {
    private static final int[] off = {-9, 9, 7, -7, 8, -8, 1, -1};
    private static boolean whiteKingHasMoved;
    private static boolean blackKingHasMoved;

    private static boolean reachedRim(int d, int f, int r) {
        return (f == 0 && d == 7 || f == 7 && d == 6 || r == 0 && d == 5 || r == 7 && d == 4
                || ((f == 7 || r == 0) && d == 3) || ((f == 0 || r == 7) && d == 2) ||
                ((f == 7 || r == 7) && d == 1) || ((f == 0 || r == 0) && d == 0));
    }

    private static boolean inKingTerritory(byte[] copy, int index) {
        for (int i = index - 8; i <= index + 8; i += 8) {
            for (int j = index - 1; j <= index + 1; j++) {
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
            if (Game.kingChecked(white, board)) return false;
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
                    if(!castleTroughCheck) {
                        copy1[61] = 0;
                        copy1[62] = 100;
                        return !Game.kingChecked(true, copy1);
                    }
                    return false;
                }
            } else {
                if (!blackKingHasMoved && freeSpace) {
                    boolean castleTroughCheck = Game.kingChecked(false, copy2);
                    if(!castleTroughCheck) {
                        copy2[5] = 0;
                        copy2[6] = -100;
                        return !Game.kingChecked(false, copy2);
                    }
                    return false;
                }
            }
        } else if (white) {
            whiteKingHasMoved = true;
        } else if (board[4] != 100) {
            blackKingHasMoved = true;
        }
        return false;
    }

    private static boolean hasLongCastlingRight(boolean white, byte[] board) {
        byte[] copy1 = copyBoard(board);
        copy1[57] = 100;
        copy1[58] = 100;
        copy1[59] = 100;
        byte[] copy2 = copyBoard(board);
        copy2[1] = -100;
        copy2[2] = -100;
        copy2[3] = -100;
        boolean castleTroughCheck = Game.kingChecked(white, white ? copy1 : copy2);
        boolean freeSpace = white ? board[60] == 100 && board[59] == 0 && board[58] == 0 && board[57] == 0 && board[56] == 5
                : board[4] == -100 && board[3] == 0 && board[2] == 0 && board[1] == 0 && board[0] == -5;
        if (white)
            return !whiteKingHasMoved && !Game.kingChecked(true, board) && freeSpace && !castleTroughCheck;
        else
            return !blackKingHasMoved && !Game.kingChecked(false, board) && freeSpace && !castleTroughCheck;
    }

    private static boolean isKingThreat(boolean white, byte[] board, int kingFile, int kingRank) {
        int[] kingMoves = {-9, -8, -7, -1, 1, 7, 8, 9};

        for (int move : kingMoves) {
            int targetPosition = kingFile + move & 7 + (kingRank + (move >> 3)) << 3;

            if (targetPosition >= 0 && targetPosition < 64) {
                int piece = board[targetPosition];
                if ((white && piece == -100) || (!white && piece == 100)) {
                    return true;
                }
            }
        }

        return false;
    }


    private static List<Integer> addCastlingMoves(boolean white, List<Integer> moves, byte[] board) {
        if (white && hasLongCastlingRight(true, board)) {
            moves.add(69);
        }
        if (white && hasShortCastlingRight(true, board)) {
            moves.add(420);
        }
        if (!white && hasLongCastlingRight(false, board)) {
            moves.add(690);
        }
        if (!white && hasShortCastlingRight(false, board)) {
            moves.add(691);
        }
        return moves;
    }

    public static List<Integer> possibleMovesLogic(byte[] board, int index, boolean white) {
        List<Integer> moves = new ArrayList<>();
        byte[] copy = copyBoard(board);
        int f = index & 0B111;
        int r = index >> 3;
        for (int d = 0; d < 8; d++) {
            if (!reachedRim(d, f, r) && isValidSquare(index + off[d])) {
                int offset = index + off[d];
                byte squareContent = copy[offset];
                if (white && squareContent > 0 || !white && squareContent < 0) continue;
                copy[offset] = (byte)(white ? 100 : -100);
                copy[index] = 0;
                if (!Game.kingChecked(white, copy) && !inKingTerritory(copy, offset)) {
                    moves.add(offset);
                }
                copy = copyBoard(board);
            }
        }
        return addCastlingMoves(white, moves, board);
    }

    private static boolean isValidSquare(int rank, int file) {
        return rank >= 0 && rank < 8 && file >= 0 && file < 8;
    }

    private static boolean isValidSquare(int index) {
        return index >= 0 && index < 64;
    }

}
