package Range;

public class BetweenNode implements Node {

    private Node lower;
    private Node upper;

    public BetweenNode(Node lower, Node upper) {
        this.lower = lower;
        this.upper = upper;
    }

    @Override
    public boolean evaluate(Integer i) {
        return this.lower.evaluate(i) && this.upper.evaluate(i);
    }
}
