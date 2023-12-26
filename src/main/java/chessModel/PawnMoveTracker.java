package chessModel;

import java.util.ArrayList;
import java.util.List;

import static chessModel.GameHelper.copyBoard;

public class PawnMoveTracker {
    private static List<Integer> possibleMovesLogicList = new ArrayList<>();

    public static boolean checksKing(byte[] board, int index, boolean white) {
        if (!white) {
            if (isValidSquare(index - 9) && board[index - 9] == -100) {
                return true;
            } else return isValidSquare(index - 7) && board[index - 7] == -100;
        } else {
            if (isValidSquare(index + 7) && board[index + 7] == 100) {
                return true;
            } else return isValidSquare(index + 9) && board[index + 9] == 100;
        }
    }

    public static List<Integer> possibleMovesLogic(byte[] board, int index, boolean white) {
        possibleMoves(board, index, white);
        return possibleMovesLogicList;
    }

    public static void possibleMoves(byte[] board, int index, boolean white) {
        possibleMovesLogicList = new ArrayList<>();
        //Check if king is already checked in position
        boolean checked = Game.kingChecked(white, board);
        byte[] copy = copyBoard(board);
        copy[index] = 0;
        //check if pawn is pinned
        boolean pinned = Game.kingChecked(white, copy);

        if (white) {
            int step = index - 9;
            if (isValidSquare(step) && board[step] < 0) {
                copy[step] = 1;
                copy[index] = 0;
                if ((pinned || checked) && !Game.kingChecked(true, copy)) {
                    possibleMovesLogicList.add(step);
                    if (pinned) return;
                } else if (!pinned) {
                    possibleMovesLogicList.add(step);
                }
            }
            if (pinned || checked) copy = copyBoard(board);
            int step2 = index - 7;
            if (isValidSquare(step2) && board[step2] < 0) {
                copy[step2] = 1;
                copy[index] = 0;
                if ((pinned || checked) && !Game.kingChecked(true, copy)) {
                    possibleMovesLogicList.add(step2);
                } else if (!pinned) {
                    possibleMovesLogicList.add(step2);
                }
            }

            //TODO en passant
        } else {
            int step = index + 7;
            if (isValidSquare(step) && board[step] > 0) {
                copy[step] = -1;
                copy[index] = 0;
                if (!Game.kingChecked(false, copy)) {
                    possibleMovesLogicList.add(step);
                    return;
                }
            }
            copy = copyBoard(board);
            int step2 = index + 9;
            if (isValidSquare(step2) && board[step2] > 0) {
                copy[step2] = -1;
                copy[index] = 0;
                if (!Game.kingChecked(false, copy)) {
                    possibleMovesLogicList.add(step2);
                    return;
                }
            }
        }


        copy[index] = (byte) (white ? 1 : -1);
        int direction = white ? -8 : 8;
        int startRank = white ? 6 : 1;
        if (isValidSquare(index + direction) && board[index + direction] == 0) {
            int move = index + direction;
            copy[move] = (byte) (white ? 1 : -1);
            copy[index] = 0;
            if (checked && !Game.kingChecked(white, copy)) {
                possibleMovesLogicList.add(move);
            } else if (!checked) {
                possibleMovesLogicList.add(move);
            }
        }
        if (checked) copy = copyBoard(board);
        if (index / 8 == startRank) {
            int twoStep = index + direction * 2;
            int onestep = index + direction;
            if (board[twoStep] == 0 && board[onestep] == 0) {
                copy[twoStep] = (byte) (white ? 1 : -1);
                copy[index] = 0;
                if (checked && !Game.kingChecked(white, copy)) {
                    possibleMovesLogicList.add(twoStep);
                } else if (!checked) {
                    possibleMovesLogicList.add(twoStep);
                }
            }
        }

    }

    private static boolean isValidSquare(int index) {
        return index >= 0 && index < 64;
    }
}
