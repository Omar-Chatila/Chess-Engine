package app.c_e.tests;

public class TestHelper {
    public static void changeTurn(byte[] board) {
        for (int i = 0; i < board.length; i++) {
            board[i] *= -1;
        }
    }
}
