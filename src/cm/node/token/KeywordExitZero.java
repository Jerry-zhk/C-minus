package cm.node.token;

public class KeywordExitZero extends Token {
    public KeywordExitZero( int line, int pos) {
        super("ExitZero", line, pos);
    }

    @Override
    public String toString() {
        return "Keyword ExitZero at line " + super.getLine() + ", position " + super.getPos();
    }
}
