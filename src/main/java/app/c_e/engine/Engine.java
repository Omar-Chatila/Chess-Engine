package app.c_e.engine;

import chessModel.Game;
import chessModel.GameHelper;

import java.util.Scanner;

public class Engine { // TODO: singleton for calculated moves, check stalemate and checkmate only after check calculation

    static boolean isLowPieceCount() {
        return GameHelper.numberOfPieces(Game.board).row() < 6 || GameHelper.numberOfPieces(Game.board).row() < 6
                || GameHelper.numberOfPieces(Game.board).sum() <= 15;
    }

    public static int playEngineMove(int depth, Scanner scanner) {
        if (isLowPieceCount()) depth++;
        byte[] oneDboard = GameHelper.to1DBoard();
        Node secondNode = new Node(oneDboard, -1, false);
        createPositions(secondNode, false, depth);
        BestMove bestMove2 = minimax(secondNode, depth, -Double.MAX_VALUE, Double.MAX_VALUE, false);
        Node optimalNode2 = bestMove2.node;
        BestMove bestMove3 = minimax(secondNode, 2, -Double.MAX_VALUE, Double.MAX_VALUE, false);
        Node optimalNode3 = bestMove3.node;
        if (optimalNode3.getValue() > 10000) optimalNode2 = optimalNode3;
        //Game.executeMove(optimalNode2.getCurrentMove(), false);
        Game.board = GameHelper.to2DBoard(optimalNode2.getCurrentBoard());
        Game.playedPositions.add(Game.board);
        System.out.println(optimalNode2.getValue());
        GameHelper.print(Game.board);
        if (Math.abs(optimalNode2.getValue()) > 10000) {
            if (scanner != null) scanner.close();
        }
        GameHelper.print(GameHelper.to2DBoard(optimalNode2.getCurrentBoard()));
        return optimalNode2.getCurrentMove();
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


    static BestMove bm = null;

    public static BestMove minimax(Node position, int depth, double alpha, double beta, boolean maximizingPlayer) {
        if (Game.checkMated(position.getCurrentBoard(), true) || Game.checkMated(position.getCurrentBoard(), false)
                || (Game.stalemated(position.getCurrentBoard(), maximizingPlayer)) || depth == 0) {
            if (Game.checkMated(position.getCurrentBoard(), true) || Game.checkMated(position.getCurrentBoard(), false))
                bm = new BestMove(position, position.getValue(), -1);
            return new BestMove(position, position.getValue(), position.getCurrentMove());
        }

        if (maximizingPlayer) {
            BestMove bestMove = new BestMove(null, -Double.MAX_VALUE, -1);
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
            BestMove bestMove = new BestMove(null, Double.MAX_VALUE, -1);
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
