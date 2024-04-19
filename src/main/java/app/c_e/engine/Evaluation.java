package app.c_e.engine;

import chessModel.Game;
import chessModel.GameHelper;

import static app.c_e.engine.PositionValue.*;

public class Evaluation {
    // TODO: dont calculate checkmate for evaluation here but in node class by checking if moveslists are empty and adding the value for eval there
    public static int getValue(byte[] board) {
        return getMaterial(board) + checkMated(board);
    }

    public static int getMaterial(byte[] board) {
        int material = 0;
        for (int i = 0; i < 64; i++) {
            byte piece = board[i];
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

    public static int checkMated(byte[] current) {
        if (Game.checkMated(current, true)) {
            return -1000000;
        } else if (Game.checkMated(current, false)) {
            return 1000000;
        }
        return 0;
    }


    public static double staleMated(byte[] current, boolean isWhite) {
        if (Game.playedPositions.size() > 40 || GameHelper.numberOfPieces().sum() < 10) {
            boolean stalematePotential = isStalematePotential(current, isWhite);
            if (stalematePotential) {
                if (isWhite && Game.stalemated(current, false)) return -100000;
            }
            return 0;
        }
        return 0;
    }

    private static boolean isStalematePotential(byte[] current, boolean isWhite) {
        boolean stalematePotential = true;
        for (byte b1 : current) {
            if (isWhite && (b1 == 3 || b1 == 9 || b1 == 5 || b1 == 4)) {
                stalematePotential = false;
                break;
            } else if (!isWhite && (b1 == -3 || b1 == -9 || b1 == -5 || b1 == -4)) {
                stalematePotential = false;
                break;
            }
        }
        return stalematePotential;
    }
}
