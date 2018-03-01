package cm.node.token;

public class KeywordProcedure extends Token {
    public KeywordProcedure(int line, int pos) {
        super("Procedure", line, pos);
    }

    @Override
    public String toString() {
        return "Keyword Procedure at line " + super.getLine() + ", position " + super.getPos();
    }
}
