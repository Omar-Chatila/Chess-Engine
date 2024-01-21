package app.c_e.engine;

import chessModel.Game;
import chessModel.GameHelper;
import chessModel.KingMoveTracker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
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
        if (isLowPieceCount()) depth++;
        // create chessboard tree
        byte[] oneDboard = GameHelper.to1DBoard();
        Node rootNode = new Node(oneDboard, -1, false);
        createPositions(rootNode, false, 2);

        List<Node> positionsLevelTwo = rootNode.getAllLeaves();
        ConcurrentLinkedQueue<BestMove> bestMoves = new ConcurrentLinkedQueue<>();

        int threadCount = positionsLevelTwo.size();
        ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        final int remainingDepth = 2;

        Runnable[] tasks = new Runnable[threadCount];
        for (int i = 0; i < tasks.length; i++) {
            int finalI = i;
            tasks[i] = () -> {
                Node currentNode = positionsLevelTwo.get(finalI);
                createPositions(currentNode, false, remainingDepth);
                BestMove currentBest = minimax(currentNode, remainingDepth, -Double.MAX_VALUE, Double.MAX_VALUE, false);
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

        List<BestMove> bestMovesList = new ArrayList<>(bestMoves);
        System.out.println("move list size: " + bestMovesList.size());
        BestMove bestMove = new BestMove(null, 1000000000, -1);
        boolean allBlack = false;
        for (BestMove move : bestMovesList) {
            allBlack |= move.node.isWhite();
            if (move.value < bestMove.value) {
                bestMove = move;
            }
        }

        System.out.println("all black " + allBlack);

        Node bestNode = bestMove.node.getParent().getParent();
        System.out.println(GameHelper.to2DBoardString(GameHelper.to2DBoard(bestNode.getCurrentBoard())));
        System.out.println(bestNode.isWhite());

        // set black king moved
        int posBefore = 0;
        if (!setBKMoved) posBefore = Game.findKingPosition(false, oneDboard);
        // execute best move
        Game.board = GameHelper.to2DBoard(bestNode.getCurrentBoard());
        if (!setBKMoved) {
            int posAfter = Game.findKingPosition(false, GameHelper.to1DBoard());
            if (posBefore != posAfter) {
                setBKMoved = true;
                KingMoveTracker.blackKingHasMoved = true;
            }
        }

        Game.playedPositions.add(Game.board);
        System.out.println(bestNode.getValue());
        GameHelper.print(GameHelper.to2DBoard(bestNode.getCurrentBoard()));
        return bestNode.getCurrentMove();
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
