package app.c_e.tests;


import chessModel.KingMoveTracker;
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
                0, 0, 0, -4, 0, 0, -9, 0,
                0, 3, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, -9, 0, 0,
                4, 0, 0, K, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 1, 0, -4, -1, 0, 0, 0,
                0, 0, 1, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves = List.of(34);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 35, true)));
        TestHelper.changeTurn(board);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 35, false)));

        byte[] board2 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, -4, -5, 0, 0, 0,
                0, 3, 0, 0, 0, 0, 0, 0,
                1, 0, 0, 0, 0, 3, 0, 0,
                4, K, -1, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 1, 0, -4, -1, 0, 0, 0,
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
                0, 0, 0, -4, -5, 0, 0, 0,
                0, 3, 0, 0, 0, 0, 0, 0,
                1, 0, 0, 1, 1, 3, 0, 0,
                4, 0, -1, 1, K, 1, 0, 0,
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
                1, 0, 0, 0, 0, 0, 0, -5,
                4, 0, 1, -1, K, -1, 0, 0,
                0, 0, 0, -4, -1, -1, 0, 0,
                0, 1, 0, -4, -1, 0, 0, 0,
                0, 0, 1, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves4 = List.of(43, 45, 35, 37);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 36, true)));
        TestHelper.changeTurn(board4);
        assertEquals(new HashSet<>(), new HashSet<>(possibleMovesLogic(board4, 36, false)));

        byte[] board5 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, -4, -5, 0, 0, -3,
                0, 3, 0, 0, 0, 0, 0, 0,
                1, 0, 0, -1, -1, -3, 0, 0,
                4, 0, -1, -1, 0, -1, 0, K,
                0, 0, 0, -4, -1, -1, 0, 0,
                0, 1, 0, -4, -1, 1, 0, -3,
                0, 0, 1, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves5 = List.of(47, 31);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board5, 39, true)));
        TestHelper.changeTurn(board5);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board5, 39, false)));
    }


    @Test
    public void testShortCastleWhite() {
        KingMoveTracker.whiteKingHasMoved = false;
        // King can castle
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

        KingMoveTracker.whiteKingHasMoved = true;
        // King cannot castle bcs has moved already
        byte[] board11 = {
                0, 0, -5, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, K, 0, 0, 5
        };
        List<Integer> expectedMoves11 = List.of(61, 59, 52, 51, 53);
        assertEquals(new HashSet<>(expectedMoves11), new HashSet<>(possibleMovesLogic(board11, 60, true)));

        KingMoveTracker.whiteKingHasMoved = false;
        // King cannot castle because piece targets dest square
        byte[] board2 = {
                0, 0, -5, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0, -5, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, K, 0, 0, 5
        };
        List<Integer> expectedMoves2 = List.of(61, 59, 52, 51, 53);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 60, true)));

        // King cannot castle because piece targets square next to king
        byte[] board3 = {
                0, 0, -5, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, -9, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, K, 0, 0, 5
        };
        List<Integer> expectedMoves3 = List.of(53);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 60, true)));

        // King cannot castle because checked
        byte[] board4 = {
                0, 0, -5, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, -5, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, K, 0, 0, 5
        };
        List<Integer> expectedMoves4 = List.of(61, 59, 51, 53);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 60, true)));

        // King cannot castle because own piece in the way
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

        // King cannot castle because opp piece in the way
        byte[] board6 = {
                0, 0, -5, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, K, 0, -3, 5
        };
        List<Integer> expectedMoves6 = List.of(61, 59, 51, 53);
        assertEquals(new HashSet<>(expectedMoves6), new HashSet<>(possibleMovesLogic(board6, 60, true)));

        // King cannot castle because he's on the wrong side of the board
        byte[] board7 = {
                0, 0, 0, 0, K, 0, 0, 5,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, -3, 5
        };
        List<Integer> expectedMoves7 = List.of(5, 3, 11, 12, 13);
        assertEquals(new HashSet<>(expectedMoves7), new HashSet<>(possibleMovesLogic(board7, 4, true)));
    }

    @Test
    public void testShortCastleBlack() {
        KingMoveTracker.blackKingHasMoved = false;
        // King can castle
        byte[] board = {
                0, 0, -5, 0, -K, 0, 0, -5,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 5
        };
        List<Integer> expectedMoves = List.of(3, 5, 11, 12, 13, 691);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 4, false)));

        KingMoveTracker.blackKingHasMoved = true;
        // King cannot castle bcs has moved already
        byte[] board11 = {
                0, 0, -5, 0, -K, 0, 0, -5,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 5
        };
        List<Integer> expectedMoves11 = List.of(3, 5, 11, 12, 13);
        assertEquals(new HashSet<>(expectedMoves11), new HashSet<>(possibleMovesLogic(board11, 4, false)));

        KingMoveTracker.blackKingHasMoved = false;
        // King cannot castle because piece targets dest square
        byte[] board2 = {
                0, 0, -5, 0, -K, 0, 0, -5,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 5, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 5
        };
        List<Integer> expectedMoves2 = List.of(3, 5, 11, 12, 13);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 4, false)));

        // King cannot castle because piece targets square next to king
        byte[] board3 = {
                0, 0, -5, 0, -K, 0, 0, -5,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 9, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 5
        };
        List<Integer> expectedMoves3 = List.of(13);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 4, false)));

        // King cannot castle because checked
        byte[] board4 = {
                0, 0, -5, 0, -K, 0, 0, -5,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 5, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 5
        };
        List<Integer> expectedMoves4 = List.of(3, 5, 11, 13);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 4, false)));

        // King cannot castle because own piece in the way
        byte[] board5 = {
                0, 0, -5, 0, -K, 0, -3, -5,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves5 = List.of(3, 5, 11, 13, 12);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board5, 4, false)));

        // King cannot castle because opp piece in the way
        byte[] board6 = {
                0, 0, -5, 0, -K, 0, 3, -5,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves6 = List.of(3, 5, 11, 13);
        assertEquals(new HashSet<>(expectedMoves6), new HashSet<>(possibleMovesLogic(board6, 4, false)));

        // King cannot castle because he's on the wrong side of the board
        byte[] board7 = {
                0, 0, 0, 0, 0, 0, 0, 5,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, -K, 0, 0, -5
        };
        List<Integer> expectedMoves7 = List.of(61, 59, 52, 53, 51);
        assertEquals(new HashSet<>(expectedMoves7), new HashSet<>(possibleMovesLogic(board7, 60, false)));
    }

    @Test
    public void testLongCastleWhite() {
        KingMoveTracker.whiteKingHasMoved = false;
        // King can castle
        byte[] board = {
                0, 0, -5, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                5, 0, 0, 0, K, 0, 0, 0
        };
        List<Integer> expectedMoves = List.of(61, 59, 52, 51, 53, 69);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 60, true)));

        KingMoveTracker.whiteKingHasMoved = true;
        // King can castle
        byte[] board11 = {
                0, 0, -5, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                5, 0, 0, 0, K, 0, 0, 0
        };
        List<Integer> expectedMoves11 = List.of(61, 59, 52, 51, 53);
        assertEquals(new HashSet<>(expectedMoves11), new HashSet<>(possibleMovesLogic(board11, 60, true)));

        KingMoveTracker.whiteKingHasMoved = false;

        // King cannot castle because piece targets dest square
        byte[] board2 = {
                0, 0, -5, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, -5, 0, 0, 0, -5, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                5, 0, 0, 0, K, 0, 0, 0
        };
        List<Integer> expectedMoves2 = List.of(61, 59, 52, 51, 53);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 60, true)));

        // King cannot castle because piece targets square next to king
        byte[] board3 = {
                0, 0, -5, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, -9, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                5, 0, 0, 0, K, 0, 0, 5
        };
        List<Integer> expectedMoves3 = List.of(53);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 60, true)));

        // King cannot castle because checked
        byte[] board4 = {
                0, 0, -5, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, -5, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                5, 0, 0, 0, K, 0, 0, 5
        };
        List<Integer> expectedMoves4 = List.of(61, 59, 51, 53);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 60, true)));

        // King cannot castle because own piece in the way
        byte[] board5 = {
                0, 0, -5, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                5, 3, 0, 0, K, 0, 3, 5
        };
        List<Integer> expectedMoves5 = List.of(61, 59, 51, 53, 52);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board5, 60, true)));

        // King cannot castle because opp piece in the way
        byte[] board6 = {
                0, 0, -5, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                5, 0, -3, 0, K, 0, -3, 5
        };
        List<Integer> expectedMoves6 = List.of(61, 59, 51, 53);
        assertEquals(new HashSet<>(expectedMoves6), new HashSet<>(possibleMovesLogic(board6, 60, true)));

        // King cannot castle because he's on the wrong side of the board
        byte[] board7 = {
                5, 0, 0, 0, K, 0, 0, 5,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, -3, 5
        };
        List<Integer> expectedMoves7 = List.of(5, 3, 11, 12, 13);
        assertEquals(new HashSet<>(expectedMoves7), new HashSet<>(possibleMovesLogic(board7, 4, true)));
    }

    @Test
    public void testLongCastleBlack() {
        KingMoveTracker.blackKingHasMoved = false;
        // King can castle
        byte[] board = {
                -5, 0, 0, 0, -K, 0, 0, -5,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 5
        };
        List<Integer> expectedMoves = List.of(3, 5, 11, 12, 13, 690, 691);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 4, false)));

        KingMoveTracker.blackKingHasMoved = true;
        // King can castle
        byte[] board11 = {
                -5, 0, 0, 0, -K, 0, 0, -5,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 5
        };
        List<Integer> expectedMoves11 = List.of(3, 5, 11, 12, 13);
        assertEquals(new HashSet<>(expectedMoves11), new HashSet<>(possibleMovesLogic(board11, 4, false)));

        KingMoveTracker.blackKingHasMoved = false;

        // King cannot castle because piece targets dest square
        byte[] board2 = {
                -5, 0, 0, 0, -K, 0, 0, -5,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 5, 0, 0, 0, 5, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 5
        };
        List<Integer> expectedMoves2 = List.of(3, 5, 11, 12, 13);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 4, false)));

        // King cannot castle because piece targets square next to king
        byte[] board3 = {
                -5, 0, 0, 0, -K, 0, 0, -5,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 9, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 5
        };
        List<Integer> expectedMoves3 = List.of(13);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 4, false)));

        // King cannot castle because checked
        byte[] board4 = {
                -5, 0, 0, 0, -K, 0, 0, -5,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 5, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 5
        };
        List<Integer> expectedMoves4 = List.of(3, 5, 11, 13);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 4, false)));

        // King cannot castle because own piece in the way
        byte[] board5 = {
                -5, 0, -5, 0, -K, 0, -3, -5,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves5 = List.of(3, 5, 11, 13, 12);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board5, 4, false)));

        // King cannot castle because opp piece in the way
        byte[] board6 = {
                -5, 3, 0, 0, -K, 0, 3, -5,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves6 = List.of(3, 5, 13);
        assertEquals(new HashSet<>(expectedMoves6), new HashSet<>(possibleMovesLogic(board6, 4, false)));

        // King cannot castle because he's on the wrong side of the board
        byte[] board7 = {
                0, 0, 0, 0, 0, 0, 0, 5,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                -5, 0, 0, 0, -K, 0, 0, -5
        };
        List<Integer> expectedMoves7 = List.of(61, 59, 52, 53, 51);
        assertEquals(new HashSet<>(expectedMoves7), new HashSet<>(possibleMovesLogic(board7, 60, false)));
    }

    @Test
    public void testNoMoves() {
        byte[] board = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, -5, -4, 0, 0, -9, 0,
                0, 3, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, -9, 0, 0,
                4, 0, 0, K, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 1, 0, -4, -1, 0, 0, 0,
                0, 0, 1, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves = List.of();
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 35, true)));
        TestHelper.changeTurn(board);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 35, false)));

        byte[] board2 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, -4, -5, 0, 0, 0,
                0, -5, 0, 0, 0, 0, 0, 0,
                1, 0, 0, 0, 0, 3, 0, 0,
                K, 0, -1, 0, 0, 0, 0, 0,
                0, 0, 0, -9, 0, 0, 0, 0,
                0, 1, 0, -4, -1, 0, 0, 0,
                0, 0, 1, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves2 = new ArrayList<>(List.of());
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 32, true)));
        TestHelper.changeTurn(board2);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 32, false)));

        byte[] board3 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 3, 3, 3,
                0, -5, 0, 0, 0, 0, K, 0,

        };
        List<Integer> expectedMoves3 = List.of();
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 62, true)));
        TestHelper.changeTurn(board3);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 62, false)));

        byte[] board4 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, -4, -5, 0, 0, 0,
                0, 3, 0, 0, 0, 0, 0, 0,
                1, 0, 0, 0, 0, 0, 0, -5,
                -5, 0, 1, -1, 0, -1, 0, 0,
                0, 0, -3, -4, -1, -1, 0, 0,
                0, 1, 0, -3, -1, 0, 0, 0,
                K, 1, 1, 0, 0, 0, 0, 0
        };
        List<Integer> expectedMoves4 = List.of();
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 56, true)));
        TestHelper.changeTurn(board4);
        assertEquals(new HashSet<>(), new HashSet<>(possibleMovesLogic(board4, 56, false)));
    }

    @Test
    public void testKingOpposition() {
        // upper opposition
        byte[] board = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, -K, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, K, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,

        };
        List<Integer> expectedMoves = List.of(52, 50, 58, 59, 60);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 51, true)));
        TestHelper.changeTurn(board);
        assertEquals(new HashSet<>(expectedMoves), new HashSet<>(possibleMovesLogic(board, 51, false)));

        byte[] board2 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, -K, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, K, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,

        };
        List<Integer> expectedMoves2 = List.of(52, 50, 58, 59, 60, 44);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 51, true)));
        TestHelper.changeTurn(board2);
        assertEquals(new HashSet<>(expectedMoves2), new HashSet<>(possibleMovesLogic(board2, 51, false)));


        byte[] board3 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, -K, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, K, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,

        };
        List<Integer> expectedMoves3 = List.of(52, 50, 58, 59, 60, 42);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 51, true)));
        TestHelper.changeTurn(board3);
        assertEquals(new HashSet<>(expectedMoves3), new HashSet<>(possibleMovesLogic(board3, 51, false)));

        // right opposition
        byte[] board4 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, K, 0, -K, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,

        };
        List<Integer> expectedMoves4 = List.of(42, 43, 50, 58, 59);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 51, true)));
        TestHelper.changeTurn(board4);
        assertEquals(new HashSet<>(expectedMoves4), new HashSet<>(possibleMovesLogic(board4, 51, false)));

        byte[] board5 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, -K, 0, 0,
                0, 0, 0, K, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,

        };
        List<Integer> expectedMoves5 = List.of(42, 43, 50, 58, 59, 60);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board5, 51, true)));
        TestHelper.changeTurn(board5);
        assertEquals(new HashSet<>(expectedMoves5), new HashSet<>(possibleMovesLogic(board5, 51, false)));

        byte[] board6 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, K, 0, 0, 0, 0,
                0, 0, 0, 0, 0, -K, 0, 0,

        };
        List<Integer> expectedMoves6 = List.of(42, 43, 50, 58, 59, 44);
        assertEquals(new HashSet<>(expectedMoves6), new HashSet<>(possibleMovesLogic(board6, 51, true)));
        TestHelper.changeTurn(board6);
        assertEquals(new HashSet<>(expectedMoves6), new HashSet<>(possibleMovesLogic(board6, 51, false)));

        // left opposition
        byte[] board7 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, -K, 0, K, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,

        };
        List<Integer> expectedMoves7 = List.of(52, 43, 59, 44, 60);
        assertEquals(new HashSet<>(expectedMoves7), new HashSet<>(possibleMovesLogic(board7, 51, true)));
        TestHelper.changeTurn(board7);
        assertEquals(new HashSet<>(expectedMoves7), new HashSet<>(possibleMovesLogic(board7, 51, false)));

        byte[] board8 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, K, 0, 0, 0, 0,
                0, -K, 0, 0, 0, 0, 0, 0,

        };
        List<Integer> expectedMoves8 = List.of(42, 43, 44, 52, 60, 59);
        assertEquals(new HashSet<>(expectedMoves8), new HashSet<>(possibleMovesLogic(board8, 51, true)));
        TestHelper.changeTurn(board8);
        assertEquals(new HashSet<>(expectedMoves8), new HashSet<>(possibleMovesLogic(board8, 51, false)));


        byte[] board9 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, -K, 0, 0, 0, 0, 0, 0,
                0, 0, 0, K, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,

        };
        List<Integer> expectedMoves9 = List.of(43, 44, 52, 60, 59, 58);
        assertEquals(new HashSet<>(expectedMoves9), new HashSet<>(possibleMovesLogic(board9, 51, true)));
        TestHelper.changeTurn(board9);
        assertEquals(new HashSet<>(expectedMoves9), new HashSet<>(possibleMovesLogic(board9, 51, false)));

        byte[] board10 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, K, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, -K, 0, 0, 0, 0,

        };
        List<Integer> expectedMoves10 = List.of(44, 42, 34, 35, 36);
        assertEquals(new HashSet<>(expectedMoves10), new HashSet<>(possibleMovesLogic(board10, 43, true)));
        TestHelper.changeTurn(board10);
        assertEquals(new HashSet<>(expectedMoves10), new HashSet<>(possibleMovesLogic(board10, 43, false)));

        //edge
        List<Integer> expectedMoves11 = List.of(58, 60);
        assertEquals(new HashSet<>(expectedMoves11), new HashSet<>(possibleMovesLogic(board10, 59, true)));
        TestHelper.changeTurn(board10);
        assertEquals(new HashSet<>(expectedMoves11), new HashSet<>(possibleMovesLogic(board10, 59, false)));

        // corner
        byte[] board11 = {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, -K, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                K, 0, 0, 0, 0, 0, 0, 0,

        };
        List<Integer> expectedMoves12 = List.of(57);
        assertEquals(new HashSet<>(expectedMoves12), new HashSet<>(possibleMovesLogic(board11, 56, true)));
        TestHelper.changeTurn(board11);
        assertEquals(new HashSet<>(expectedMoves12), new HashSet<>(possibleMovesLogic(board11, 56, false)));
    }

}
