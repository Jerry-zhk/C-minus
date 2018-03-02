package cm.node.block;

import cm.analyzer.Analyzer;
import cm.node.token.Identifier;

public class BlockProcedure extends Block {

    private BlockProcedureName procedureName;

    private BlockParameterList parameters;

    private BlockDeclaration declaration;

    private BlockStatementList statements;

    public BlockProcedure(BlockProcedureName procedureName, BlockStatementList statements) {
        this.procedureName = procedureName;
        this.statements = statements;
    }

    public BlockProcedure(BlockProcedureName procedureName, BlockParameterList parameters, BlockDeclaration declaration, BlockStatementList statements) {
        this.procedureName = procedureName;
        this.parameters = parameters;
        this.declaration = declaration;
        this.statements = statements;
    }

    public void setParameters(BlockParameterList parameters) {
        this.parameters = parameters;
    }

    public void setDeclaredVariables(BlockDeclaration declaration) {
        this.declaration = declaration;
    }

    @Override
    public void apply(Analyzer analyzer) {
        analyzer.procedureIn(this);

        procedureName.apply(analyzer);

        if(parameters  != null)
            parameters.apply(analyzer);

        if(declaration != null)
            declaration.apply(analyzer);

        statements.apply(analyzer);

        analyzer.procedureOut(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Procedure ").append(procedureName).append(":\n");
        if(parameters != null)
            sb.append(" Parameters: ").append(parameters.toString()).append("\n");
        if(declaration != null)
            sb.append("Local Variables: ").append(declaration.toString()).append("\n");

        sb.append("{\n").append(statements.toString()).append("}\n");
        return sb.toString();
    }
}
