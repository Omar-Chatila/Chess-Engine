package app.c_e.tests;


import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static chessModel.KingMoveTracker.possibleMovesLogic;
import static org.testng.AssertJUnit.assertEquals;

public class KingMoveTests {
    public static final byte K = 100;

    @Test
    public void testFreeMoves() {
        byte[] board = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, K, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves = List.of(34, 36, 43, 28, 27, 26, 44, 42);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 35, true)));
        TestHelper.changeTurn(board);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 35, false)));


        byte[] board2 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, K
        };
        List<Integer> expectedMoves2 = List.of(62, 55, 54);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 63, true)));
        TestHelper.changeTurn(board);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 63, false)));


        byte[] board3 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                K, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves3 = List.of(40, 24, 33, 25, 41);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 32, true)));
        TestHelper.changeTurn(board);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 32, false)));

        byte[] board4 = {
                0, 0, 0, 0, 0, 0, 0, K,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves4 = List.of(6, 15, 14);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 7, true)));
        TestHelper.changeTurn(board);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 7, false)));
    }

    @Test
    public void testCrowdedBoard() {
        byte[] board = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0,-4, 0, 0,-9, 0,
                0, 3, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,-9, 0, 0,
                4, 0, 0, K, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 1, 0,-4,-1, 0, 0, 0,
                0, 0, 1, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves = List.of(34);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 35, true)));
        TestHelper.changeTurn(board);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 35, false)));

        byte[] board2 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0,-4, -5, 0, 0, 0,
                0, 3, 0, 0, 0, 0, 0, 0,
                1, 0, 0, 0, 0, 3, 0, 0,
                4, K,-1, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 1, 0,-4, -1, 0, 0, 0,
                0, 0, 1, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves2 = new ArrayList<>(List.of(34, 26, 40));
        possibleMovesLogic(board2, 33, false);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 33, true)));
        TestHelper.changeTurn(board2);
        expectedMoves2.add(41);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 33, false)));

        byte[] board3 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0,-4,-5, 0, 0, 0,
                0, 3, 0, 0, 0, 0, 0, 0,
                1, 0, 0, 1, 1, 3, 0, 0,
                4, 0,-1, 1, K, 1, 0, 0,
                0, 0, 0, 1, 1, 1, 0, 0,
                0, 1, 0,-4,-1, 0, 0, 0,
                0, 0, 1, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves3 = List.of();
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 36, true)));
        TestHelper.changeTurn(board3);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 36, false)));

        byte[] board4 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0,-4,-5, 0, 0, 0,
                0, 3, 0, 0, 0, 0, 0, 0,
                1, 0, 0, 0, 0, 0, 0,-5,
                4, 0, 1,-1, K,-1, 0, 0,
                0, 0, 0,-4,-1,-1, 0, 0,
                0, 1, 0,-4,-1, 0, 0, 0,
                0, 0, 1, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves4 = List.of(43, 45, 35, 37);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 36, true)));
        TestHelper.changeTurn(board4);
        assertEquals(new HashSet<>(), new HashSet<>(possibleMovesLogic(board4, 36, false)));

        byte[] board5 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0,-4,-5, 0, 0,-3,
                0, 3, 0, 0, 0, 0, 0, 0,
                1, 0, 0,-1,-1,-3, 0, 0,
                4, 0,-1,-1, 0,-1, 0, K,
                0, 0, 0,-4,-1,-1, 0, 0,
                0, 1, 0,-4,-1, 1, 0, -3,
                0, 0, 1, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves5 = List.of(47, 31);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board5, 39, true)));
        TestHelper.changeTurn(board5);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board5, 39, false)));

    }


    @Test
    public void testShortCastleWhite() {
        byte[] board = {
                0, 0, -5, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, K, 0, 0, 5
        };
        List<Integer> expectedMoves = List.of(61, 59, 52, 51, 53, 420);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 60, true)));

        byte[] board2 = {
                0, 0, -5, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0,-5, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, K, 0, 0, 5
        };
        List<Integer> expectedMoves2 = List.of(61, 59, 52, 51, 53);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 60, true)));

        byte[] board3 = {
                0, 0, -5, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4,-9, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, K, 0, 0, 5
        };
        List<Integer> expectedMoves3 = List.of(53);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 60, true)));

        byte[] board4 = {
                0, 0, -5, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0,-5, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, K, 0, 0, 5
        };
        List<Integer> expectedMoves4 = List.of(61, 59, 51, 53);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 60, true)));

        byte[] board5 = {
                0, 0, -5, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, K, 0, 3, 5
        };
        List<Integer> expectedMoves5 = List.of(61, 59, 51, 53, 52);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board5, 60, true)));
    }

    @Test
    public void testShortCastleBlack() { //TODO: implement
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

    //TODO check long castle moves for both
    //TODO check no moves positions
    //TODO check king opposition
}
