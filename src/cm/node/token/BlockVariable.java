package cm.node.token;

public class BlockVariable {

    private Identifier identifier;

    public BlockVariable(Identifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return identifier.getText();
    }
}
