package app.c_e.engine;

public class BestMove {
    public Node node; // The best Node corresponding to the best value
    public double value; // The value of the best move
    public String move;

    public BestMove(Node node, double value, String move) {
        this.node = node;
        this.value = value;
        this.move = move;
    }
}