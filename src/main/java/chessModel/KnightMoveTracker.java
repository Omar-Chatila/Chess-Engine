package chessModel;

import java.util.ArrayList;
import java.util.List;

import static chessModel.GameHelper.copyBoard;

public class KnightMoveTracker {
    private static final byte[] off = {-17, -10, 6, 15, -15, -6, 10, 17};

    public static boolean checksKing(byte[] board, int index, boolean white) {
        for (byte d = 0; d < 8; d++) {
            int offset = index + off[d];
            if (isValidSquare(offset)) {
                byte squareContent = board[offset];
                if (!white && squareContent == -100) {
                    return true;
                } else if (white && squareContent == 100) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<Integer> possibleMovesLogic(byte[] board, int index, boolean white) {
        List<Integer> moves = new ArrayList<>();
        byte[] copy = copyBoard(board);
        int rank = index / 8;
        int file = index % 8;
        for (byte d = 0; d < 8; d++) {
            int offset = index + off[d];
            if (isValidSquare(offset)) {
                int destRank = offset / 8;
                int destFile = offset % 8;
                if (Math.abs(destRank - rank) <= 2 && Math.abs(destFile - file) <= 2) {
                    byte squareContent = copy[offset];
                    if (white && squareContent > 0) continue;
                    if (!white && squareContent < 0) continue;
                    if (squareContent == 0) {
                        copy[offset] = (byte) (white ? 3 : -3);
                        copy[index] = 0;
                        if (!Game.kingChecked(white, copy)) {
                            moves.add(offset);
                        }
                    } else if (white) {
                        copy[offset] = 3;
                        copy[index] = 0;
                        if (!Game.kingChecked(true, copy))
                            moves.add(offset);
                    } else {
                        copy[offset] = -3;
                        copy[index] = 0;
                        if (!Game.kingChecked(false, copy))
                            moves.add(offset);
                    }
                }
                copy = copyBoard(board);
            }
        }
        return moves;
    }

    private static boolean isValidSquare(byte rank, byte file) {
        return rank >= 0 && rank < 8 && file >= 0 && file < 8;
    }

    private static boolean isValidSquare(int index) {
        return index >= 0 && index < 64;
    }
}
