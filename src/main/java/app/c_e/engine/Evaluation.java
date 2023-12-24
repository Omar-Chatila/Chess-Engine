package app.c_e.engine;

import chessModel.*;

import static app.c_e.engine.PositionValue.*;

public class Evaluation {

    public static double getValue(byte[][] board, boolean isWhite, String move) {
        return getMaterial(board) + checkMated(board);
    }

    public static double getMaterial(byte[][] board) {
        int material = 0;
        for (int i = 0; i < 64; i++) {
            int y = i / 8;
            int x = i % 8;
            byte piece = board[y][x];
            switch (piece) {
                case 9 -> material += PIECE_VALUES[4] + QUEEN_B_PST[i];
                case 5 -> material += PIECE_VALUES[3] + ROOK_B_PST[i];
                case 4 -> material += PIECE_VALUES[2] + BISHOP_B_PST[i];
                case 3 -> material += PIECE_VALUES[1] + KNIGHT_B_PST[i];
                case 1 -> material += PIECE_VALUES[0] + PAWN_B_PST[i];
                case 100 -> material += PIECE_VALUES[5] + KING_B_PST[i];

                case -9 -> material += PIECE_VALUES[10] - QUEEN_W_PST[i];
                case -5 -> material += PIECE_VALUES[9] - ROOK_W_PST[i];
                case -4 -> material += PIECE_VALUES[8] - BISHOP_W_PST[i];
                case -3 -> material += PIECE_VALUES[7] - KNIGHT_W_PST[i];
                case -1 -> material += PIECE_VALUES[6] - PAWN_W_PST[i];
                case -100 -> material += PIECE_VALUES[11] - KING_W_PST[i];
            }
        }
        return material;
    }

    public static double kingSafety(byte[][] board) {
        boolean endgame = GameHelper.numberOfPieces(board).sum() < 8;
        if (endgame) {
            int whiteKMoves = 0;
            int blackMoves = 0;
            for (byte i = 0; i < board.length; i++) {
                for (byte j = 0; j < board.length; j++) {
                    if (board[i][j] == 100) {
                        whiteKMoves += KingMoveTracker.possibleMovesLogic(board, i, j, true).size();
                    } else if (board[i][j] == -100) {
                        blackMoves += KingMoveTracker.possibleMovesLogic(board, i, j, false).size();
                    }
                }
            }
            return ((whiteKMoves / 8.0 + 1) * 3.0) - ((blackMoves / 8.0 + 1) * 3.0);
        }
        return 0;
    }

    public static double centralControl(byte[][] board) {     // increase value of position by 0.1 per centralized piece
        double result = 0;
        if (board[3][3] > 0) {
            result += 0.4;
        } else if (board[3][3] < 0) {
            result -= 0.4;
        }
        if (board[3][4] > 0) {
            result += 0.4;
        } else if (board[3][4] < 0) {
            result -= 0.4;
        }
        if (board[4][3] > 0) {
            result += 0.4;
        } else if (board[4][3] < 0) {
            result -= 0.4;
        }
        if (board[4][4] > 0) {
            result += 0.4;
        } else if (board[4][4] < 0) {
            result -= 0.4;
        }
        if (Game.playedPositions.size() <= 2 && (board[4][4] == 1 || board[4][3] == 1)) {
            result += 1;
        }
        if (Game.playedPositions.size() <= 2 && (board[3][4] == -1 || board[3][3] == -1)) {
            result -= 1;
        }
        return result;
    }

    public static double checkMated(byte[][] current) {
        if (Game.checkMated(current, true)) {
            return -1000000;
        } else if (Game.checkMated(current, false)) {
            return 1000000;
        }
        return 0;
    }

    public static double staleMated(byte[][] current, boolean isWhite) {
        if (Game.playedPositions.size() > 40 || GameHelper.numberOfPieces().sum() < 10) {
            boolean stalematePotential = isStalematePotential(current, isWhite);
            if (stalematePotential) {
                if (isWhite && Game.stalemated(current, false)) return -100000;
            }
            if (isWhite && Game.stalemated(current, false)) return -100000;
            return 0;
        }
        return 0;
    }

