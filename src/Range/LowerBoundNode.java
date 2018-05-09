package Range;

public class LowerBoundNode implements Node {

    private Integer value;

    public LowerBoundNode(Integer value) {
        this.value = value;
    }

    @Override
    public boolean evaluate(Integer i) {
        return i >= this.value;
    }
}
