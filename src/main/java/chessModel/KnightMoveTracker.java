package chessModel;

import java.util.ArrayList;
import java.util.List;

import static chessModel.GameHelper.copyBoard;

public class KnightMoveTracker {
    private static final byte[] offsetY = {-2, -1, 1, 2, -2, -1, 1, 2};
    private static final byte[] offsetX = {-1, -2, -2, -1, 1, 2, 2, 1};

    public static boolean validateKnight(byte[][] board, String move, boolean white) {
        byte file = (byte) (move.charAt(move.contains("x") ? 2 : 1) - 'a');
        char x = move.charAt(move.contains("x") ? 3 : 2);
        byte rank = (byte) (8 - Character.getNumericValue(x));
        byte startFile = -1;
        byte startRank = -1;
        if (move.matches("N[a-h][a-h][1-8]")) {
            startFile = (byte) (move.charAt(1) - 'a');
            rank = (byte) (8 - Character.getNumericValue(move.charAt(move.contains("x") ? 4 : 3)));
            file = (byte) (x - 'a');
        } else if (move.matches("N[1-8][a-h][1-8]")) {
            startRank = (byte) (8 - Character.getNumericValue(move.charAt(1)));
            rank = (byte) (8 - Character.getNumericValue(move.charAt(move.contains("x") ? 4 : 3)));
            file = (byte) (x - 'a');
        }
        for (byte i = 0; i < 8; i++) {
            byte rankY = (byte) (rank + offsetY[i]);
            byte fileX = (byte) (file + offsetX[i]);
            if (isValidSquare(rankY, fileX)) {
                boolean isWhiteKnight = board[rankY][fileX] == 3;
                boolean isBlackKnight = board[rankY][fileX] == -3;
                if ((isWhiteKnight && white || isBlackKnight && !white)
                        && ((move.contains("x") && white && board[rank][file] < 0
                        || move.contains("x") && !white && board[rank][file] > 0)
                        || !move.contains("x"))) {
                    if ((move.matches("N[a-h][a-h][1-8]") && fileX == startFile) ||
                            (move.matches("N[1-8][a-h][1-8]") && rankY == startRank) ||
                            (!move.matches("N[a-h][a-h][1-8]") && !move.matches("N[1-8][a-h][1-8]"))) {
                        byte[][] copy = copyBoard(board);
                        copy[rankY][fileX] = 0;
                        copy[rank][file] = (byte) (white ? 3 : -3);
                        if (!Game.kingChecked(white, copy)) {
                            Game.board = copy;
                            return true;
                        } else {
                            return false;
                        }
                    }
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
            if (isValidSquare((byte) (rank + offsetY[d]), (byte) (file + offsetX[d]))) {
                byte squareContent = copy[rank + offsetY[d]][file + offsetX[d]];
                String toAdd = (rank + offsetY[d]) + "" + (file + offsetX[d]);
                if (white && squareContent > 0) continue;
                if (!white && squareContent<0) continue;
                if (squareContent == 0) {
                    copy[rank + offsetY[d]][file + offsetX[d]] = (byte) (white ? 3 : -3);
                    copy[rank][file] = 0;
                    if (!Game.kingChecked(white, copy)) {
                        if (white) {
                            moves.add(toAdd);
                        } else {
                            moves.add((7 - (rank + offsetY[d])) + "" + (7 - (file + offsetX[d])));
                        }
                    }
                } else if (white) {
                    copy[rank + offsetY[d]][file + offsetX[d]] = 3;
                    copy[rank][file] = 0;
                    if (!Game.kingChecked(true, copy))
                        moves.add(toAdd);
                } else {
                    copy[rank + offsetY[d]][file + offsetX[d]] = -3;
                    copy[rank][file] = 0;
                    if (!Game.kingChecked(false, copy))
                        moves.add((7 - (rank + offsetY[d])) + "" + (7 - (file + offsetX[d])));
                }
                copy = copyBoard(board);
            }
        }
        return moves;
    }

    public static boolean checksKing(byte[][] board, byte rank, byte file, boolean white) {
        for (byte d = 0; d < 8; d++) {
            if (isValidSquare((byte) (rank + offsetY[d]), (byte) (file + offsetX[d]))) {
                byte squareContent = board[rank + offsetY[d]][file + offsetX[d]];
                if (!white && squareContent == -100) {
                    return true;
                } else if (white && squareContent == 100) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<String> possibleMovesLogic(byte[][] board, byte rank, byte file, boolean white) {
        List<String> moves = new ArrayList<>();
        byte[][] copy = copyBoard(board);
        for (byte d = 0; d < 8; d++) {
            if (isValidSquare((byte) (rank + offsetY[d]), (byte) (file + offsetX[d]))) {
                byte squareContent = copy[rank + offsetY[d]][file + offsetX[d]];
                String toAdd = (rank + offsetY[d]) + "" + (file + offsetX[d]);
                if (white && squareContent > 0) continue;
                if (!white && squareContent < 0) continue;
                if (squareContent == 0) {
                    copy[rank + offsetY[d]][file + offsetX[d]] = (byte) (white ? 3 : -3);
                    copy[rank][file] = 0;
                    if (!Game.kingChecked(white, copy)) {
                        moves.add(toAdd);
                    }
                } else if (white) {
                    copy[rank + offsetY[d]][file + offsetX[d]] = 3;
                    copy[rank][file] = 0;
                    if (!Game.kingChecked(true, copy))
                        moves.add(toAdd);
                } else {
                    copy[rank + offsetY[d]][file + offsetX[d]] = -3;
                    copy[rank][file] = 0;
                    if (!Game.kingChecked(false, copy))
                        moves.add(((rank + offsetY[d])) + "" + ((file + offsetX[d])));
                }
            }
            copy = copyBoard(board);
        }
        return moves;
    }

    private static boolean isValidSquare(byte rank, byte file) {
        return rank >= 0 && rank < 8 && file >= 0 && file < 8;
    }
}
