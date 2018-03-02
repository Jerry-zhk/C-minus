package cm.node.token;

import cm.node.base.Node;

public class Token extends Node {

    private String text;
    private int line;
    private int pos;

    public Token(String text, int line, int pos) {
        this.text = text;
        this.line = line;
        this.pos = pos;
    }

    public String getText() {
        return text;
    }

    public int getLine() {
        return line;
    }

    public int getPos() {
        return pos;
    }

    @Override
    public String toString() {
        return "Token " + text + " at line " + line + ", position " + pos;
    }
}
