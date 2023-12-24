package chessModel;

public record IntIntPair(int row, int column) {

    @Override
    public String toString() {
        return row + "" + column;
    }

    public int sum() {
        return row + column;
    }
}
