package app.c_e.engine;

public class BestMove {
    public Node node; // The best Node corresponding to the best value
    public double value; // The value of the best move
    public int move;

    public BestMove(Node node, double value, int move) {
        this.node = node;
        this.value = value;
        this.move = move;
    }
}