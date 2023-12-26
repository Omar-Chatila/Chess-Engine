package chessModel;

import java.util.ArrayList;
import java.util.List;

import static chessModel.GameHelper.copyBoard;

public class BishopMoveTracker {
    private static final int[] dx = {-1, 1, -1, 1};
    private static final int[] dy = {-1, 1, 1, -1};

    private static void whitesOppPiece(int index, boolean white, List<Integer> moves, byte[] copy, int d, int i, byte squareContent, int toAdd) {
        int offset = index + i * off[d];
        if (white && squareContent < 0) {
            copy[offset] = 4;
            copy[index] = 0;
            if (!Game.kingChecked(true, copy))
                moves.add(toAdd);
        } else if (!white && squareContent > 0) {
            copy[offset] = -4;
            copy[index] = 0;
            if (!Game.kingChecked(false, copy))
                moves.add(offset);
        }
    }


    public static List<Integer> possibleMovesLogic(byte[] board, int index, boolean white) {
        List<Integer> moves = new ArrayList<>();
        byte[] copy = copyBoard(board);
        for (int d = 0; d < 4; d++) {
            int i = 1;
            while (isValidSquare(index + i * off[d])) {
                int offset = index + i * off[d];
                byte squareContent = copy[offset];
                if (white && squareContent > 0 || !white && squareContent < 0) break;
                if (squareContent == 0) {
                    copy[offset] = (byte) (white ? 4 : -4);
                    copy[index] = 0;
                    if (!Game.kingChecked(white, copy)) {
                        if (white) {
                            moves.add(offset);
                        } else {
                            moves.add(offset);
                        }
                    }
                } else {
                    whitesOppPiece(index, white, moves, copy, d, i, squareContent, offset);
                    break;
                }
                int file = offset % 8;
                int rank = offset / 8;
                if (d == 0 && (file == 0 || rank == 0) || d == 1 && (file == 7 || rank == 7) || d == 2 && (file == 0 || rank == 7)
                        || d == 3 && (file == 7 || rank == 0)) break;
                i++;
                copy = copyBoard(board);
            }
        }
        return moves;
    }

    private static final int[] off = {-9, 9, 7, -7};

    public static boolean checksKing(byte[] board, int index, boolean white) {
        for (int d = 0; d < 4; d++) {
            int i = 1;
            while (isValidSquare(index + i * dy[d] + i * dx[d])) {
                int offset = index + i * dy[d] + i * dx[d];
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

    private static boolean isValidSquare(int index) {
        return index >= 0 && index < 64;
    }
}
