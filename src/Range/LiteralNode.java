package Range;

public class LiteralNode implements Node {

    private Integer value;

    public LiteralNode(Integer value) {
        this.value = value;
    }

    @Override
    public boolean evaluate(Integer i) {
        return i == this.value;
    }
}
