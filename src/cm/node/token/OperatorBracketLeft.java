package cm.node.token;

public class OperatorBracketLeft extends Token {
    public OperatorBracketLeft(int line, int pos) {
        super("[", line, pos);
    }

    @Override
    public String toString() {
        return "Operator [ at line " + super.getLine() + ", position " + super.getPos();
    }
}
