package Range;

import java.util.List;
import java.util.regex.Pattern;

class RangeParser {
    private static Pattern numberPat = Pattern.compile("^[0-9]+$");

    private List<String> tokens;
    private Integer upto;

    public RangeParser(List<String> tokens) {
        this.tokens = tokens;
        this.upto = 0;
    }

    /**
     * returns the current token we're up to
     * @return String token
     */
    private String peek() {
        if (end()) {
            return null;
        }
        return this.tokens.get(upto);
    }

    /**
     * munches and returns the current token
     * @return String token
     * @throws ParseException
     */
    private String consume() throws ParseException {
        if (end()) {
            throw new ParseException("fell off end of tokens");
        }
        String value = this.peek();
        upto += 1;
        return value;
    }

    /**
     * munches and returns the current token, asserting integer
     * @return Integer token
     * @throws ParseException
     */
    private Integer consumeInt() throws ParseException {
        if (end()) {
            throw new ParseException("fell off end of tokens");
        }
        Integer value;
        try {
            value = Integer.parseInt(this.peek());
        } catch (NumberFormatException e) {
            throw new ParseException(e.getMessage());
        }
        upto += 1;
        return value;
    }

    /**
     * Checks if we hit the end of the tokens.
     * If this returns true, then peek() will fail because we don't have any more tokens
     * @return boolean
     */
    private boolean end() {
        return upto >= tokens.size();
    }

    public Node parse() throws ParseException {
        Node node = null;
        if (end()) {
            node = new UnboundedNode();

        } else if (numberPat.matcher(peek()).matches()) {
            // expression begins with a number
            Integer value1 = consumeInt();

            // could be a literal single value, a between range, or a lower bounded range

            // single literal value
            if (end()) {
                return new LiteralNode(value1);

            } else if (peek().equals("-")) {
                consume();
                if (end()) {
                    node = new LowerBoundNode(value1);
                } else {
                    Integer value2 = consumeInt();
                    if (value1 <= value2) {
                        node = new BetweenNode(new LowerBoundNode(value1), new UpperBoundNode(value2));
                    } else {
                        node = new BetweenNode(new LowerBoundNode(value2), new UpperBoundNode(value1));
                    }
                }

            } else if (peek().equals("+")) {
                // lower bounded range
                consume();
                if (!end()) {
                    throw new ParseException("'+' must end a lower bounded range only");
                }
                return new LowerBoundNode(value1);
            }


        } else if (peek().equals("-")) {
            // parse upperBound expression
            // consume the operator, consume the next int
            consume();
            Integer value = consumeInt();
            node = new UpperBoundNode(value);
        }

        if (!end()) {
            throw new ParseException("invalid trailing tokens");
        }
        return node;
    }
}
