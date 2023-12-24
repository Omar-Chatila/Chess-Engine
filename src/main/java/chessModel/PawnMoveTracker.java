package chessModel;

import java.util.ArrayList;
import java.util.List;

import static chessModel.GameHelper.copyBoard;

public class PawnMoveTracker {
    private static List<String> possibleMovesLogicList = new ArrayList<>();
    private static String whiteEnpassant;
    private static String blackEnpassant;


    private static boolean validatePawn(byte[][] board, String move, boolean white) {
        if (!move.contains("x")) {
            setEnPassantSquare(move, white);
            int file = move.charAt(0) - 'a', rank = Character.getNumericValue(move.charAt(1));
            if (file < 0 || file > 7 || rank < 0 || rank > 8 || board[8 - rank][file] != 0) return false;
            if (white) {
                if (rank < 3) return false;
                return rank != 4 && board[8 - (rank - 1)][file] == 1
                        || rank == 4 && (board[8 - (rank - 1)][file] == 1 || (board[8 - (rank - 2)][file] == 1 && board[8 - (rank - 1)][file] == 0));
            } else {
                if (rank > 6) return false;
                return rank != 5 && board[8 - rank - 1][file] == -1
                        || rank == 5 && (board[8 - rank - 1][file] == -1 || (board[8 - rank - 2][file] == -1 && board[8 - rank - 1][file] == 0));
            }
        }

        if (move.contains("x") && move.length() == 4) {
            if (white && blackEnpassant != null && blackEnpassant.equals(move.substring(2))) {
                return true;
            } else if (!white && whiteEnpassant != null && whiteEnpassant.equals(move.substring(2))) {
                return true;
            }
            int pawnFile = move.charAt(0) - 'a', capFile = move.charAt(2) - 'a';
            int capRank = 8 - Character.getNumericValue(move.charAt(3));
            int pawnRank = white ? capRank + 1 : capRank - 1;
            if (pawnFile < 0 || pawnFile > 7 || pawnRank < 0 || pawnRank > 7 || capFile < 0 || capFile > 7 || capRank < 0 || capRank > 7)
                return false;
            return (white && board[pawnRank][pawnFile] == 1 && board[capRank][capFile] < 0)
                    || (!white && board[pawnRank][pawnFile] == -1 && board[capRank][capFile] > 0);
        }

        return false;
    }

    public static boolean movePawn(byte[][] board, String move, boolean white) {
        if (move.contains("=")) {
            byte[][] copy = copyBoard(board);
            int file = move.charAt(0) - 'a', rank = 8 - Character.getNumericValue(move.charAt(move.contains("x") ? 3 : 1));
            if (move.contains("x")) {
                String piece = Character.toString(move.charAt(move.length() - 1));
                String newPiece = white ? piece : piece.toLowerCase();
                byte np = map(newPiece);
                int capFile = move.charAt(2) - 'a';
                copy[rank + (white ? 1 : -1)][file] = 0;
                copy[rank][capFile] = np;
            } else {
                copy[rank + (white ? 1 : -1)][file] = 0;
                String newPiece = Character.toString(move.charAt(3));
                byte np = map(newPiece);
                copy[rank][file] = np;
            }
            if (!Game.kingChecked(white, copy)) {
                Game.board = copy;
                return true;
            }
            return false;
        }
        if (validatePawn(board, move, white) && !move.contains("x")) {
            int file = move.charAt(0) - 'a', rank = 8 - Character.getNumericValue(move.charAt(1));
            byte[][] copy = copyBoard(board);
            if (rank == (white ? 4 : 3)) {
                if (copy[rank + (white ? 2 : -2)][file] == (white ? 1 : -1) && copy[rank + (white ? 1 : -1)][file] == 0) {
                    copy[rank + (white ? 2 : -2)][file] = 0;
                } else {
                    copy[rank + (white ? 1 : -1)][file] = 0;
                }
            } else {
                copy[rank + (white ? 1 : -1)][file] = 0;
            }
            copy[rank][file] = (byte) (white ? 1 : -1);
            if (!Game.kingChecked(white, copy)) {
                Game.board = copy;
                return true;
            }
            return false;
        } else if (validatePawn(board, move, white) && move.contains("x")) {
            int pawnFile = move.charAt(0) - 'a', capFile = move.charAt(2) - 'a';
            int capRank = 8 - Character.getNumericValue(move.charAt(3));
            int pawnRank = white ? capRank + 1 : capRank - 1;
            byte[][] copy = copyBoard(board);
            if (white && blackEnpassant != null && blackEnpassant.equals(move.substring(2))) {
                copy[3][capFile] = 0;
            } else if (!white && whiteEnpassant != null && whiteEnpassant.equals(move.substring(2))) {
                copy[4][capFile] = 0;
            }
            copy[pawnRank][pawnFile] = 0;
            copy[capRank][capFile] = (byte) (white ? 1 : -1);
            if (!Game.kingChecked(white, copy)) {
                Game.board = copy;
                return true;
            }
            return false;
        }
        return false;
    }

