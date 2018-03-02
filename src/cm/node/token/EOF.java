package cm.node.token;

public class EOF extends Token {
    public EOF(int line, int pos) {
        super("", line, pos);
    }

    @Override
    public String toString() {
        return "EOF at line " + super.getLine() + " position " + super.getPos();
    }
}
