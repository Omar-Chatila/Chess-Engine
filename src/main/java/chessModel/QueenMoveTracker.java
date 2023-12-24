package chessModel;

import java.util.ArrayList;
import java.util.List;

import static chessModel.GameHelper.copyBoard;

public class QueenMoveTracker {
    private static final int[] dx = {-1, 1, -1, 1, 0, 0, 1, -1};
    private static final int[] dy = {-1, 1, 1, -1, 1, -1, 0, 0};

    public static boolean validateQueen(byte[][] board, String move, boolean white) {
        if (!move.contains("x") && move.length() == 4) {
            if (Character.isDigit(move.charAt(1))) {
                int movingRank = 8 - Character.getNumericValue(move.charAt(1));
                int file = move.charAt(2) - 'a';
                int rank = 8 - Character.getNumericValue(move.charAt(3));
                return validateQueenHelper(board, rank, file, white, 100, movingRank);
            } else {
                int movingFile = move.charAt(1) - 'a';
                int file = move.charAt(2) - 'a';
                int rank = 8 - Character.getNumericValue(move.charAt(3));
                return validateQueenHelper(board, rank, file, white, movingFile, 100);
            }
        } else if (move.contains("x") && move.length() == 5) {
            if (Character.isDigit(move.charAt(1))) {
                int movingRank = 8 - Character.getNumericValue(move.charAt(1));
                int file = move.charAt(3) - 'a';
                int rank = 8 - Character.getNumericValue(move.charAt(4));
                if (!white && board[rank][file] > 0)
                    return validateQueenHelper(board, rank, file, false, 100, movingRank);
                else if (white && board[rank][file] < 0) {
                    return validateQueenHelper(board, rank, file, true, 100, movingRank);
                }
            } else {
                int movingFile = move.charAt(1) - 'a';
                int file = move.charAt(3) - 'a';
                int rank = 8 - Character.getNumericValue(move.charAt(4));
                if (!white && board[rank][file] > 0)
                    return validateQueenHelper(board, rank, file, false, movingFile, 100);
                else if (white && board[rank][file] < 0)
                    return validateQueenHelper(board, rank, file, true, movingFile, 100);
            }
        } else {
            if (!move.contains("x")) {
                int file = move.charAt(1) - 'a';
                int rank = 8 - Character.getNumericValue(move.charAt(2));
                return validateQueenHelper(board, rank, file, white);
            } else {
                int file = move.charAt(2) - 'a';
                int rank = 8 - Character.getNumericValue(move.charAt(3));
                if (!white && board[rank][file] > 0) {
                    return validateQueenHelper(board, rank, file, false);
                } else if (white && board[rank][file] < 0) {
                    return validateQueenHelper(board, rank, file, true);
                }
            }
        }
        return false;
    }


    public static List<String> possibleMoves(byte[][] board, int rank, int file, boolean white) {
        if (!white) {
            rank = 7 - rank;
            file = 7 - file;
        }
        List<String> moves = new ArrayList<>();
        byte[][] copy = copyBoard(board);
        for (int d = 0; d < 8; d++) {
            int i = 1;
            while (isValidSquare(rank + i * dy[d], file + i * dx[d])) {
                byte squareContent = copy[rank + i * dy[d]][file + i * dx[d]];
                String toAdd = (rank + i * dy[d]) + "" + (file + i * dx[d]);
                if (white && squareContent > 0) break;
                if (!white && squareContent < 0) break;
                if (squareContent == 0) {
                    copy[rank + i * dy[d]][file + i * dx[d]] = (byte) (white ? 9 : -9);
                    copy[rank][file] = 0;
                    if (!Game.kingChecked(white, copy)) {
                        if (white) {
                            moves.add(toAdd);
                        } else {
                            moves.add((7 - (rank + i * dy[d])) + "" + (7 - (file + i * dx[d])));
                        }
                    }
                } else if (white) {
                    copy[rank + i * dy[d]][file + i * dx[d]] = 9;
                    copy[rank][file] = 0;
                    if (!Game.kingChecked(true, copy))
                        moves.add(toAdd);
                    break;
                } else {
                    copy[rank + i * dy[d]][file + i * dx[d]] = -9;
                    copy[rank][file] = 0;
                    if (!Game.kingChecked(false, copy))
                        moves.add((7 - (rank + i * dy[d])) + "" + (7 - (file + i * dx[d])));
                    break;
                }
                i++;
                copy = copyBoard(board);
            }
        }
        return moves;
    }

