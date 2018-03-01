package cm.node.token;

public class KeywordVar extends Token {
    public KeywordVar(int line, int pos) {
        super("Var", line, pos);
    }

    @Override
    public String toString() {
        return "Keyword Var at line " + super.getLine() + ", position " + super.getPos();
    }
}
