package Range;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Range {

    // used for tokenizing the input string
    // static here so that it's only compiled once
    private static Pattern pat = Pattern.compile("[0-9]+|-|\\+|.+?");

    private Node rootNode;

    Range(String s) throws ParseException {
        // split into tokens
        Matcher tokenizer = pat.matcher(s);
        List<String> tokens = new ArrayList<>();

        while (tokenizer.find()) {
            tokens.add(tokenizer.group(0));
        }

        // send it to the parser
        this.rootNode = new RangeParser(tokens).parse();
    }

    public boolean contains(Integer i) {
        return this.rootNode.evaluate(i);
    }
}

