package chessModel;

import java.util.ArrayList;
import java.util.List;

import static chessModel.GameHelper.copyBoard;

public class RookMoveTracker {

    public static boolean checksKing(byte[] board, int index, boolean white) {
        for (int d = 0; d < 4; d++) {
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

    private static final int[] off = {8, -8, 1, -1};

    public static List<Integer> possibleMovesLogic(byte[] board, int index, boolean white) {
        List<Integer> moves = new ArrayList<>();
        byte[] copy = copyBoard(board);
        for (int d = 0; d < 4; d++) {
            if (index % 8 == 0 && d == 3 || index % 8 == 7 && d == 2 || index / 8 == 0 && d == 1 || index / 8 == 7 && d == 0)
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
                    if (!Game.kingChecked(white, copy)) {
                        if (white) {
                            moves.add(offset);
                        } else {
                            moves.add(offset);
                        }
                    }
                } else if (white) {
                    copy[offset] = 5;
                    copy[index] = 0;
                    if (!Game.kingChecked(true, copy))
                        moves.add(offset);
                    break;
                } else {
                    copy[offset] = -5;
                    copy[index] = 0;
                    if (!Game.kingChecked(false, copy))
                        moves.add(offset);
                    break;
                }
                int file = offset % 8;
                int rank = offset / 8;
                if (d == 0 && rank == 7 || d == 1 && rank == 0
                        || d == 2 && file == 7 || d == 3 && file == 0) break;
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
