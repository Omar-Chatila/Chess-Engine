package app.c_e.engine;

public class PositionValue {
    static int[] PIECE_VALUES = {
            100,            //P
            320,            //N
            330,            //B
            500,            //R
            900,            //Q
            1000000,           //K
            -100,           //bP
            -320,           //bN
            -330,           //bB
            -500,           //bR
            -900,           //bQ
            -1000000           //bK
    };


    static final int[] PAWN_W_PST = {
            0, 0, 0, 0, 0, 0, 0, 0,
            5, 10, 10, -20, -20, 10, 10, 5,
            5, -5, -10, 0, 0, -10, -5, 5,
            0, 0, 0, 20, 20, 0, 0, 0,
            5, 5, 10, 25, 25, 10, 5, 5,
            10, 10, 20, 30, 30, 20, 10, 10,
            50, 50, 50, 50, 50, 50, 50, 50,
            0, 0, 0, 0, 0, 0, 0, 0
    };
    static final int[] KNIGHT_W_PST = {
            -50, -40, -30, -30, -30, -30, -40, -50,
            -40, -20, 0, 5, 5, 0, -20, -40,
            -30, 5, 10, 15, 15, 10, 5, -30,
            -30, 0, 15, 20, 20, 15, 0, -30,
            -30, 5, 15, 20, 20, 15, 5, -30,
            -30, 0, 10, 15, 15, 10, 0, -30,
            -40, -20, 0, 0, 0, 0, -20, -40,
            -50, -40, -30, -30, -30, -30, -40, -50
    };

    static final int[] BISHOP_W_PST = {
            -20, -10, -10, -10, -10, -10, -10, -20,
            -10, 5, 0, 0, 0, 0, 5, -10,
            -10, 10, 10, 10, 10, 10, 10, -10,
            -10, 0, 10, 10, 10, 10, 0, -10,
            -10, 5, 5, 10, 10, 5, 5, -10,
            -10, 0, 5, 10, 10, 5, 0, -10,
            -10, 0, 0, 0, 0, 0, 0, -10,
            -20, -10, -10, -10, -10, -10, -10, -20
    };
    static final int[] ROOK_W_PST = {
            0, 0, 5, 10, 10, 5, 0, 0,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            5, 10, 10, 10, 10, 10, 10, 5,
            0, 0, 0, 0, 0, 0, 0, 0,
    };
    static final int[] QUEEN_W_PST = {
            -20, -10, -10, -5, -5, -10, -10, -20,
            -10, 0, 5, 0, 0, 0, 0, -10,
            -10, 5, 5, 5, 5, 5, 0, -10,
            0, 0, 5, 5, 5, 5, 0, -5,
            -5, 0, 5, 5, 5, 5, 0, -5,
            -10, 0, 5, 5, 5, 5, 0, -10,
            -10, 0, 0, 0, 0, 0, 0, -10,
            -20, -10, -10, -5, -5, -10, -10, -20,
    };
    static final int[] KING_W_PST = {
            20, 30, 10, 0, 0, 10, 30, 20,
            20, 20, 0, 0, 0, 0, 20, 20,
            -10, -20, -20, -20, -20, -20, -20, -10,
            -20, -30, -30, -40, -40, -30, -30, -20,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30,
    };

    static final int[] PAWN_B_PST = new int[64];
    static final int[] KNIGHT_B_PST = new int[64];
    static final int[] BISHOP_B_PST = new int[64];
    static final int[] ROOK_B_PST = new int[64];
    static final int[] QUEEN_B_PST = new int[64];
    static final int[] KING_B_PST = new int[64];

    public static void initEvaluation() {
        for (int i = 0; i < 64; i++) {
            PAWN_B_PST[i] = PAWN_W_PST[63 - i];
            KNIGHT_B_PST[i] = KNIGHT_W_PST[63 - i];
            BISHOP_B_PST[i] = BISHOP_W_PST[63 - i];
            ROOK_B_PST[i] = ROOK_W_PST[63 - i];
            QUEEN_B_PST[i] = QUEEN_W_PST[63 - i];
            KING_B_PST[i] = KING_W_PST[63 - i];
        }
    }
}
