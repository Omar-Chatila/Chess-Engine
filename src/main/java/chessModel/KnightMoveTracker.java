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
        copy[index] = 0;
        int rank = index / 8;
        int file = index % 8;
        boolean checked = Game.kingChecked(white, board);
        boolean pinned = !checked && Game.kingChecked(white, copy);
        if (pinned) return moves;
        for (byte d = 0; d < 8; d++) {
            int offset = index + off[d];
            if (isValidSquare(offset)) {
                int destRank = offset / 8;
                int destFile = offset % 8;
                if (Math.abs(destRank - rank) <= 2 && Math.abs(destFile - file) <= 2) {
                    byte squareContent = copy[offset];
                    if (white && squareContent > 0) continue;
                    if (!white && squareContent < 0) continue;
                    if (!checked) {
                        moves.add(offset);
                    } else {
                        copy[offset] = (byte) (white ? 3 : -3);
                        if (!Game.kingChecked(white, copy)) moves.add(offset);
                    }

                }
            }
        }
        return moves;
    }

    private static boolean isValidSquare(int index) {
        return index >= 0 && index < 64;
    }
}
