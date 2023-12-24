package app.c_e.engine;

import chessModel.Game;
import chessModel.GameHelper;
import chessModel.GameStates;
import chessModel.IntIntPair;

import java.util.Scanner;

public class Engine { // TODO: singleton for calculated moves, check stalemate and checkmate only after check calculation
    static byte[][] test =
            {{0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 3, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 4, 0, -100},
                    {0, 0, 0, 0, 0, 1, 0, 0},
                    {1, 0, 0, 0, 0, 0, 0, 5},
                    {0, 0, 0, 0, 100, 0, 0, 1},
                    {0, 0, 0, 0, 0, 0, 0, 0},
            };
    static byte[][] test2 = {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 4, 0, 0, 0},
            {0, 0, 5, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, -100},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 100, 1, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0},
    };

    public static void main(String[] args) {

        //System.out.println(KingMoveTracker.possibleMovesLogic(test, (byte) 5, (byte) 3, true));
        //playAutomatically();


        //System.out.println(Game.stalemated(false));
        //System.out.println(Evaluation.getValue(test, true, ""));
        //System.out.println(Evaluation.getValue(test2, false, ""));

        PositionValue.initEvaluation();

        playGame();
        /*GameHelper.initialize(Game.board);
        Node secondNode = new Node(Game.board, "", true);
        createPositions(secondNode, true, 5); // ohne king checked mit evaluate activity
         */
    }

    public static void playGame() {
        GameHelper.initialize(Game.board);
        Scanner scanner = new Scanner(System.in);
        while (!GameStates.isGameOver()) {
            int depth = 4;
            playEngineMove(depth, scanner);
            String move = "";
            try {
                if (scanner.hasNext()) {
                    move = scanner.nextLine();
                }
            } catch (IllegalStateException e) {
                break;
            }
            Game.executeMove(move, false);
        }
    }

    public static void playAutomatically() {
        // GameHelper.initialize(Game.board);
        Game.board = test2;
        while (!GameStates.isGameOver()) {
            int depth = 4;
            IntIntPair number = GameHelper.numberOfPieces();
            if (number.row() < 12 || number.column() < 12) depth = 5;
            if (number.row() < 6 || number.column() < 6) depth = 6;
            playEngineMove(depth, true);
            playEngineMove(depth, false);
        }
    }

    public static void playAutomatically(byte[][] board, int depth) {
        Game.board = board;
        while (!GameStates.isGameOver()) {
            playEngineMove(depth, true);
            playEngineMove(depth, false);
        }
    }

    public static String playEngineMove(int depth, Scanner scanner) {
        if (GameHelper.numberOfPieces(Game.board).row() < 6 || GameHelper.numberOfPieces(Game.board).row() < 6
        || GameHelper.numberOfPieces(Game.board).sum() <= 15) depth += 2;
        Node secondNode = new Node(Game.board, "", false);
        createPositions(secondNode, false, depth);
        BestMove bestMove2 = minimax(secondNode, depth, -Double.MAX_VALUE, Double.MAX_VALUE, false);
        Node optimalNode2 = bestMove2.node;
        BestMove bestMove3 = minimax(secondNode, 2, -Double.MAX_VALUE, Double.MAX_VALUE, false);
        Node optimalNode3 = bestMove3.node;
        if (optimalNode3.getValue() > 10000) optimalNode2 = optimalNode3;
        Game.executeMove(optimalNode2.getCurrentMove(), false);
        Game.board = optimalNode2.getCurrentBoard();
        Game.playedPositions.add(Game.board);
        System.out.println(optimalNode2.getValue());
        GameHelper.print(Game.board);
        if (Math.abs(optimalNode2.getValue()) > 10000) {
            GameStates.setGameOver(true);
            if (scanner != null) scanner.close();
        }
        return optimalNode2.getCurrentMove();
    }

    private static void playEngineMove(int depth, boolean white) {
        Node secondNode = new Node(Game.board, "", white);
        System.out.println(white);
        createPositions(secondNode, white, depth);
        BestMove bestMove2 = minimax(secondNode, depth, -Double.MAX_VALUE, Double.MAX_VALUE, white);
        Node optimalNode2 = bestMove2.node;
        Game.board = optimalNode2.getCurrentBoard();
        Game.playedPositions.add(Game.board);
        System.out.println("Value " + optimalNode2.getValue());
        GameHelper.print(Game.board);
        System.out.println(bm.node);
    }

    private static void createAllPositions(Node node, boolean startingPlayer) {
        // Calculate moves for black and white in a nested loop as needed
        node.setChildren(startingPlayer);
        for (Node n : node.getChildren()) {
            n.setChildren(!startingPlayer);
            for (Node u : n.getChildren()) {
                u.setChildren(startingPlayer);
                for (Node v : u.getChildren()) {
                    v.setChildren(!startingPlayer);
                    for (Node x : v.getChildren()) {
                        x.setChildren(startingPlayer);
                    }
                }
            }
        }
        System.out.println("Total number of nodes: " + Node.numberOfNodes);
    }

    private static void createPositions(Node node, boolean startingPlayer, int depth) {
        if (depth == 0) {
            return;
        }
        node.setChildren(startingPlayer);
        for (Node child : node.getChildren()) {
            createPositions(child, !startingPlayer, depth - 1);
        }
    }

    private static boolean isTerminal(Node position, boolean maximizingPlayer, int depth) {
        if (depth == 0) return true;
        byte[][] board = position.getCurrentBoard();
        boolean whiteChecked = Game.kingChecked(true, board);
        boolean whiteNoMoves = Game.hasNoMoves(board, true);
        if (whiteChecked && whiteNoMoves) return true; // white checkmate
        if (maximizingPlayer && !whiteChecked && whiteNoMoves) return true;
        boolean blackChecked = Game.kingChecked(false, board);
        boolean blackNoMoves = Game.hasNoMoves(board, false);
        if (!maximizingPlayer && !blackChecked && blackNoMoves) return true;
        return false;
    }

    static BestMove bm = null;

    public static BestMove minimax(Node position, int depth, double alpha, double beta, boolean maximizingPlayer) {
        if (Game.checkMated(position.getCurrentBoard(), true) || Game.checkMated(position.getCurrentBoard(), false)
                || (Game.stalemated(position.getCurrentBoard(), maximizingPlayer)) || depth == 0) {
            if (Game.checkMated(position.getCurrentBoard(), true) || Game.checkMated(position.getCurrentBoard(), false))
                bm = new BestMove(position, position.getValue(), "");
            return new BestMove(position, position.getValue(), position.getCurrentMove());
        }

        if (maximizingPlayer) {
            BestMove bestMove = new BestMove(null, -Double.MAX_VALUE, null);
            for (Node child : position.getChildren()) {
                BestMove move = minimax(child, depth - 1, alpha, beta, false);
                if (move.value > bestMove.value) {
                    bestMove.value = move.value;
                    bestMove.node = child;
                }
                alpha = Math.max(alpha, bestMove.value);
                if (beta <= alpha)
                    break;
            }
            return bestMove;
        } else {
            BestMove bestMove = new BestMove(null, Double.MAX_VALUE, null);
            for (Node child : position.getChildren()) {
                BestMove move = minimax(child, depth - 1, alpha, beta, true);
                if (move.value < bestMove.value) {
                    bestMove.value = move.value;
                    bestMove.node = child;
                }
                beta = Math.min(beta, bestMove.value);
                if (beta <= alpha)
                    break;
            }
            return bestMove;
        }
    }
}
