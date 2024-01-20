package app.c_e.engine;

import chessModel.*;

import java.util.ArrayList;
import java.util.List;

import static chessModel.KingMoveTracker.B_SHORT_CASTLE;
import static chessModel.KingMoveTracker.W_SHORT_CASTLE;

public class Node {
    public static long numberOfNodes;
    private final List<Node> children = new ArrayList<>();
    private final byte[] currentBoard;


    private final int currentMove;
    private double value;
    private final boolean isWhite;

    public Node(byte[] currentBoard, int currentMove, boolean isWhite) {
        this.currentBoard = currentBoard;
        this.currentMove = currentMove;
        this.isWhite = isWhite;
        setValue();
    }

    public int getCurrentMove() {
        return currentMove;
    }

    public void setValue() {
        this.value = Evaluation.getValue(this.currentBoard);
    }

    public double getValue() {
        return this.value;
    }

    public void setChildren(boolean white) {
        byte pawn = (byte) (white ? 1 : -1);
        byte knight = (byte) (white ? 3 : -3);
        byte bishop = (byte) (white ? 4 : -4);
        byte rook = (byte) (white ? 5 : -5);
        byte queen = (byte) (white ? 9 : -9);
        byte king = (byte) (white ? 100 : -100);
        for (byte i = 0; i < 64; i++) {
            if (currentBoard[i] == 0) continue;
            if (currentBoard[i] == (pawn)) {
                List<Integer> pMoves = PawnMoveTracker.possibleMovesLogic(currentBoard, i, white);
                applyMoves(white, pawn, i, pMoves);
            } else if (currentBoard[i] == (bishop)) {
                List<Integer> bMoves = BishopMoveTracker.possibleMovesLogic(currentBoard, i, white);
                applyMoves(white, bishop, i, bMoves);
            } else if (currentBoard[i] == (knight)) {
                List<Integer> nMoves = KnightMoveTracker.possibleMovesLogic(currentBoard, i, white);
                applyMoves(white, knight, i, nMoves);
            } else if (currentBoard[i] == (rook)) {
                List<Integer> rMoves = RookMoveTracker.possibleMovesLogic(currentBoard, i, white);
                applyMoves(white, rook, i, rMoves);
            } else if (currentBoard[i] == (queen)) {
                List<Integer> qMoves = QueenMoveTracker.possibleMovesLogic(currentBoard, i, white);
                applyMoves(white, queen, i, qMoves);
            } else if (currentBoard[i] == (king)) {
                List<Integer> kMoves = KingMoveTracker.possibleMovesLogic(currentBoard, i, white);
                applyMoves(white, king, i, kMoves);
            }
        }
    }

    private void applyMoves(boolean white, byte piece, int i, List<Integer> pMoves) {
        for (int move : pMoves) {
            byte[] newBoard = GameHelper.copyBoard(currentBoard);
            int moveRank;
            int moveFile;
            if (white && move == 69 || !white && move == 690) {
                //LC
                if (white) {
                    newBoard[56] = 0;
                    newBoard[57] = 0;
                    newBoard[58] = 100;
                    newBoard[59] = 5;
                    newBoard[60] = 0;
                    Node node = new Node(newBoard, move, true);
                    node.setValue();
                    this.children.add(node);
                } else {
                    newBoard[0] = 0;
                    newBoard[1] = 0;
                    newBoard[2] = -100;
                    newBoard[3] = -5;
                    newBoard[4] = 0;
                    Node node = new Node(newBoard, move, false);
                    node.setValue();
                    this.children.add(node);
                }
                numberOfNodes++;
                return;
            } else if (white && move == W_SHORT_CASTLE || !white && move == B_SHORT_CASTLE) {
                if (white) {
                    newBoard[60] = 0;
                    newBoard[61] = 5;
                    newBoard[62] = 100;
                    newBoard[63] = 0;
                    Node node = new Node(newBoard, move, true);
                    node.setValue();
                    this.children.add(node);
                } else {
                    newBoard[4] = 0;
                    newBoard[5] = -5;
                    newBoard[6] = -100;
                    newBoard[7] = 0;
                    Node node = new Node(newBoard, move, false);
                    node.setValue();
                    this.children.add(node);
                }
                numberOfNodes++;
                return;
            } else {
                moveRank = move / 8;
                moveFile = move % 8;
            }
            newBoard[i] = 0;
            if (Math.abs(piece) == 1 && (moveRank == 7 || moveRank == 0)) {
                // TODO: promote to all pieces by adding one node every promoted piece
                newBoard[moveRank * 8 + moveFile] = (byte) (white ? 9 : -9);
            } else {
                newBoard[moveRank * 8 + moveFile] = piece;
            }
            Node node = new Node(newBoard, move, white);
            this.children.add(node);
            numberOfNodes++;
        }
    }

    public List<Node> getChildren() {
        return children;
    }

    public byte[] getCurrentBoard() {
        return currentBoard;
    }

    public Node getBestMove() {
        List<Node> children = getChildren();
        if (!children.isEmpty()) {
            return children.get(children.size() - 1); // Best move is at the end of the sorted list
        } else {
            return null; // No children available
        }
    }
}
