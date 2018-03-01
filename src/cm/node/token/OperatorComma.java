package cm.node.token;

public class OperatorComma extends Token {
    public OperatorComma(int line, int pos) {
        super(",", line, pos);
    }

    @Override
    public String toString() {
        return "Operator , at line " + super.getLine() + ", position " + super.getPos();
    }
}
