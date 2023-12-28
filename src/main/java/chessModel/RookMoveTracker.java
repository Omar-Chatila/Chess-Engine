package chessModel;

import java.util.ArrayList;
import java.util.List;

import static chessModel.GameHelper.copyBoard;

public class RookMoveTracker {

    public static boolean checksKing(byte[] board, int index, boolean white) {
        int f = index % 8;
        int r = index / 8;
        for (int d = 0; d < 4; d++) {
            int i = 1;
            if (reachedRim(d, f, r)) continue;
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
                int file = offset % 8;
                int rank = offset / 8;
                if (d == 0 && rank == 7 || d == 1 && rank == 0
                        || d == 2 && file == 7 || d == 3 && file == 0) break;
                i++;
            }
        }
        return false;
    }

    private static boolean reachedRim(int d, int f, int r) {
        return f == 0 && d == 3 || f == 7 && d == 2 || r == 0 && d == 1 || r == 7 && d == 0;
    }

    private static final int[] off = {8, -8, 1, -1};

    public static List<Integer> possibleMovesLogic(byte[] board, int index, boolean white) {
        int f = index % 8;
        int r = index / 8;
        List<Integer> moves = new ArrayList<>();
        byte[] copy = copyBoard(board);
        copy[index] = 0;
        boolean checked = Game.kingChecked(white, board);
        boolean pinned = !checked && Game.kingChecked(white, copy);
        for (int d = 0; d < 4; d++) {
            if (reachedRim(d, f, r))
                continue;
            int i = 1;
            while (isValidSquare(index + i * off[d])) {
                int offset = index + i * off[d];
                byte squareContent = copy[offset];
                if (white && squareContent > 0) break;
                if (!white && squareContent < 0) break;
                if (squareContent == 0) {
                    copy[offset] = (byte) (white ? 5 : -5);
                    copy[index] = 0;
                    if ((pinned || checked) && !Game.kingChecked(white, copy)) {
                        moves.add(offset);
                    } else if (!pinned) {
                        moves.add(offset);
                    }
                } else if (white) {
                    copy[offset] = 5;
                    copy[index] = 0;
                    if ((pinned || checked) && !Game.kingChecked(true, copy))
                        moves.add(offset);
                    else if (!pinned) moves.add(offset);
                    break;
                } else {
                    copy[offset] = -5;
                    copy[index] = 0;
                    if ((pinned || checked) && !Game.kingChecked(false, copy))
                        moves.add(offset);
                    else if (!pinned) {
                        moves.add(offset);
                    }
                    break;
                }
                int file = offset % 8;
                int rank = offset / 8;
                if (d == 0 && rank == 7 || d == 1 && rank == 0
                        || d == 2 && file == 7 || d == 3 && file == 0) break;
                i++;
                if (pinned) copy = copyBoard(board);
            }
        }
        return moves;
    }

    private static boolean isValidSquare(int index) {
        return index >= 0 && index < 64;
    }
}
