package app.c_e.tests;


import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;

import static chessModel.BishopMoveTracker.possibleMovesLogic;
import static org.testng.AssertJUnit.assertEquals;

public class BishopMoveTests {

    @Test
    public void testCenterMoves() {
        byte[] board = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 4, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves = List.of(44, 53, 62, 42, 49, 56, 17, 8, 28, 21, 14, 7, 26);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 35, true)));
        TestHelper.changeTurn(board);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 35, false)));


        byte[] board2 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 4, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves2 = List.of(54, 63, 36, 27, 18, 9, 0, 38, 31, 52, 59);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 45, true)));
        TestHelper.changeTurn(board);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 45, false)));


        byte[] board3 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 4, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves3 = List.of(48, 32, 50, 59, 34, 27, 20, 13, 6);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 41, true)));
        TestHelper.changeTurn(board);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 41, false)));
    }

    @Test
    public void testCornerMoves() {
        byte[] board = {
                4, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves = List.of(9, 18, 27, 36, 45, 54, 63);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 0, true)));
        TestHelper.changeTurn(board);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 0, false)));

        byte[] board2 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 4
        };
        List<Integer> expectedMoves2 = List.of(0, 9, 18, 27, 36, 45, 54);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 63, true)));
        TestHelper.changeTurn(board2);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 63, false)));

        byte[] board3 = {
                0, 0, 0, 0, 0, 0, 0, 4,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves3 = List.of(14, 21, 28, 35, 42, 49, 56);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 7, true)));
        TestHelper.changeTurn(board3);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 7, false)));


        byte[] board4 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                4, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves4 = List.of(14, 21, 28, 35, 42, 49, 7);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 56, true)));
        TestHelper.changeTurn(board4);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 56, false)));
    }

    @Test
    public void testEdgesMoves() {
        byte[] board = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 4,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves = List.of(38, 45, 52, 59, 22, 13, 4);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 31, true)));

        byte[] board2 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 4, 0, 0, 0, 0
        };
        List<Integer> expectedMoves2 = List.of(52, 45, 38, 31, 50, 41, 32);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 59, true)));

        byte[] board3 = {
                0, 0, 9, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves3 = List.of(11, 20, 29, 38, 47, 9, 16);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 2, true)));

    }

    @Test
    public void testCrowdedBoard() {
        byte[] board = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, -4, 0, 0, -9, 0,
                0, 3, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 3, 0, 0,
                4, 0, 0, 4, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 1, 0, -4, -1, 0, 0, 0,
                0, 0, 1, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves = List.of(28, 21, 14, 44, 53, 62, 42, 26);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 35, true)));
        TestHelper.changeTurn(board);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 35, false)));

        byte[] board2 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, -4, -5, 0, 0, 0,
                0, 3, 0, 0, 0, 0, 0, 0,
                1, 0, 0, 0, 0, 3, 0, 0,
                4, 4, -1, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 1, 0, -4, -1, 0, 0, 0,
                0, 0, 1, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves2 = List.of(26, 19, 12, 42, 51, 40);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 33, true)));
        TestHelper.changeTurn(board2);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 33, false)));

        byte[] board3 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, -4, -5, 0, 0, 0,
                0, 3, 0, 0, 0, 0, 0, 0,
                1, 0, 0, 1, 1, 3, 0, 0,
                4, 0, -1, 1, 4, 1, 0, 0,
                0, 0, 0, 1, 1, 1, 0, 0,
                0, 1, 0, -4, -1, 0, 0, 0,
                0, 0, 1, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves3 = List.of();
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 36, true)));
        TestHelper.changeTurn(board3);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 36, false)));

        byte[] board4 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, -4, -5, 0, 0, 0,
                0, 3, 0, 0, 0, 0, 0, 0,
                1, 0, 0, -1, -1, -3, 0, 0,
                4, 0, -1, -1, 4, -1, 0, 0,
                0, 0, 0, -4, -1, -1, 0, 0,
                0, 1, 0, -4, -1, 0, 0, 0,
                0, 0, 1, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves4 = List.of(29, 43, 45, 27);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 36, true)));
        TestHelper.changeTurn(board4);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 36, false)));

        byte[] board5 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, -4, -5, 0, 0, -3,
                0, 3, 0, 0, 0, 0, 0, 0,
                1, 0, 0, -1, -1, -3, 0, 0,
                4, 0, -1, -1, 0, -1, 0, 4,
                0, 0, 0, -4, -1, -1, 0, 0,
                0, 1, 0, -4, -1, 1, 0, 3,
                0, 0, 1, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves5 = List.of(30, 21, 12, 46);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board5, 39, true)));
        TestHelper.changeTurn(board5);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board5, 39, false)));

    }


    @Test
    public void testPinnedMoves() {
        // pinned from above
        byte[] board = {
                0, 0, -5, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 100, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves = List.of();
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 42, true)));
        TestHelper.changeTurn(board);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 42, false)));

        // pinned from left
        byte[] board2 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                -9, 0, 0, 0, 4, 0, 0, 100,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves2 = List.of();
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 36, true)));
        TestHelper.changeTurn(board2);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 36, false)));

        // pinned from right
        byte[] board3 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 100, 4, 0, 0, -5, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves3 = List.of();
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 26, true)));
        TestHelper.changeTurn(board3);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 26, false)));

        // pinned from below
        byte[] board4 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 100, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, -9, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves4 = List.of();
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 42, true)));
        TestHelper.changeTurn(board4);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 42, false)));

        // pinned from top left
        byte[] board5 = {
                -4, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 9, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 100
        };
        List<Integer> expectedMoves5 = List.of(9, 0, 27, 36, 45, 54);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board5, 18, true)));
        TestHelper.changeTurn(board5);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board5, 18, false)));

        // pinned from bottom right
        byte[] board6 = {
                100, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 9, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, -9
        };
        List<Integer> expectedMoves6 = List.of(9, 63, 27, 36, 45, 54);
        assertEquals(new HashSet<>(expectedMoves6), new HashSet<>(possibleMovesLogic(board6, 18, true)));
        TestHelper.changeTurn(board6);
        assertEquals(new HashSet<>(expectedMoves6), new HashSet<>(possibleMovesLogic(board6, 18, false)));

        // pinned from bottom left
        byte[] board7 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 100, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 4, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, -9, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves7 = List.of(50, 57, 36);
        assertEquals(new HashSet<>(expectedMoves7), new HashSet<>(possibleMovesLogic(board7, 43, true)));
        TestHelper.changeTurn(board7);
        assertEquals(new HashSet<>(expectedMoves7), new HashSet<>(possibleMovesLogic(board7, 43, false)));

        // pinned from top right
        byte[] board8 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, -9, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 4, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 100, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves8 = List.of(50, 36, 29);
        assertEquals(new HashSet<>(expectedMoves8), new HashSet<>(possibleMovesLogic(board8, 43, true)));
        TestHelper.changeTurn(board8);
        assertEquals(new HashSet<>(expectedMoves8), new HashSet<>(possibleMovesLogic(board8, 43, false)));

    }

    @Test
    public void testCheckedMoves() {
        byte[] board = {
                0, 0, 0, 100, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 4, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, -5, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves = List.of(43, 11);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 29, true)));
        TestHelper.changeTurn(board);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 29, false)));

        // Double check
        byte[] board2 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                -9, 0, 0, 100, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 4, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, -5, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves2 = List.of();
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 21, true)));
        TestHelper.changeTurn(board2);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 21, false)));

        byte[] board3 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 9, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                4, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 1, 1, 1,
                -5, 0, 0, 0, 0, 100, 0, 0
        };
        List<Integer> expectedMoves3 = List.of(58);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 40, true)));
        TestHelper.changeTurn(board3);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 40, false)));

        byte[] board4 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 9, 4, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, -4, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 1, 1, 1,
                0, 0, 0, 0, 0, 100, 0, 0
        };
        List<Integer> expectedMoves4 = List.of(43);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 22, true)));
        TestHelper.changeTurn(board4);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 22, false)));

        byte[] board5 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 9, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, -4, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 1, 1, 1,
                0, 0, 0, 4, 0, 100, 0, 0
        };
        List<Integer> expectedMoves5 = List.of(52);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board5, 59, true)));
        TestHelper.changeTurn(board5);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board5, 59, false)));

        byte[] board6 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                4, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 9, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, -3, 0, 0, 0,
                0, 0, 0, 0, 0, 1, 1, 1,
                0, 0, 0, 0, 0, 100, 0, 0
        };
        List<Integer> expectedMoves6 = List.of(44);
        assertEquals(new HashSet<>(expectedMoves6), new HashSet<>(possibleMovesLogic(board6, 8, true)));
        TestHelper.changeTurn(board6);
        assertEquals(new HashSet<>(expectedMoves6), new HashSet<>(possibleMovesLogic(board6, 8, false)));

        byte[] board7 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 9, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, -4, 0, 0, 0, 0,
                4, 0, 0, 0, 0, 1, 1, 1,
                0, -9, 0, 0, 0, 100, 0, 0
        };
        List<Integer> expectedMoves7 = List.of();
        assertEquals(new HashSet<>(expectedMoves7), new HashSet<>(possibleMovesLogic(board7, 48, true)));
        TestHelper.changeTurn(board7);
        assertEquals(new HashSet<>(expectedMoves7), new HashSet<>(possibleMovesLogic(board7, 48, false)));
    }
}
