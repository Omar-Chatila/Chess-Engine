package app.c_e.tests;


import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;

import static chessModel.QueenMoveTracker.possibleMovesLogic;
import static org.testng.AssertJUnit.assertEquals;

public class QueenMoveTests {

    @Test
    public void testCenterMoves() {
        byte[] board = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 9, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves = List.of(27, 19, 11, 3, 34, 33, 32, 36, 37, 38, 39, 43, 51, 59, 44, 53, 62, 42, 49, 56, 26, 17, 8, 28, 21, 14, 7);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 35, true)));
        board[35] = -9;
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 35, false)));


        byte[] board2 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 9, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves2 = List.of(46, 47, 53, 61, 44, 43, 42, 41, 40, 37, 29, 21, 13, 5, 38, 31, 54, 63, 36, 27, 18, 9, 0, 52, 59);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 45, true)));
        board2[45] = -9;
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 45, false)));


        byte[] board3 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 9, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves3 = List.of(40, 42, 43, 44, 45, 46, 47, 33, 25, 17, 9, 1, 49, 57, 50, 59, 48, 32, 34, 27, 20, 13, 6);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 41, true)));
        board3[41] = -9;
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 41, false)));
    }

    @Test
    public void testCornerMoves() {
        byte[] board = {
                9, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves = List.of(1, 2, 3, 4, 5, 6, 7, 8, 16, 24, 32, 40, 48, 56, 9, 18, 27, 36, 45, 54, 63);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 0, true)));
        board[0] = -9;
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 0, false)));

        byte[] board2 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 9
        };
        List<Integer> expectedMoves2 = List.of(0, 9, 18, 27, 36, 45, 54, 62, 61, 60, 59, 58, 57, 56, 55, 47, 39, 31, 23, 15, 7);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 63, true)));
        board2[63] = -9;
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 63, false)));

        byte[] board3 = {
                0, 0, 0, 0, 0, 0, 0, 9,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves3 = List.of(6, 5, 4, 3, 2, 1, 0, 15, 23, 31, 39, 47, 55, 63, 14, 21, 28, 35, 42, 49, 56);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 7, true)));
        board3[7] = -9;
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 7, false)));


        byte[] board4 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                9, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves4 = List.of(0, 8, 16, 24, 32, 40, 48, 57, 58, 59, 60, 61, 62, 63, 14, 21, 28, 35, 42, 49, 7);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 56, true)));
        board4[56] = -9;
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 56, false)));
    }

    @Test
    public void testEdgesMoves() {
        byte[] board = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 9,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves = List.of(23, 15, 7, 39, 47, 55, 63, 30, 29, 28, 27, 26, 25, 24, 38, 45, 52, 59, 22, 13, 4);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 31, true)));

        byte[] board2 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 9, 0, 0, 0, 0
        };
        List<Integer> expectedMoves2 = List.of(60, 61, 62, 63, 58, 57, 56, 51, 43, 35, 27, 19, 11, 3, 52, 45, 38, 31, 50, 41, 32);
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
        List<Integer> expectedMoves3 = List.of(0, 1, 3, 4, 5, 6, 7, 10, 18, 26, 34, 42, 50, 58, 11, 20, 29, 38, 47, 9, 16);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 2, true)));

    }

    @Test
    public void testCrowdedBoard() {
        byte[] board = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, -4, 0, 0, 0, 0,
                0, 3, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 3, 0, 0,
                4, 0, 0, 9, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, -4, -1, 0, 0, 0,
                0, 0, 1, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves = List.of(36, 37, 38, 39, 34, 33, 27, 19, 11, 43, 51, 44, 53, 7, 14, 21, 28, 42, 49, 56, 62, 26);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 35, true)));
        TestHelper.changeTurn(board);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 35, false)));

        byte[] board2 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, -4, -5, 0, 0, 0,
                0, 3, 0, 0, 0, 0, 0, 0,
                1, 0, 0, 0, 0, 3, 0, 0,
                4, 9, -1, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 1, 0, -4, -1, 0, 0, 0,
                0, 0, 1, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves2 = List.of(25, 42, 40, 26, 19, 12, 34, 51, 41);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 33, true)));
        TestHelper.changeTurn(board2);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 33, false)));

        byte[] board3 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, -4, -5, 0, 0, 0,
                0, 3, 0, 0, 0, 0, 0, 0,
                1, 0, 0, 1, 1, 3, 0, 0,
                4, 0, -1, 1, 9, 1, 0, 0,
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
                4, 0, -1, -1, 9, -1, 0, 0,
                0, 0, 0, -4, -1, -1, 0, 0,
                0, 1, 0, -4, -1, 0, 0, 0,
                0, 0, 1, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves4 = List.of(37, 35, 28, 29, 27, 44, 43, 45);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 36, true)));
        TestHelper.changeTurn(board4);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 36, false)));

        byte[] board5 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, -4, -5, 0, 0, -3,
                0, 3, 0, 0, 0, 0, 0, 0,
                1, 0, 0, -1, -1, -3, 0, 0,
                4, 0, -1, -1, 0, -1, 0, 9,
                0, 0, 0, -4, -1, -1, 0, 0,
                0, 1, 0, -4, -1, 0, 0, 3,
                0, 0, 1, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves5 = List.of(31, 23, 47, 46, 53, 60, 38, 37, 15, 12, 30, 21, 12);
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
                0, 0, 9, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 100, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves = List.of(50, 34, 18, 2, 26, 10);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 42, true)));
        TestHelper.changeTurn(board);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 42, false)));

        // pinned from left
        byte[] board2 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                -9, 0, 0, 0, 9, 0, 0, 100,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves2 = List.of(35, 34, 33, 32, 37, 38);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 36, true)));
        TestHelper.changeTurn(board2);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 36, false)));

        // pinned from right
        byte[] board3 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 100, 9, 0, 0, -5, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves3 = List.of(27, 28, 29);
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
                0, 0, 9, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, -9, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves4 = List.of(50, 58, 34, 26);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 42, true)));
        TestHelper.changeTurn(board4);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 42, false)));

        // pinned from top left
        byte[] board5 = {
                -4, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 9, 0, 0, 0, 0, 0,
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
                0, 0, 9, 0, 0, 0, 0, 0,
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
                0, 0, 0, 9, 0, 0, 0, 0,
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
                0, 0, 0, 9, 0, 0, 0, 0,
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
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 100, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 9, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, -5, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves = List.of(19, 35);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 21, true)));
        TestHelper.changeTurn(board);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 21, false)));

        // Double check
        byte[] board2 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                -9, 0, 0, 100, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 9, 0, 0,
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
                9, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 1, 1, 1,
                -5, 0, 0, 0, 0, 100, 0, 0
        };
        List<Integer> expectedMoves3 = List.of(56, 58);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 40, true)));
        TestHelper.changeTurn(board3);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 40, false)));

        byte[] board4 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 9, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                9, 0, 0, -4, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 1, 1, 1,
                0, 0, 0, 0, 0, 100, 0, 0
        };
        List<Integer> expectedMoves4 = List.of(43);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 40, true)));
        TestHelper.changeTurn(board4);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 40, false)));

        byte[] board5 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 9, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, -4, 0, 0, 0, 0,
                9, 0, 0, 0, 0, 1, 1, 1,
                0, 0, 0, 0, 0, 100, 0, 0
        };
        List<Integer> expectedMoves5 = List.of(52);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board5, 48, true)));
        TestHelper.changeTurn(board5);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board5, 48, false)));

        byte[] board6 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 9, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                9, 0, 0, 0, -3, 0, 0, 0,
                0, 0, 0, 0, 0, 1, 1, 1,
                0, 0, 0, 0, 0, 100, 0, 0
        };
        List<Integer> expectedMoves6 = List.of(44);
        assertEquals(new HashSet<>(expectedMoves6), new HashSet<>(possibleMovesLogic(board6, 40, true)));
        TestHelper.changeTurn(board6);
        assertEquals(new HashSet<>(expectedMoves6), new HashSet<>(possibleMovesLogic(board6, 40, false)));

        byte[] board7 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 9, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, -3, 0, 0, 0,
                9, 0, 0, 0, 0, 1, 1, 1,
                0, 0, 0, 0, 0, 100, 0, 0
        };
        List<Integer> expectedMoves7 = List.of();
        assertEquals(new HashSet<>(expectedMoves7), new HashSet<>(possibleMovesLogic(board7, 48, true)));
        TestHelper.changeTurn(board7);
        assertEquals(new HashSet<>(expectedMoves7), new HashSet<>(possibleMovesLogic(board7, 48, false)));
    }
}
