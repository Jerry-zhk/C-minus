package cm.node.token;

public class OperatorSemicolon extends Token {
    public OperatorSemicolon(int line, int pos) {
        super(";", line, pos);
    }

    @Override
    public String toString() {
        return "Operator ; at line " + super.getLine() + ", position " + super.getPos();
    }
}