    private static byte map(String newPiece) {
        byte np = 0;
        switch (newPiece) {
            case "r" -> np = -5;
            case "b" -> np = -4;
            case "n" -> np = -3;
            case "k" -> np = -100;
            case "q" -> np = -9;
            case "p" -> np = -1;
            case "R" -> np = 5;
            case "B" -> np = 4;
            case "N" -> np = 3;
            case "K" -> np = 100;
            case "Q" -> np = 9;
            case "P" -> np = 1;
        }
        return np;
    }

    public static void resetEnpassant() {
        if (blackEnpassant != null) {
            String move = blackEnpassant.charAt(0) + "" + 5;
            if (Game.moveList.indexOf(move) != Game.moveList.size() - 1) {
                blackEnpassant = null;
            }
        }
        if (whiteEnpassant != null) {
            String move = whiteEnpassant.charAt(0) + "" + 4;
            if (Game.moveList.indexOf(move) != Game.moveList.size() - 1) {
                whiteEnpassant = null;
            }
        }

    }

    public static boolean checksKing(byte[][] board, int rank, int file, boolean white) {
        if (!white) {
            if (isValidSquare(rank - 1, file - 1) && board[rank - 1][file - 1] == -100) {
                return true;
            } else return isValidSquare(rank - 1, file + 1) && board[rank - 1][file + 1] == -100;
        } else {
            if (isValidSquare(rank + 1, file - 1) && board[rank + 1][file - 1] == 100) {
                return true;
            } else return isValidSquare(rank + 1, file + 1) && board[rank + 1][file + 1] == 100;
        }
    }

    public static List<String> possibleMovesLogic(byte[][] board, int rank, int file, boolean white) {
        possibleMoves(board, rank, file, white);
        return possibleMovesLogicList;
    }

    public static void setEnPassantSquare(String move, boolean white) {
        if (white && move.matches("[a-h]4") && Game.board[5][move.charAt(0) - 'a'] == 0 && Game.board[6][move.charAt(0) - 'a'] == 1) {
            whiteEnpassant = move.charAt(0) + "" + 3;

        } else if (!white && move.matches("[a-h]5") && Game.board[2][move.charAt(0) - 'a'] == 0 && Game.board[1][move.charAt(0) - 'a'] == -1) {
            blackEnpassant = move.charAt(0) + "" + 6;
        } else {
            blackEnpassant = null;
            whiteEnpassant = null;
        }
    }

