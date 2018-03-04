package cm.node.block;

import cm.analyzer.Analyzer;
import cm.node.token.Identifier;

public class BlockProcedure extends Block {

    private Identifier name;

    private BlockParameterList parameters;

    private BlockDeclaration declaration;

    private BlockStatementList statements;

    public BlockProcedure(Identifier name, BlockStatementList statements) {
        this.name = name;
        this.statements = statements;
    }

    public BlockProcedure(Identifier name, BlockParameterList parameters, BlockDeclaration declaration, BlockStatementList statements) {
        this.name = name;
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

    public Identifier getName() {
        return name;
    }

    @Override
    public void apply(Analyzer analyzer) {
        analyzer.procedureIn(this);

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
        sb.append("Procedure ").append(name).append(":\n");
        if(parameters != null)
            sb.append(" Parameters: ").append(parameters.toString()).append("\n");
        if(declaration != null)
            sb.append("Local Variables: ").append(declaration.toString()).append("\n");

        sb.append("{\n").append(statements.toString()).append("}\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {


        boolean same = false;

        if (obj != null && obj instanceof BlockProcedure)
        {
            same = (this.getName().equals(((BlockProcedure) obj).getName()));
        }

        return same;
    }

    public int expectingParametersCount(){
        return (parameters == null) ? 0 : parameters.size();
    }

}
