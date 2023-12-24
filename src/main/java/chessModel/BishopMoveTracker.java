package chessModel;

import java.util.ArrayList;
import java.util.List;

import static chessModel.GameHelper.copyBoard;

public class BishopMoveTracker {
    private static final int[] dx = {-1, 1, -1, 1};
    private static final int[] dy = {-1, 1, 1, -1};

    public static boolean validateBishop(byte[][] board, String move, boolean white) {
        if (!move.contains("x")) {
            int file = move.charAt(1) - 'a';
            int rank = 8 - Character.getNumericValue(move.charAt(2));
            return validateBishopHelper(board, rank, file, white);
        } else {
            int file = move.charAt(2) - 'a';
            int rank = 8 - Character.getNumericValue(move.charAt(3));
            if (!white && board[rank][file] > 0) {
                return validateBishopHelper(board, rank, file, false);
            } else if (white && board[rank][file] < 0) {
                return validateBishopHelper(board, rank, file, true);
            }
        }
        return false;
    }

    private static boolean validateBishopHelper(byte[][] board, int rank, int file, boolean white) {
        for (int d = 0; d < 4; d++) {
            int i = 1;
            while (isValidSquare(rank + i * dy[d], file + i * dx[d])) {
                byte squareContent = board[rank + i * dy[d]][file + i * dx[d]];
                if (squareContent == 4 && white) {
                    byte[][] copy = copyBoard(board);
                    copy[rank + i * dy[d]][file + i * dx[d]] = 0;
                    copy[rank][file] = 4;
                    if (!Game.kingChecked(true, copy)) {
                        Game.board = copy;
                        return true;
                    } else {
                        return false;
                    }
                } else if (squareContent == -4 && !white) {
                    byte[][] copy = copyBoard(board);
                    copy[rank + i * dy[d]][file + i * dx[d]] = 0;
                    copy[rank][file] = -4;
                    if (!Game.kingChecked(false, copy)) {
                        Game.board = copy;
                        return true;
                    } else {
                        return false;
                    }
                } else if (squareContent != 0) {
                    break;
                }
                i++;

            }
        }
        return false;
    }

    private static void whitesOppPiece(int rank, int file, boolean white, List<String> moves, byte[][] copy, int d, int i, byte squareContent, String toAdd) {
        if (white && squareContent < 0) {
            copy[rank + i * dy[d]][file + i * dx[d]] = 4;
            copy[rank][file] = 0;
            if (!Game.kingChecked(true, copy))
                moves.add(toAdd);
        } else if (!white && squareContent > 0) {
            copy[rank + i * dy[d]][file + i * dx[d]] = -4;
            copy[rank][file] = 0;
            if (!Game.kingChecked(false, copy))
                moves.add(((rank + i * dy[d])) + "" + ((file + i * dx[d])));

        }
    }

    public static List<String> possibleMovesLogic(byte[][] board, int rank, int file, boolean white) {
        List<String> moves = new ArrayList<>();
        byte[][] copy = copyBoard(board);
        for (int d = 0; d < 4; d++) {
            int i = 1;
            while (isValidSquare(rank + i * dy[d], file + i * dx[d])) {
                byte squareContent = copy[rank + i * dy[d]][file + i * dx[d]];
                String toAdd = (rank + i * dy[d]) + "" + (file + i * dx[d]);
                if (white && squareContent > 0) break;
                if (!white && squareContent < 0) break;
                if (squareContent == 0) {
                    copy[rank + i * dy[d]][file + i * dx[d]] = (byte) (white ? 4 : -4);
                    copy[rank][file] = 0;
                    if (!Game.kingChecked(white, copy)) {
                        if (white) {
                            moves.add(toAdd);
                        } else {
                            moves.add(((rank + i * dy[d])) + "" + ((file + i * dx[d])));
                        }
                    }
                } else {
                    whitesOppPiece(rank, file, white, moves, copy, d, i, squareContent, toAdd);
                    break;
                }
                i++;
                copy = copyBoard(board);
            }
        }
        return moves;
    }

    public static boolean checksKing(byte[][] board, int rank, int file, boolean white) {
        for (int d = 0; d < 4; d++) {
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

    public static List<String> possibleMoves(byte[][] board, int rank, int file, boolean white) {
        if (!white) {
            rank = 7 - rank;
            file = 7 - file;
        }
        List<String> moves = new ArrayList<>();
        byte[][] copy = copyBoard(board);
        for (int d = 0; d < 4; d++) {
            int i = 1;
            while (isValidSquare(rank + i * dy[d], file + i * dx[d])) {
                byte squareContent = copy[rank + i * dy[d]][file + i * dx[d]];
                String toAdd = (rank + i * dy[d]) + "" + (file + i * dx[d]);
                if (white && squareContent > 0) break;
                if (!white && squareContent < 0) break;
                if (squareContent == 0) {
                    System.out.println("heir");
                    copy[rank + i * dy[d]][file + i * dx[d]] = (byte) (white ? 4 : -4);
                    copy[rank][file] = 0;
                    if (!Game.kingChecked(white, copy)) {
                        if (white) {
                            moves.add(toAdd);
                        } else {
                            moves.add((7 - (rank + i * dy[d])) + "" + (7 - (file + i * dx[d])));
                        }
                    }
                } else {
                    whitesOppPiece(rank, file, white, moves, copy, d, i, squareContent, toAdd);
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