    public static List<String> possibleMovesGUI(byte[][] board, int rank, int file, boolean white) {
        List<String> moves = new ArrayList<>();
        byte[][] copy = copyBoard(board);
        int logicRank = rank;
        int logicFile = file;
        if (!white) {
            logicRank = 7 - logicRank;
            logicFile = 7 - logicFile;
        }
        int startRank = 6;
        int direction = white ? -1 : 1;
        if (isValidSquare(logicRank + direction, logicFile) && board[logicRank + direction][logicFile] == 0) {
            copy[logicRank + direction][logicFile] = (byte) (white ? 1 : -1);
            copy[logicRank][logicFile] = 0;
            if (white) {
                if (!Game.kingChecked(true, copy)) {
                    possibleMovesLogicList.add((logicRank + direction) + "" + logicFile);
                    moves.add((rank + direction) + "" + file);
                }
            } else {
                if (!Game.kingChecked(false, copy)) {
                    possibleMovesLogicList.add((logicRank + direction) + "" + logicFile);
                    moves.add((rank - direction) + "" + file);
                }
            }
        }
        copy = copyBoard(board);
        if (rank == startRank) {
            if (board[logicRank + direction * 2][logicFile] == 0 && board[logicRank + direction][logicFile] == 0) {
                copy[logicRank + direction * 2][logicFile] = (byte) (white ? 1 : -1);
                copy[logicRank][logicFile] = 0;
                if (white) {
                    if (!Game.kingChecked(true, copy)) {
                        possibleMovesLogicList.add((rank + direction * 2) + "" + file);
                        moves.add((rank + direction * 2) + "" + file);
                    }
                } else {
                    if (!Game.kingChecked(false, copy)) {
                        possibleMovesLogicList.add((rank + direction * 2) + "" + file);
                        moves.add((rank - (direction * 2)) + "" + file);
                    }
                }
            }
        }
        copy = copyBoard(board);
        if (white) {
            if (isValidSquare(rank - 1, file - 1) && board[rank - 1][file - 1] < 0) {
                copy[rank - 1][file - 1] = 1;
                copy[rank][file] = 0;
                if (!Game.kingChecked(true, copy)) {
                    possibleMovesLogicList.add((rank - 1) + "" + (file - 1));
                    moves.add((rank - 1) + "" + (file - 1));
                }
            }
            copy = copyBoard(board);
            if (isValidSquare(rank - 1, file + 1) && board[rank - 1][file + 1] < 0) {
                copy[rank - 1][file + 1] = 1;
                copy[rank][file] = 0;
                if (!Game.kingChecked(true, copy)) {
                    possibleMovesLogicList.add((rank - 1) + "" + (file + 1));
                    moves.add((rank - 1) + "" + (file + 1));
                }
            }
            copy = copyBoard(board);
            if (blackEnpassant != null) {
                int r = 8 - Integer.parseInt(Character.getNumericValue(blackEnpassant.charAt(1)) + "");
                int f = blackEnpassant.charAt(0) - 'a';
                copy[r][f] = 1;
                copy[rank][file] = 0;
                if (!Game.kingChecked(true, copy)) {
                    if (rank == r + 1 && Math.abs(file - f) == 1) {
                        moves.add(r + "" + f);
                        possibleMovesLogicList.add(r + "" + f);
                    }
                }
            }
        } else {
            if (isValidSquare(logicRank + 1, logicFile - 1) && board[logicRank + 1][logicFile - 1] > 0) {
                copy[logicRank + 1][logicFile - 1] = -1;
                copy[logicRank][logicFile] = 0;
                if (!Game.kingChecked(false, copy)) {
                    possibleMovesLogicList.add((logicRank + 1) + "" + (logicFile - 1));
                    moves.add(((rank - 1)) + "" + ((file + 1)));
                }
            }
            copy = copyBoard(board);
            if (isValidSquare(logicRank + 1, logicFile + 1) && board[logicRank + 1][logicFile + 1] > 0) {
                copy[logicRank + 1][logicFile + 1] = -1;
                copy[logicRank][logicFile] = 0;
                if (!Game.kingChecked(false, copy)) {
                    possibleMovesLogicList.add((logicRank + 1) + "" + (logicFile + 1));
                    moves.add(((rank - 1)) + "" + ((file - 1)));
                }
            }
            copy = copyBoard(board);
            if (whiteEnpassant != null) {
                int r = 2;
                int f = 7 - (whiteEnpassant.charAt(0) - 'a');
                copy[7 - r][7 - f] = -1;
                copy[logicRank][logicFile] = 0;
                if (!Game.kingChecked(false, copy)) {
                    if (rank == r + 1 && Math.abs(file - f) == 1) {
                        moves.add(r + "" + f);
                        possibleMovesLogicList.add((7 - r) + "" + (7 - f));
                    }
                }
            }

        }
        return moves;
    }

