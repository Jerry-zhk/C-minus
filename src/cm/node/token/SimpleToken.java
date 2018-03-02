package cm.node.token;

import cm.node.base.Assignable;

public abstract class SimpleToken extends Token implements Assignable {

    public SimpleToken(String text, int line, int pos) {
        super(text, line, pos);
    }
}
