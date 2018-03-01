package cm.node.token;

public class BlockProcedure extends Block {

    private Identifier procedureName;

    private BlockVariableList parameters;

    private BlockVariableList declaredVariables;

    private BlockStatementList statements;

    public BlockProcedure(Identifier procedureName, BlockStatementList statements) {
        this.procedureName = procedureName;
        this.statements = statements;
    }

    public BlockProcedure(Identifier procedureName, BlockVariableList parameters, BlockVariableList declaredVariables, BlockStatementList statements) {
        this.procedureName = procedureName;
        this.parameters = parameters;
        this.declaredVariables = declaredVariables;
        this.statements = statements;
    }

    public void setParameters(BlockVariableList parameters) {
        this.parameters = parameters;
    }

    public void setDeclaredVariables(BlockVariableList declaredVariables) {
        this.declaredVariables = declaredVariables;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Procedure ").append(procedureName.getText()).append(":\n");
        if(parameters != null)
            sb.append(" Parameters: ").append(parameters.toString()).append("\n");
        if(declaredVariables != null)
            sb.append("Local Variables: ").append(declaredVariables.toString()).append("\n");

        sb.append("{\n").append(statements.toString()).append("}\n");
        return sb.toString();
    }
}
