package cm.node.token;

public class OperatorEqual extends Token {
    public OperatorEqual(int line, int pos) {
        super("=", line, pos);
    }

    @Override
    public String toString() {
        return "Operator = at line " + super.getLine() + ", position " + super.getPos();
    }
}
