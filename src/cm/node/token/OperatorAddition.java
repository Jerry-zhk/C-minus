package cm.node.token;

public class OperatorAddition extends Token implements ArithmeticOperator {
    public OperatorAddition(int line, int pos) {
        super("+", line, pos);
    }

    @Override
    public String toString() {
        return "Operator + at line " + super.getLine() + ", position " + super.getPos();
    }
}
