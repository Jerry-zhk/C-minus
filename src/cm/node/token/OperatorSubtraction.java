package cm.node.token;

public class OperatorSubtraction extends Token implements ArithmeticOperator {
    public OperatorSubtraction(int line, int pos) {
        super("-", line, pos);
    }

    @Override
    public String toString() {
        return "Operator - at line " + super.getLine() + ", position " + super.getPos();
    }
}
