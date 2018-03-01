package cm.node.token;

public class KeywordPrint extends Token {
    public KeywordPrint(int line, int pos) {
        super("Print", line, pos);
    }

    @Override
    public String toString() {
        return "Keyword Print at line " + super.getLine() + ", position " + super.getPos();
    }
}