    public static void possibleMoves(byte[][] board, int rank, int file, boolean white) {
        possibleMovesLogicList = new ArrayList<>();
        byte[][] copy = copyBoard(board);
        int startRank = white ? 6 : 1;
        int direction = white ? -1 : 1;
        if (isValidSquare(rank + direction, file) && board[rank + direction][file] == 0) {
            copy[rank + direction][file] = (byte) (white ? 1 : -1);
            copy[rank][file] = 0;
            if (!Game.kingChecked(white, copy)) {
                possibleMovesLogicList.add((rank + direction) + "" + file);
            }
        }
        copy = copyBoard(board);
        if (rank == startRank) {
            if (board[rank + direction * 2][file] == 0 && board[rank + direction][file] == 0) {
                copy[rank + direction * 2][file] = (byte) (white ? 1 : -1);
                copy[rank][file] = 0;
                if (!Game.kingChecked(white, copy)) {
                    possibleMovesLogicList.add((rank + direction * 2) + "" + file);
                }
            }
        }
        copy = copyBoard(board);
        if (white) {
            if (isValidSquare(rank - 1, file - 1) && board[rank - 1][file - 1] < 0) {
                copy[rank - 1][file - 1] = 1;
                copy[rank][file] = 0;
                if (!Game.kingChecked(true, copy)) {
                    possibleMovesLogicList.add((rank - 1) + "" + (file - 1));
                }
            }
            copy = copyBoard(board);
            if (isValidSquare(rank - 1, file + 1) && board[rank - 1][file + 1] < 0) {
                copy[rank - 1][file + 1] = 1;
                copy[rank][file] = 0;
                if (!Game.kingChecked(true, copy)) {
                    possibleMovesLogicList.add((rank - 1) + "" + (file + 1));
                }
            }
            copy = copyBoard(board);
            if (blackEnpassant != null) {
                int r = 8 - Integer.parseInt(Character.getNumericValue(blackEnpassant.charAt(1)) + "");
                int f = blackEnpassant.charAt(0) - 'a';
                copy[r][f] = 1;
                copy[rank][file] = 0;
                if (!Game.kingChecked(true, copy)) {
                    if (rank == r + 1 && Math.abs(file - f) == 1) {
                        possibleMovesLogicList.add(r + "" + f);
                    }
                }
            }
        } else {
            if (isValidSquare(rank + 1, file - 1) && board[rank + 1][file - 1] > 0) {
                copy[rank + 1][file - 1] = -1;
                copy[rank][file] = 0;
                if (!Game.kingChecked(false, copy)) {
                    possibleMovesLogicList.add((rank + 1) + "" + (file - 1));
                }
            }
            copy = copyBoard(board);
            if (isValidSquare(rank + 1, file + 1) && board[rank + 1][file + 1] > 0) {
                copy[rank + 1][file + 1] = -1;
                copy[rank][file] = 0;
                if (!Game.kingChecked(false, copy)) {
                    possibleMovesLogicList.add((rank + 1) + "" + (file + 1));
                }
            }
            copy = copyBoard(board);
            if (whiteEnpassant != null) {
                int r = 2;
                int f = 7 - (whiteEnpassant.charAt(0) - 'a');
                copy[7 - r][7 - f] = -1;
                copy[rank][file] = 0;
                if (!Game.kingChecked(false, copy)) {
                    if (rank == r + 1 && Math.abs(file - f) == 1) {
                        possibleMovesLogicList.add((7 - r) + "" + (7 - f));
                    }
                }
            }

        }
    }

    private static boolean isValidSquare(int rank, int file) {
        return rank >= 0 && rank < 8 && file >= 0 && file < 8;
    }
}
