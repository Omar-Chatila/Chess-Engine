package chessModel;

import java.util.ArrayList;
import java.util.List;

import static chessModel.GameHelper.copyBoard;

public class PawnMoveTracker {

    public static List<Integer> possibleMovesLogic(byte[] board, int index, boolean white) {
        int file = index & 0x07;
        List<Integer> possibleMovesLogicList = new ArrayList<>();
        byte[] copy = copyBoard(board);
        copy[index] = 0;
        //check if pawn is pinned
        boolean checked = Game.kingChecked(white, board);
        boolean pinned = !checked && Game.kingChecked(white, copy);

        if (white) {
            byte temp1 = 0, temp2 = 0;
            int step = index - 9;
            if (file != 0 && isValidSquare(step) && board[step] < 0) {
                if (testCase(index, possibleMovesLogicList, copy, checked, pinned, step)) return possibleMovesLogicList;
            }
            if (file != 7) {
                int step2 = index - 7;
                if (isValidSquare(step2) && board[step2] < 0) {
                    if (testCase(index, possibleMovesLogicList, copy, checked, pinned, step2))
                        return possibleMovesLogicList;
                }
            }

            //TODO en passant
        } else {
            byte temp3 = 0, temp4 = 0;
            int step = index + 7;
            if (file != 0 && isValidSquare(step) && board[step] > 0) {
                if (testCase2(index, possibleMovesLogicList, copy, checked, pinned, step))
                    return possibleMovesLogicList;
            }
            int step2 = index + 9;
            if (file != 7 && isValidSquare(step2) && board[step2] > 0) {
                if (testCase2(index, possibleMovesLogicList, copy, checked, pinned, step2))
                    return possibleMovesLogicList;
            }
        }


        copy[index] = (byte) (white ? 1 : -1);
        int direction = white ? -8 : 8;
        int startRank = white ? 6 : 1;
        if (isValidSquare(index + direction) && board[index + direction] == 0) {
            int move = index + direction;
            testCase3(index, white, possibleMovesLogicList, copy, checked, pinned, move);
            copy[move] = 0;
        }


        if (index >> 3 == startRank) {
            int twoStep = index + direction * 2;
            int onestep = index + direction;
            if (board[twoStep] == 0 && board[onestep] == 0) {
                testCase3(index, white, possibleMovesLogicList, copy, checked, pinned, twoStep);
            }
        }
        return possibleMovesLogicList;
    }

    private static void testCase3(int index, boolean white, List<Integer> possibleMovesLogicList, byte[] copy, boolean checked, boolean pinned, int move) {
        copy[move] = (byte) (white ? 1 : -1);
        copy[index] = 0;
        if ((pinned || checked) && !Game.kingChecked(white, copy)) {
            possibleMovesLogicList.add(move);
        } else if (!pinned && !checked) {
            possibleMovesLogicList.add(move);
        }
    }

    private static boolean testCase2(int index, List<Integer> possibleMovesLogicList, byte[] copy, boolean checked, boolean pinned, int step2) {
        byte temp4;
        temp4 = copy[step2];
        copy[step2] = -1;
        copy[index] = 0;
        if ((pinned || checked) && !Game.kingChecked(false, copy)) {
            possibleMovesLogicList.add(step2);
            if (pinned) return true;
        } else if (!pinned && !checked) {
            possibleMovesLogicList.add(step2);
        }
        copy[step2] = temp4;
        return false;
    }

    private static boolean testCase(int index, List<Integer> possibleMovesLogicList, byte[] copy, boolean checked, boolean pinned, int step) {
        byte temp1;
        temp1 = copy[step];
        copy[step] = 1;
        copy[index] = 0;
        if ((pinned || checked) && !Game.kingChecked(true, copy)) {
            possibleMovesLogicList.add(step);
            if (pinned) return true;
        } else if (!pinned && !checked) {
            possibleMovesLogicList.add(step);
        }
        copy[step] = temp1;
        return false;
    }

    private static boolean isValidSquare(int index) {
        return index >= 0 && index < 64;
    }
}
