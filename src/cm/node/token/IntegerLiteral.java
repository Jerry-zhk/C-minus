package cm.node.token;

public class IntegerLiteral extends SimpleToken {
    public IntegerLiteral(String text, int line, int pos) {
        super(text, line, pos);
    }

    @Override
    public String toString() {
        return "IntegerLiteral " + super.getText() + " at line " + super.getLine() + ", position " + super.getPos();
    }
}