    private static boolean isStalematePotential(byte[][] current, boolean isWhite) {
        boolean stalematePotential = true;
        for (byte[] b : current) {
            for (byte b1 : b) {
                if (isWhite && (b1 == 3 || b1 == 9 || b1 == 5 || b1 == 4)) {
                    stalematePotential = false;
                    break;
                } else if (!isWhite && (b1 == -3 || b1 == -9 || b1 == -5 || b1 == -4)) {
                    stalematePotential = false;
                    break;
                }
            }
        }
        return stalematePotential;
    }

    public static double development(byte[][] current) {      // increase value of position by 0.2 per developed piece if white, decrease if black
        double result = 0;

        if (current[7][5] == 0) {
            result += 0.3;
        } else if (current[7][5] > 0) {
            result -= 0.3;
        }
        if (current[7][6] == 0) {
            result += 0.3;
        } else if (current[7][6] > 0) {
            result -= 0.3;
        }
        if (current[7][3] == 0) {
            result += 0.3;
        } else if (current[7][3] > 0) {
            result -= 0.3;
        }
        if (current[7][2] == 0) {
            result += 0.3;
        } else if (current[7][2] > 0) {
            result -= 0.3;
        }
        if (current[0][5] == 0) {
            result -= 0.3;
        } else if (current[0][5] > 0) {
            result += 0.3;
        }
        if (current[0][6] == 0) {
            result -= 0.3;
        } else if (current[0][6] < 0) {
            result += 0.3;
        }
        if (current[0][3] == 0) {
            result -= 0.3;
        } else if (current[0][3] < 0) {
            result += 0.3;
        }
        if (current[0][2] == 0) {
            result -= 0.3;
        } else if (current[0][2] < 0) {
            result += 0.3;
        }
        int playedPos = Game.playedPositions.size();
        boolean midGame = playedPos < 30;
        boolean safeWK = false;
        boolean safeBK = false;
        if (midGame) {
            for (int i = 0; i < 8; i++) {
                if (current[7][i] == -100) {
                    safeBK = true;
                }
                if (current[0][i] == 100) {
                    safeWK = true;
                }
            }
            if (!safeWK) result -= 5;
            if (!safeBK) result += 5;
        }

        for (int i = 0; i < 8; i++) {                       // deduct 0.2 for knights on the rim
            if (current[i][7] == 3 || current[i][0] == 3) {
                result -= 0.2;
            }
            if (current[i][7] == -3 || current[i][0] == -3) {
                result += 0.2;
            }
        }
        return result;
    }

    public static double pieceMobility(byte[][] current) {
        int whiteMoves = 0;
        int blackMoves = 0;
        for (byte i = 0; i < 8; i++) {
            for (byte j = 0; j < 8; j++) {
                switch (current[i][j]) {
                    case 4 -> whiteMoves += BishopMoveTracker.possibleMovesLogic(current, i, j, true).size();
                    case 3 -> whiteMoves += KnightMoveTracker.possibleMovesLogic(current, i, j, true).size();
                    case 5 -> {
                        if (GameHelper.numberOfPieces().sum() < 20)
                            whiteMoves += RookMoveTracker.possibleMovesLogic(current, i, j, true).size();
                    }
                    case 9 -> {
                        if (Game.playedPositions.size() > 5) // check if still in opening stage
                            whiteMoves += QueenMoveTracker.possibleMovesLogic(current, i, j, true).size();
                    }

                    case -4 -> blackMoves += BishopMoveTracker.possibleMovesLogic(current, i, j, false).size();
                    case -3 -> blackMoves += KnightMoveTracker.possibleMovesLogic(current, i, j, false).size();
                    case -5 -> {
                        if (Game.playedPositions.size() > 9)
                            blackMoves += RookMoveTracker.possibleMovesLogic(current, i, j, false).size();
                    }
                    case -9 -> {
                        if (Game.playedPositions.size() > 5)
                            blackMoves += QueenMoveTracker.possibleMovesLogic(current, i, j, false).size();
                    }
                }
            }
        }
        return (whiteMoves - blackMoves) / 15.0;
    }
}
