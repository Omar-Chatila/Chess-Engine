package chessModel;

import java.util.ArrayList;
import java.util.List;

import static chessModel.GameHelper.copyBoard;

public class QueenMoveTracker {

    public static boolean checksKing(byte[] board, int index, boolean white) {
        for (int d = 0; d < 8; d++) {
            int i = 1;
            while (isValidSquare(index + i * off[d])) {
                int offset = index + i * off[d];
                byte squareContent = board[offset];
                if (!white && squareContent == -100) {
                    return true;
                } else if (white && squareContent == 100) {
                    return true;
                } else if (squareContent != 0) {
                    break;
                }
                i++;
            }
        }
        return false;
    }

    private static final int[] off = {-9, 9, 7, -7, 8, -8, 1, -1};

    public static List<Integer> possibleMovesLogic(byte[] board, int index, boolean white) {
        List<Integer> moves = new ArrayList<>();
        byte[] copy = copyBoard(board);
        int f = index % 8;
        int r = index / 8;
        for (int d = 0; d < 8; d++) {
            if (f == 0 && d == 7 || f == 7 && d == 6 || r == 0 && d == 5 || r == 7 && d == 4
                    || ((f == 7 || r == 0) && d == 3) || ((f == 0 || r == 7) && d == 2) ||
                    ((f == 7 || r == f) && d == 1) || ((f == 0 || r == 0) && d == 0))
                continue;
            int i = 1;
            while (isValidSquare(index + i * off[d])) {
                int offset = index + i * off[d];
                byte squareContent = copy[offset];
                if (white && squareContent > 0) break;
                if (!white && squareContent < 0) break;
                if (squareContent == 0) {
                    copy[offset] = (byte) (white ? 9 : -9);
                    copy[index] = 0;
                    if (!Game.kingChecked(white, copy)) {
                        if (white) {
                            moves.add(offset);
                        } else {
                            moves.add(offset);
                        }
                    }
                } else if (white) {
                    copy[offset] = 9;
                    copy[index] = 0;
                    if (!Game.kingChecked(true, copy))
                        moves.add(offset);
                    break;
                } else {
                    copy[offset] = -9;
                    copy[index] = 0;
                    if (!Game.kingChecked(false, copy))
                        moves.add(offset);
                    break;
                }
                int file = offset % 8;
                int rank = offset / 8;
                if (d == 0 && (file == 0 || rank == 0) || d == 1 && (file == 7 || rank == 7) || d == 2 && (file == 0 || rank == 7)
                        || d == 3 && (file == 7 || rank == 0)
                        || d == 4 && rank == 7 || d == 5 && rank == 0
                        || d == 6 && file == 7 || d == 7 && file == 0) break;
                i++;
                copy = copyBoard(board);
            }
        }
        return moves;
    }


    private static boolean isValidSquare(int index) {
        return index >= 0 && index < 64;
    }
}
