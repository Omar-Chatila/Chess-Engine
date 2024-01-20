package app.c_e.tests;


import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;

import static chessModel.PawnMoveTracker.possibleMovesLogic;
import static org.testng.AssertJUnit.assertEquals;

public class PawnMoveTests {
    public static final byte K = 100;
    public static final byte P = 1;
    public static final byte N = 3;
    public static final byte R = 5;
    public static final byte B = 4;

    public static final byte k = -100;
    public static final byte p = -1;
    public static final byte n = -3;
    public static final byte r = -5;
    public static final byte b = -4;

    @Test
    public void testOneStepWhite() {
        byte[] board = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, P, 0, 0, P, 0, P,
                0, 0, P, 0, 0, 0, P, 0,
                P, 0, 0, P, 0, 0, 0, 0,
                0, P, 0, 0, P, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves = List.of(24);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 32, true)));

        List<Integer> expectedMoves2 = List.of(33);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board, 41, true)));

        List<Integer> expectedMoves3 = List.of();
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board, 26, true)));

        List<Integer> expectedMoves4 = List.of(10);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board, 18, true)));

        List<Integer> expectedMoves5 = List.of(27);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board, 35, true)));

        List<Integer> expectedMoves6 = List.of(36);
        assertEquals(new HashSet<>(expectedMoves6), new HashSet<>(possibleMovesLogic(board, 44, true)));

        List<Integer> expectedMoves7 = List.of(13);
        assertEquals(new HashSet<>(expectedMoves7), new HashSet<>(possibleMovesLogic(board, 21, true)));

        List<Integer> expectedMoves8 = List.of(22);
        assertEquals(new HashSet<>(expectedMoves8), new HashSet<>(possibleMovesLogic(board, 30, true)));

        List<Integer> expectedMoves9 = List.of(15);
        assertEquals(new HashSet<>(expectedMoves9), new HashSet<>(possibleMovesLogic(board, 23, true)));
    }

    @Test
    public void testOneStepBlack() {
        byte[] board = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, -P, 0, 0, -P, 0, -P,
                0, 0, -P, 0, 0, 0, -P, 0,
                -P, 0, 0, -P, 0, 0, 0, 0,
                0, -P, 0, 0, -P, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves = List.of(40);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 32, false)));

        List<Integer> expectedMoves2 = List.of(49);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board, 41, false)));

        List<Integer> expectedMoves3 = List.of(34);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board, 26, false)));

        List<Integer> expectedMoves4 = List.of();
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board, 18, false)));

        List<Integer> expectedMoves5 = List.of(43);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board, 35, false)));

        List<Integer> expectedMoves6 = List.of(52);
        assertEquals(new HashSet<>(expectedMoves6), new HashSet<>(possibleMovesLogic(board, 44, false)));

        List<Integer> expectedMoves7 = List.of(29);
        assertEquals(new HashSet<>(expectedMoves7), new HashSet<>(possibleMovesLogic(board, 21, false)));

        List<Integer> expectedMoves8 = List.of(38);
        assertEquals(new HashSet<>(expectedMoves8), new HashSet<>(possibleMovesLogic(board, 30, false)));

        List<Integer> expectedMoves9 = List.of(31);
        assertEquals(new HashSet<>(expectedMoves9), new HashSet<>(possibleMovesLogic(board, 23, false)));
    }

    @Test
    public void testTwoStepWhite() {
        byte[] board = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 5, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 4, 0,
                0, 0, 0, 0, 3, 0, 0, 0,
                P, P, P, P, P, P, P, P,
                0, 9, 0, 0, 0, 0, 0, 0,

        };
        List<Integer> expectedMoves = List.of(40, 32);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 48, true)));

        List<Integer> expectedMoves1 = List.of(41, 33);
        assertEquals(new HashSet<>(expectedMoves1), new HashSet<>(possibleMovesLogic(board, 49, true)));

        List<Integer> expectedMoves2 = List.of(42, 34);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board, 50, true)));

        List<Integer> expectedMoves3 = List.of(43, 35);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board, 51, true)));

        List<Integer> expectedMoves4 = List.of();
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board, 52, true)));

        List<Integer> expectedMoves5 = List.of(45, 37);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board, 53, true)));

        List<Integer> expectedMoves6 = List.of(46);
        assertEquals(new HashSet<>(expectedMoves6), new HashSet<>(possibleMovesLogic(board, 54, true)));

        List<Integer> expectedMoves7 = List.of(47, 39);
        assertEquals(new HashSet<>(expectedMoves7), new HashSet<>(possibleMovesLogic(board, 55, true)));
    }

    @Test
    public void testTwoStepBlack() {
        byte[] board = {
                0, 0, 0, 0, 0, 0, 0, 0,
                p, p, p, p, p, p, p, p,
                0, 0, p, 0, 0, 0, 0, 0,
                0, P, 0, 0, 0, 0, 0, 0,
                0, 0, 0, P, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
        };
        List<Integer> expectedMoves = List.of(16, 24);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 8, false)));

        List<Integer> expectedMoves1 = List.of(17);
        assertEquals(new HashSet<>(expectedMoves1), new HashSet<>(possibleMovesLogic(board, 9, false)));

        List<Integer> expectedMoves2 = List.of();
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board, 10, false)));

        List<Integer> expectedMoves3 = List.of(19, 27);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board, 11, false)));

        List<Integer> expectedMoves4 = List.of(20, 28);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board, 12, false)));

        List<Integer> expectedMoves5 = List.of(21, 29);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board, 13, false)));

        List<Integer> expectedMoves6 = List.of(22, 30);
        assertEquals(new HashSet<>(expectedMoves6), new HashSet<>(possibleMovesLogic(board, 14, false)));

        List<Integer> expectedMoves7 = List.of(23, 31);
        assertEquals(new HashSet<>(expectedMoves7), new HashSet<>(possibleMovesLogic(board, 15, false)));
    }

    @Test
    public void testCapturesWhite() {
        byte[] board = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, p, 0, p, 0,
                b, 0, b, 0, 0, P, 0, P,
                b, P, 0, P, 0, 0, 0, 0,
                0, 0, 0, 0, 0, P, B, P,
                n, r, 0, 0, N, 0, 0, N,
                P, 0, P, 0, P, 0, P, 0,
                0, 9, 0, 0, 0, 0, 0, 0,

        };
        List<Integer> expectedMoves = List.of(41);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 48, true)));

        List<Integer> expectedMoves1 = List.of(16, 17, 18);
        assertEquals(new HashSet<>(expectedMoves1), new HashSet<>(possibleMovesLogic(board, 25, true)));

        List<Integer> expectedMoves2 = List.of(42, 34, 41);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board, 50, true)));

        List<Integer> expectedMoves3 = List.of(18, 19);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board, 27, true)));

        List<Integer> expectedMoves4 = List.of();
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board, 52, true)));

        List<Integer> expectedMoves5 = List.of(29);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board, 37, true)));

        List<Integer> expectedMoves6 = List.of(46);
        assertEquals(new HashSet<>(expectedMoves6), new HashSet<>(possibleMovesLogic(board, 54, true)));

        List<Integer> expectedMoves7 = List.of(31);
        assertEquals(new HashSet<>(expectedMoves7), new HashSet<>(possibleMovesLogic(board, 39, true)));

        List<Integer> expectedMoves8 = List.of(15, 14);
        assertEquals(new HashSet<>(expectedMoves8), new HashSet<>(possibleMovesLogic(board, 23, true)));

        List<Integer> expectedMoves9 = List.of(13, 12, 14);
        assertEquals(new HashSet<>(expectedMoves9), new HashSet<>(possibleMovesLogic(board, 21, true)));
    }

    @Test
    public void testCapturesBlack() {
        byte[] board = {
                0, 0, 0, 0, 0, 0, 0, 0,
                p, 0, 0, 0, 0, 0, p, B,
                0, P, p, 0, 0, 0, B, B,
                0, p, 0, 0, p, 0, 0, p,
                N, R, R, p, 0, 0, P, 0,
                p, 0, p, 0, r, p, 0, 0,
                0, P, 0, N, r, R, B, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
        };
        List<Integer> expectedMoves = List.of(16, 17, 24);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 8, false)));

        List<Integer> expectedMoves1 = List.of(48, 49);
        assertEquals(new HashSet<>(expectedMoves1), new HashSet<>(possibleMovesLogic(board, 40, false)));

        List<Integer> expectedMoves2 = List.of(32, 34);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board, 25, false)));

        List<Integer> expectedMoves3 = List.of(26);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board, 18, false)));

        List<Integer> expectedMoves31 = List.of(49, 50, 51);
        assertEquals(new HashSet<>(expectedMoves31), new HashSet<>(possibleMovesLogic(board, 42, false)));

        List<Integer> expectedMoves4 = List.of(43);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board, 35, false)));

        List<Integer> expectedMoves5 = List.of(36);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board, 28, false)));

        List<Integer> expectedMoves6 = List.of(54);
        assertEquals(new HashSet<>(expectedMoves6), new HashSet<>(possibleMovesLogic(board, 45, false)));

        List<Integer> expectedMoves7 = List.of(23);
        assertEquals(new HashSet<>(expectedMoves7), new HashSet<>(possibleMovesLogic(board, 14, false)));

        List<Integer> expectedMoves8 = List.of(38, 39);
        assertEquals(new HashSet<>(expectedMoves8), new HashSet<>(possibleMovesLogic(board, 31, false)));
    }

    @Test
    public void testPinnedMovesWhite() {
        // pinned from above
        byte[] board = {
                0, 0, -5, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, P, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, K, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves = List.of(34);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 42, true)));

        // pinned from left
        byte[] board2 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                -9, 0, 0, 0, P, 0, 0, K,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves2 = List.of();
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 36, true)));


        // pinned from right
        byte[] board3 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, K, P, 0, 0, -5, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves3 = List.of();
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 26, true)));


        // pinned from below
        byte[] board4 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, K, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, P, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, -9, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves4 = List.of(34);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 42, true)));

        // pinned from top left
        byte[] board5 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, -4, 0, 0, 0, 0, 0, 0,
                0, 0, P, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 9, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, K
        };
        List<Integer> expectedMoves5 = List.of(9);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board5, 18, true)));

        // pinned from bottom right
        byte[] board6 = {
                K, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, P, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 9, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, -9
        };
        List<Integer> expectedMoves6 = List.of();
        assertEquals(new HashSet<>(expectedMoves6), new HashSet<>(possibleMovesLogic(board6, 18, true)));

        // pinned from bottom left
        byte[] board7 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, K, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, P, 0, 0, 0, 0,
                0, 0, 0, 0, r, 0, 0, 0,
                0, -9, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves7 = List.of();
        assertEquals(new HashSet<>(expectedMoves7), new HashSet<>(possibleMovesLogic(board7, 43, true)));

        // pinned from top right
        byte[] board8 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, -9, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, P, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, K, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves8 = List.of();
        assertEquals(new HashSet<>(expectedMoves8), new HashSet<>(possibleMovesLogic(board8, 43, true)));

    }

    @Test
    public void testPinnedMovesBlack() {
        // pinned from above
        byte[] board = {
                0, 0, 5, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, p, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, K, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves = List.of(50);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 42, false)));

        // pinned from left
        byte[] board2 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                9, 0, 0, 0, p, 0, 0, k,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves2 = List.of();
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 36, false)));


        // pinned from right
        byte[] board3 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, k, p, 0, 0, 5, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves3 = List.of();
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 26, false)));


        // pinned from below
        byte[] board4 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, k, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, p, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 9, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves4 = List.of(50);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 42, false)));

        // pinned from top left
        byte[] board5 = {
                4, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, p, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 9, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, k
        };
        List<Integer> expectedMoves5 = List.of();
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board5, 18, false)));

        // pinned from bottom right
        byte[] board6 = {
                k, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, p, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 9, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 9
        };
        List<Integer> expectedMoves6 = List.of();
        assertEquals(new HashSet<>(expectedMoves6), new HashSet<>(possibleMovesLogic(board6, 18, false)));

        // pinned from bottom left
        byte[] board7 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, k, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, p, 0, 0, 0, 0,
                0, 0, 0, 0, r, 0, 0, 0,
                0, 9, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves7 = List.of();
        assertEquals(new HashSet<>(expectedMoves7), new HashSet<>(possibleMovesLogic(board7, 43, false)));

        // pinned from top right
        byte[] board8 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 9, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, p, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, k, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves8 = List.of();
        assertEquals(new HashSet<>(expectedMoves8), new HashSet<>(possibleMovesLogic(board8, 43, false)));

    }

    @Test
    public void testWhiteCheckedMoves() {
        byte[] board = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, K, 0, 0, 0, 0,
                0, 0, 0, 0, 0, P, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, P, 0, 0, 0, 0, 0,
                0, 0, 0, -5, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves = List.of();
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 21, true)));

        // Double check
        byte[] board2 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                -9, 0, 0, K, -5, 0, 0, 0,
                0, 0, 0, 0, 0, P, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves2 = List.of();
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 21, true)));

        byte[] board3 = {
                0, 0, 0, 0, K, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 9, 0, 0,
                0, b, P, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                9, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 1, 1, 1,
                -5, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves3 = List.of(18);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 26, true)));

        byte[] board4 = {
                0, 0, 0, 0, K, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 9, 0, 0,
                0, b, 0, 0, 0, 0, 0, 0,
                0, 0, P, 0, 0, 0, 0, 0,
                9, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 1, 1, 1,
                -5, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves4 = List.of(25);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 34, true)));

        byte[] board5 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 9, 0, 0,
                0, b, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                9, 0, P, 0, 0, 1, 1, 1,
                0, 0, 0, 0, 0, K, 0, 0
        };
        List<Integer> expectedMoves5 = List.of(34);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board5, 50, true)));
    }

    @Test
    public void testBlackCheckedMoves() {
        byte[] board = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, k, 0, 0, 0, 0,
                0, 0, 0, 0, 0, p, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, p, 0, 0, 0, 0, 0,
                0, 0, 0, R, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves = List.of();
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 21, false)));

        List<Integer> expectedMoves1 = List.of(43);
        assertEquals(new HashSet<>(expectedMoves1), new HashSet<>(possibleMovesLogic(board, 34, false)));

        // Double check
        byte[] board2 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                9, 0, 0, k, 0, 0, 0, 0,
                0, 0, 0, 0, 0, p, 0, 0,
                0, 0, 0, 0, N, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves2 = List.of();
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 21, false)));

        byte[] board3 = {
                0, 0, 0, 0, k, 0, 0, 0,
                0, 0, p, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 9, 0, 0,
                0, B, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                9, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 1, 1, 1,
                -5, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves3 = List.of(18);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 10, false)));

        byte[] board4 = {
                0, 0, 0, 0, k, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                p, 0, 0, 0, 0, 9, 0, 0,
                0, B, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                9, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 1, 1, 1,
                -5, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves4 = List.of(25);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 16, false)));

        byte[] board5 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, p, p, 0, 0, 0, 0, 0,
                B, 0, 0, 0, 0, 9, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                9, 0, p, 0, 0, 1, 1, 1,
                0, 0, 0, 0, 0, k, 0, 0
        };
        List<Integer> expectedMoves5 = List.of(16, 25);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board5, 9, false)));
    }
}