    private static boolean validateQueenHelper(byte[][] board, int rank, int file, boolean white) {
        return validateQueenHelper(board, rank, file, white, 100, 100);
    }

    private static boolean validateQueenHelper(byte[][] board, int rank, int file, boolean white, int myFile, int myRank) {
        for (int d = 0; d < 8; d++) {
            int i = 1;
            while (isValidSquare(rank + i * dy[d], file + i * dx[d])) {
                byte squareContent = board[rank + i * dy[d]][file + i * dx[d]];
                if (squareContent == 9 && white) {
                    if ((myFile > 10 && myRank > 10) || (myFile < 10 && (file + i * dx[d]) == myFile) || (myRank < 10 && (rank + i * dy[d]) == myRank)) {
                        byte[][] copy = copyBoard(board);
                        copy[rank + i * dy[d]][file + i * dx[d]] = 0;
                        copy[rank][file] = 9;
                        if (!Game.kingChecked(true, copy)) {
                            Game.board = copy;
                            return true;
                        } else {
                            return false;
                        }
                    }
                } else if (squareContent == -9 && !white) {
                    if ((myFile > 10 && myRank > 10) || (myFile < 10 && (file + i * dx[d]) == myFile) || (myRank < 10 && (rank + i * dy[d]) == myRank)) {
                        byte[][] copy = copyBoard(board);
                        copy[rank + i * dy[d]][file + i * dx[d]] = 0;
                        copy[rank][file] = -9;
                        if (!Game.kingChecked(false, copy)) {
                            Game.board = copy;
                            return true;
                        } else {
                            return false;
                        }
                    }
                } else if (squareContent != 0) {
                    break;
                }
                i++;
            }
        }
        return false;
    }

    public static boolean checksKing(byte[][] board, int rank, int file, boolean white) {
        for (int d = 0; d < 8; d++) {
            int i = 1;
            while (isValidSquare(rank + i * dy[d], file + i * dx[d])) {
                byte squareContent = board[rank + i * dy[d]][file + i * dx[d]];
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

    public static List<String> possibleMovesLogic(byte[][] board, int rank, int file, boolean white) {
        List<String> moves = new ArrayList<>();
        byte[][] copy = copyBoard(board);
        for (int d = 0; d < 8; d++) {
            int i = 1;
            while (isValidSquare(rank + i * dy[d], file + i * dx[d])) {
                byte squareContent = copy[rank + i * dy[d]][file + i * dx[d]];
                String toAdd = (rank + i * dy[d]) + "" + (file + i * dx[d]);
                if (white && squareContent > 0) break;
                if (!white && squareContent < 0) break;
                if (squareContent == 0) {
                    copy[rank + i * dy[d]][file + i * dx[d]] = (byte) (white ? 9 : -9);
                    copy[rank][file] = 0;
                    if (!Game.kingChecked(white, copy)) {
                        if (white) {
                            moves.add(toAdd);
                        } else {
                            moves.add(((rank + i * dy[d])) + "" + ((file + i * dx[d])));
                        }
                    }
                } else if (white) {
                    copy[rank + i * dy[d]][file + i * dx[d]] = 9;
                    copy[rank][file] = 0;
                    if (!Game.kingChecked(true, copy))
                        moves.add(toAdd);
                    break;
                } else {
                    copy[rank + i * dy[d]][file + i * dx[d]] = -9;
                    copy[rank][file] = 0;
                    if (!Game.kingChecked(false, copy))
                        moves.add(((rank + i * dy[d])) + "" + ((file + i * dx[d])));
                    break;
                }
                i++;
                copy = copyBoard(board);
            }
        }
        return moves;
    }

    private static boolean isValidSquare(int rank, int file) {
        return rank >= 0 && rank < 8 && file >= 0 && file < 8;
    }
}
