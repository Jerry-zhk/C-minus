package cm.node.token;

public class Identifier extends SimpleToken {
    public Identifier(String text, int line, int pos) {
        super(text, line, pos);
    }

    @Override
    public String toString() {
        return "Identifier " + super.getText() + " at line " + super.getLine() + ", position " + super.getPos();
    }

    @Override
    public boolean equals(Object obj) {

        boolean same = false;

        if (obj != null && obj instanceof Identifier)
        {
            same = (this.getText().equals(((Identifier) obj).getText()));
        }

        return same;
    }
}
