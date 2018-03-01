package cm.node.token;

public class BlockCall extends BlockStatement {

    private Identifier procedureName;
    private BlockVariableList arguments;

    public BlockCall(Identifier procedureName) {
        this.procedureName = procedureName;
    }

    public BlockCall(Identifier procedureName, BlockVariableList arguments) {
        this.procedureName = procedureName;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Call ").append(procedureName.getText()).append("[").append(arguments).append("];").append("\n");
        return sb.toString();
    }
}
