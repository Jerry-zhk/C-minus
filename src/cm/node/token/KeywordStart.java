package cm.node.token;

public class KeywordStart extends Token {
    public KeywordStart(int line, int pos) {
        super("Start", line, pos);
    }

    @Override
    public String toString() {
        return "Keyword Start at line " + super.getLine() + ", position " + super.getPos();
    }
}
