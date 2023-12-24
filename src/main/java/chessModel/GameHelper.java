package chessModel;

import java.util.ArrayList;
import java.util.List;

public class GameHelper {
    public static byte[][] copyBoard(byte[][] board) {
        byte[][] copy = new byte[8][8];
        for (int i = 0; i < 8; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, 8);
        }
        return copy;
    }

    public static void print(byte[][] board) {
        System.out.println("-----------------------------");
        for (byte[] a : board) {
            for (byte s : a) {
                switch (s) {
                    case 1 -> System.out.print("P ");
                    case 3 -> System.out.print("N ");
                    case 4 -> System.out.print("B ");
                    case 5 -> System.out.print("R ");
                    case 9 -> System.out.print("Q ");
                    case 100 -> System.out.print("K ");
                    case -1 -> System.out.print("p ");
                    case -3 -> System.out.print("n ");
                    case -4 -> System.out.print("b ");
                    case -5 -> System.out.print("r ");
                    case -9 -> System.out.print("q ");
                    case -100 -> System.out.print("k ");
                    case 0 -> System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println("-----------------------------");
        System.out.println();
    }

    public static IntIntPair numberOfPieces(byte[][] board) {
        int mytally = 0;
        int engtally = 0;
        for (byte[] arr : board) {
            for (byte piece : arr) {
                if (piece > 0) {
                    mytally++;
                } else if (piece < 0) {
                    engtally++;
                }
            }
        }
        return new IntIntPair(mytally, engtally);
    }

    public static IntIntPair numberOfPieces() {
        return numberOfPieces(Game.board);
    }

    public static void initialize(byte[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (i != 1 && i != 6) {
                    board[i][j] = 0;
                } else if (i == 1) {
                    board[i][j] = -1;
                } else {
                    board[i][j] = 1;
                }
            }
        }
        board[0][0] = -5;
        board[0][1] = -3;
        board[0][2] = -4;
        board[0][3] = -9;
        board[0][4] = -100;
        board[0][5] = -4;
        board[0][6] = -3;
        board[0][7] = -5;

        board[7][0] = 5;
        board[7][1] = 3;
        board[7][2] = 4;
        board[7][3] = 9;
        board[7][4] = 100;
        board[7][5] = 4;
        board[7][6] = 3;
        board[7][7] = 5;
        Game.playedPositions.add(copyBoard(board));
    }

    public static List<Byte> boardToList(byte[][] board) {
        List<Byte> list = new ArrayList<>();
        for (byte[] array : board) {
            for (int i = 0; i < 8; i++) {
                list.add(array[i]);
            }
        }
        return list;
    }

    public static String boardToString(byte[][] board) {
        StringBuilder result = new StringBuilder();
        for (byte[] a : board) {
            for (byte s : a) {
                result.append(s).append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    public static boolean boardEquals(byte[][] first, byte[][] second) {
        for (int i = 0; i < first.length; i++) {
            for (int j = 0; j < first[i].length; j++) {
                if (first[i][j] != (second[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }
}
