package Range;

public class UpperBoundNode implements Node {

    private Integer value;

    public UpperBoundNode(Integer value) {
        this.value = value;
    }

    @Override
    public boolean evaluate(Integer i) {
        return i <= this.value;
    }
}
