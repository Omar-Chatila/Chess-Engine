package app.c_e.engine;

import chessModel.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static chessModel.KingMoveTracker.*;

public class Node {
    private final ArrayList<Node> children = new ArrayList<>();
    private final byte[] currentBoard;
    private final int currentMove;
    private final int value;
    private final boolean isWhite;

    public Node(byte[] currentBoard, int currentMove, boolean isWhite) {
        this.currentBoard = currentBoard;
        this.currentMove = currentMove;
        this.isWhite = isWhite;
        this.value = Evaluation.getValue(this.currentBoard);
    }

    public int getCurrentMove() {
        return currentMove;
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
            if (white && move == W_LONG_CASTLE || !white && move == B_LONG_CASTLE) {
                //LC
                if (white) {
                    newBoard[56] = 0;
                    newBoard[57] = 0;
                    newBoard[58] = 100;
                    newBoard[59] = 5;
                    newBoard[60] = 0;
                    Node node = new Node(newBoard, move, true);
                    this.children.add(node);
                } else {
                    newBoard[0] = 0;
                    newBoard[1] = 0;
                    newBoard[2] = -100;
                    newBoard[3] = -5;
                    newBoard[4] = 0;
                    this.children.add(new Node(newBoard, move, false));
                }
                continue;
            } else if (white && move == W_SHORT_CASTLE || !white && move == B_SHORT_CASTLE) {
                if (white) {
                    newBoard[60] = 0;
                    newBoard[61] = 5;
                    newBoard[62] = 100;
                    newBoard[63] = 0;
                    this.children.add(new Node(newBoard, move, true));
                } else {
                    newBoard[4] = 0;
                    newBoard[5] = -5;
                    newBoard[6] = -100;
                    newBoard[7] = 0;
                    this.children.add(new Node(newBoard, move, false));
                }
                continue;
            } else {
                moveRank = move >> 3;
                moveFile = move & 0B111;
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
        }
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public byte[] getCurrentBoard() {
        return currentBoard;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public List<Node> getAllLeaves() {
        List<Node> leaves = new ArrayList<>();
        collectLeaves(this, leaves);
        return leaves;
    }

    private void collectLeaves(Node node, List<Node> leaves) {
        if (node.isLeaf()) {
            leaves.add(node);
        } else {
            for (Node child : node.children) {
                collectLeaves(child, leaves);
            }
        }
    }

    public int size() {
        int size = 1;
        for (Node child : children) {
            size += child.size();
        }
        return size;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public int height() {
        if (children.isEmpty()) {
            return 0;
        }

        int maxHeight = 0;
        for (Node child : children) {
            int childHeight = child.height();
            maxHeight = Math.max(maxHeight, childHeight);
        }

        return maxHeight + 1;
    }

    @Override
    public String toString() {
        return "Node{" +
                "currentBoard=" + Arrays.toString(currentBoard) +
                ", currentMove=" + currentMove +
                ", value=" + value +
                ", isWhite=" + isWhite +
                '}';
    }
}
