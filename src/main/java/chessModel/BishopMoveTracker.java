package chessModel;

import java.util.ArrayList;
import java.util.List;

import static chessModel.GameHelper.copyBoard;

public class BishopMoveTracker {
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

    private static final int[] off = {-9, 9, 7, -7};

    private static boolean reachedRim(int d, int f, int r) {
        return ((f == 7 || r == 0) && d == 3) || ((f == 0 || r == 7) && d == 2) ||
                ((f == 7 || r == f) && d == 1) || ((f == 0 || r == 0) && d == 0);
    }

    public static List<Integer> possibleMovesLogic(byte[] board, int index, boolean white) {
        List<Integer> moves = new ArrayList<>();
        byte[] copy = copyBoard(board);
        copy[index] = 0;
        boolean pinned = Game.kingChecked(white, board);
        int f = index & 0x07;
        int r = index >> 3;
        for (int d = 0; d < 4; d++) {
            if (reachedRim(d, f, r))
                continue;
            int i = 1;
            while (isValidSquare(index + i * off[d])) {
                int offset = index + i * off[d];
                byte squareContent = copy[offset];
                if (white && squareContent > 0 || !white && squareContent < 0) break;
                if (squareContent == 0) {
                    copy[offset] = (byte) (white ? 4 : -4);
                    copy[index] = 0;
                    if (pinned && !Game.kingChecked(white, copy)) {
                        moves.add(offset);
                    } else if (!pinned) {
                        moves.add(offset);
                    }
                } else {
                    whitesOppPiece(index, white, moves, copy, d, i, squareContent, offset);
                    break;
                }
                int file = offset & 0x07;
                int rank = offset >> 3;
                if (d == 0 && (file == 0 || rank == 0) || d == 1 && (file == 7 || rank == 7) || d == 2 && (file == 0 || rank == 7)
                        || d == 3 && (file == 7 || rank == 0)) break;
                i++;
                if (pinned) copy = copyBoard(board);
            }
        }
        return moves;
    }


    public static boolean checksKing(byte[] board, int index, boolean white) {
        int f = index & 0x07;
        int r = index >> 3;
        for (int d = 0; d < 4; d++) {
            if (reachedRim(d, f, r)) continue;
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
                int file = offset & 0x07;
                int rank = offset >> 3;
                if (d == 0 && (file == 0 || rank == 0) || d == 1 && (file == 7 || rank == 7) || d == 2 && (file == 0 || rank == 7)
                        || d == 3 && (file == 7 || rank == 0)) break;
                i++;
            }
        }
        return false;
    }

    private static boolean isValidSquare(int index) {
        return index >= 0 && index < 64;
    }
}
