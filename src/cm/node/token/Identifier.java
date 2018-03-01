package cm.node.token;

public class Identifier extends Token implements Assignable, SingleValued {
    public Identifier(String text, int line, int pos) {
        super(text, line, pos);
    }

    @Override
    public String toString() {
        return "Identifier " + super.getText() + " at line " + super.getLine() + ", position " + super.getPos();
    }
}
