package cm.node.token;

public class KeywordEnd extends Token {
    public KeywordEnd(int line, int pos) {
        super("End", line, pos);
    }

    @Override
    public String toString() {
        return "Keyword End at line " + super.getLine() + ", position " + super.getPos();
    }
}
