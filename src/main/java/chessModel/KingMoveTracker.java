package chessModel;

import java.util.ArrayList;
import java.util.List;

import static chessModel.GameHelper.copyBoard;

public class KingMoveTracker {
    private static final int[] dx = {-1, 1, -1, 1, 0, 0, 1, -1};
    private static final int[] dy = {-1, 1, 1, -1, 1, -1, 0, 0};
    private static boolean whiteKingHasMoved;
    private static boolean blackKingHasMoved;


    public static boolean validateKing(byte[][] board, String move, boolean white) {
        if (move.equals("O-O")) {
            return castleShort(white);
        }
        if (move.equals("O-O-O")) {
            return castleLong(white);
        }
        if (!move.contains("x")) {
            int file = move.charAt(1) - 'a';
            int rank = 8 - Character.getNumericValue(move.charAt(2));
            return validateKingHelper(board, rank, file, white);
        } else {
            int file = move.charAt(2) - 'a';
            int rank = 8 - Character.getNumericValue(move.charAt(3));
            if (!white && board[rank][file] > 0) {
                return validateKingHelper(board, rank, file, false);
            } else if (white && board[rank][file] < 0) {
                return validateKingHelper(board, rank, file, true);
            }
        }
        return false;
    }

    private static boolean inKingTerritory(byte[][] copy, int rank, int file) {
        for (int i = rank - 1; i <= rank + 1; i++) {
            for (int j = file - 1; j <= file + 1; j++) {
                if (isValidSquare(i, j)) {
                    if (rank == i && file == j) continue;
                    if (copy[i][j] == 100 || copy[i][j] == -100) {
                        return true;
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
            if (isValidSquare(rank + dy[d], file + dx[d])) {
                byte squareContent = copy[rank + dy[d]][file + dx[d]];
                String toAdd = (rank + dy[d]) + "" + (file + dx[d]);
                if (white && squareContent > 0) continue;
                if (!white && squareContent < 0) continue;
                copy[rank + dy[d]][file + dx[d]] = (byte) (white ? 100 : -100);
                copy[rank][file] = 0;
                if ((white && !Game.kingChecked(true, copy) || !white && !Game.kingChecked(false, copy)) && !inKingTerritory(copy, rank + dy[d], file + dx[d])) {
                    if (white) {
                        moves.add(toAdd);
                    } else {
                        moves.add((7 - (rank + dy[d])) + "" + (7 - (file + dx[d])));
                    }
                }
                copy = copyBoard(board);
            }
        }
        return addLongCastleMoves(white, moves);
    }

    private static List<String> addLongCastleMoves(boolean white, List<String> moves) {
        if (white && hasLongCastlingRight(true)) {
            moves.add("72");
            moves.add("71");
            moves.add("70");
        }
        if (white && hasShortCastlingRight(true)) {
            moves.add("76");
            moves.add("77");
        }
        if (!white && hasLongCastlingRight(false)) {
            moves.add("75");
            moves.add("76");
            moves.add("77");
        }
        if (!white && hasShortCastlingRight(false)) {
            moves.add("71");
            moves.add("70");
        }
        return moves;
    }

    private static boolean validateKingHelper(byte[][] board, int rank, int file, boolean white) {
        for (int d = 0; d < 8; d++) {
            int i = 1;
            if (isValidSquare(rank + i * dy[d], file + i * dx[d])) {
                byte squareContent = board[rank + i * dy[d]][file + i * dx[d]];
                if (squareContent == 100 && white) {
                    byte[][] copy = copyBoard(board);
                    copy[rank + i * dy[d]][file + i * dx[d]] = 0;
                    copy[rank][file] = 100;
                    whiteKingHasMoved = true;
                    if (!Game.kingChecked(true, copy) && !inKingTerritory(copy, rank, file)) {
                        Game.board = copy;
                        return true;
                    } else {
                        return false;
                    }
                } else if (squareContent == -100 && !white) {
                    byte[][] copy = copyBoard(board);
                    copy[rank + i * dy[d]][file + i * dx[d]] = 0;
                    copy[rank][file] = -100;
                    blackKingHasMoved = true;
                    if (!Game.kingChecked(false, copy) && !inKingTerritory(copy, rank, file)) {
                        Game.board = copy;
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public static boolean hasShortCastlingRight(boolean white) {
        return hasShortCastlingRight(white, Game.board);
    }

    public static boolean hasShortCastlingRight(boolean white, byte[][] board) {
        if (white && board[7][4] == 100 || !white && board[0][4] == -100) {
            byte[][] copy1 = new byte[0][];
            byte[][] copy2 = new byte[0][];
            if (white) {
                copy1 = copyBoard(board);
                copy1[7][5] = 100;
                copy1[7][6] = 100;
            } else {
                copy2 = copyBoard(board);
                copy2[0][5] = -100;
                copy2[0][6] = -100;
            }
            boolean freeSpace = white ? board[7][4] == 100 && board[7][5] == 0 && board[7][6] == 0 && board[7][7] == 5
                    : board[0][4] == -100 && board[0][5] == 0 && board[0][6] == 0 && board[0][7] == -5;
            if (white) {
                if (!whiteKingHasMoved && freeSpace) {
                    boolean castleTroughCheck = Game.kingChecked(true, copy1);
                    return !castleTroughCheck;
                }
            } else {
                if (!blackKingHasMoved && freeSpace) {
                    boolean castleTroughCheck = Game.kingChecked(false, copy2);
                    return !castleTroughCheck;
                }
            }
        }
        return false;
    }

    private static boolean hasLongCastlingRight(boolean white) {
        return hasLongCastlingRight(white, Game.board);
    }

    private static boolean hasLongCastlingRight(boolean white, byte[][] board) {
        byte[][] copy1 = copyBoard(board);
        copy1[7][1] = 100;
        copy1[7][2] = 100;
        copy1[7][3] = 100;
        byte[][] copy2 = copyBoard(board);
        copy2[0][1] = -100;
        copy2[0][2] = -100;
        copy2[0][3] = -100;
        boolean castleTroughCheck = Game.kingChecked(white, white ? copy1 : copy2);
        boolean freeSpace = white ? board[7][4] == 100 && board[7][3] == 0 && board[7][2] == 0 && board[7][1] == 0 && board[7][0] == 5
                : board[0][4] == -100 && board[0][3] == 0 && board[0][2] == 0 && board[0][1] == 0 && board[0][0] == -5;
        if (white)
            return !whiteKingHasMoved && !Game.kingChecked(true, board) && freeSpace && !castleTroughCheck;
        else
            return !blackKingHasMoved && !Game.kingChecked(false, board) && freeSpace && !castleTroughCheck;
    }


    private static List<String> addCastlingMoves(boolean white, List<String> moves) {
        if (white && hasLongCastlingRight(true)) {
            moves.add("lc");
        }
        if (white && hasShortCastlingRight(true)) {
            moves.add("sc");
        }
        if (!white && hasLongCastlingRight(false)) {
            moves.add("lc");
        }
        if (!white && hasShortCastlingRight(false)) {
            moves.add("sc");
        }
        return moves;
    }

    private static List<String> addCastlingMoves(boolean white, List<String> moves, byte[][] board) {
        if (white && hasLongCastlingRight(true, board)) {
            moves.add("lc");
        }
        if (white && hasShortCastlingRight(true, board)) {
            moves.add("sc");
        }
        if (!white && hasLongCastlingRight(false, board)) {
            moves.add("lc");
        }
        if (!white && hasShortCastlingRight(false, board)) {
            moves.add("sc");
        }
        return moves;
    }

    public static List<String> possibleMovesLogic(byte[][] board, byte rank, byte file, boolean white) {
        List<String> moves = new ArrayList<>();
        byte[][] copy = copyBoard(board);
        for (int d = 0; d < 8; d++) {
            if (isValidSquare(rank + dy[d], file + dx[d])) {
                byte squareContent = copy[rank + dy[d]][file + dx[d]];
                String toAdd = (rank + dy[d]) + "" + (file + dx[d]);
                if (white && squareContent > 0) continue;
                if (!white && squareContent < 0) continue;
                copy[rank + dy[d]][file + dx[d]] = (byte) (white ? 100 : -100);
                copy[rank][file] = 0;
                if (!Game.kingChecked(white, copy) && !inKingTerritory(copy, rank + dy[d], file + dx[d])) {
                    moves.add(toAdd);
                }
                copy = copyBoard(board);
            }
        }
        return addCastlingMoves(white, moves, board);
    }

    private static boolean castleShort(boolean white) {
        if (hasShortCastlingRight(white)) {
            if (white) {
                Game.board[7][6] = 100;
                Game.board[7][5] = 5;
                Game.board[7][4] = 0;
                Game.board[7][7] = 0;
                byte[][] copy = copyBoard(Game.board);
                copy[7][5] = 100;
                copy[7][4] = 100;
                return !Game.kingChecked(true) && !Game.kingChecked(true, copy);
            } else {
                Game.board[0][6] = -100;
                Game.board[0][5] = -5;
                Game.board[0][4] = 0;
                Game.board[0][7] = 0;
                byte[][] copy = copyBoard(Game.board);
                copy[0][5] = -100;
                copy[0][4] = -100;
                return !Game.kingChecked(false) && !Game.kingChecked(false, copy);
            }
        }
        return false;
    }

    private static boolean castleLong(boolean white) {
        if (hasLongCastlingRight(white)) {
            if (white) {
                Game.board[7][2] = 100;
                Game.board[7][3] = 5;
                Game.board[7][4] = 0;
                Game.board[7][0] = 0;
                byte[][] copy = copyBoard(Game.board);
                copy[7][3] = 100;
                copy[7][2] = 100;
                copy[7][4] = 100;
                return !Game.kingChecked(true) && !Game.kingChecked(true, copy);
            } else {
                Game.board[0][2] = -100;
                Game.board[0][3] = -5;
                Game.board[0][4] = 0;
                Game.board[0][0] = 0;
                byte[][] copy = copyBoard(Game.board);
                copy[0][3] = -100;
                copy[0][2] = -100;
                copy[0][4] = -100;
                return !Game.kingChecked(false) && !Game.kingChecked(false, copy);
            }
        }
        return false;
    }

    private static boolean isValidSquare(int rank, int file) {
        return rank >= 0 && rank < 8 && file >= 0 && file < 8;
    }

}
