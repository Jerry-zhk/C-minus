package cm.node.token;

public class KeywordCall extends Token {
    public KeywordCall(int line, int pos) {
        super("Call", line, pos);
    }

    @Override
    public String toString() {
        return "Keyword Call at line " + super.getLine() + ", position " + super.getPos();
    }
}
