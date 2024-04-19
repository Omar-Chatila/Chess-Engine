package app.c_e.engine;

import chessModel.Game;
import chessModel.GameHelper;
import chessModel.KingMoveTracker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Engine {

    static boolean isLowPieceCount() {
        return GameHelper.numberOfPieces(Game.board).row() < 6 || GameHelper.numberOfPieces(Game.board).row() < 6
                || GameHelper.numberOfPieces(Game.board).sum() <= 15;
    }


    static boolean setBKMoved = false;

    public static int playEngineMove(int depth) {
        if (GameHelper.onlyOneLeft()) depth = 7;
        // create chessboard tree
        byte[] oneDboard = GameHelper.to1DBoard();
        Node rootNode = new Node(oneDboard, -1, true);
        createPositions(rootNode, false, 1);

        int remainingDepth = depth - 1;
        List<Node> positionsLevelTwo = rootNode.getAllLeaves();
        List<Node> newRoots = new ArrayList<>(positionsLevelTwo.size());
        for (int i = 0; i < positionsLevelTwo.size(); i++) {
            newRoots.add(new Node(oneDboard, -1, true));
        }

        int threadCount = positionsLevelTwo.size();
        ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        Runnable[] tasks = new Runnable[threadCount];

        PriorityQueue<BestMove> bestMoves = new PriorityQueue<>((o1, o2) -> (int)(o1.value - o2.value));

        for (int i = 0; i < tasks.length; i++) {
            Node currentRoot = newRoots.get(i);
            Node currentNode = positionsLevelTwo.get(i);
            currentRoot.getChildren().add(currentNode);
            int finalDepth = depth;
            tasks[i] = () -> {
                createPositions(currentNode, true, remainingDepth);
                BestMove currentBest = minimax(currentRoot, finalDepth, -Double.MAX_VALUE, Double.MAX_VALUE, false);
                bestMoves.add(currentBest);
                latch.countDown();
            };
        }

        for (Runnable task : tasks) {
            threadPool.execute(task);
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            System.err.println("Error");
        }

        threadPool.shutdown();

        BestMove currentBest = bestMoves.poll();

        // set black king moved
        int posBefore = 0;
        if (!setBKMoved) posBefore = Game.findKingPosition(false, oneDboard);
        // execute best move
        assert currentBest != null;
        Game.board = GameHelper.to2DBoard(currentBest.node.getCurrentBoard());
        if (!setBKMoved) {
            int posAfter = Game.findKingPosition(false, GameHelper.to1DBoard());
            if (posBefore != posAfter) {
                setBKMoved = true;
                KingMoveTracker.blackKingHasMoved = true;
            }
        }

        Game.playedPositions.add(Game.board);
        System.out.println(currentBest.node.getValue());
        rootNode.getChildren().clear();
        System.gc();
        return currentBest.node.getCurrentMove();
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

    public static BestMove minimax(Node position, int depth, double alpha, double beta, boolean maximizingPlayer) {
        if (Game.checkMated(position.getCurrentBoard(), true) || Game.checkMated(position.getCurrentBoard(), false)
                || (Game.stalemated(position.getCurrentBoard(), maximizingPlayer)) || depth == 0) {
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
