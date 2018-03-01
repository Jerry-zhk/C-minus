package cm.node.token;

public class OperatorBracketRight extends Token {
    public OperatorBracketRight(int line, int pos) {
        super("]", line, pos);
    }

    @Override
    public String toString() {
        return "Operator ] at line " + super.getLine() + ", position " + super.getPos();
    }
}
