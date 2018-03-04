package cm.node.block;

import cm.analyzer.Analyzer;
import cm.node.token.Identifier;

public class BlockCall extends BlockStatement {

    private Identifier procedureName;
    private BlockArgumentList arguments;

    public BlockCall(Identifier procedureName) {
        this.procedureName = procedureName;
    }

    public BlockCall(Identifier procedureName, BlockArgumentList arguments) {
        this.procedureName = procedureName;
        this.arguments = arguments;
    }

    public Identifier getProcedureName() {
        return procedureName;
    }

    public BlockArgumentList getArguments() {
        return arguments;
    }

    @Override
    public void apply(Analyzer analyzer) {
        analyzer.callIn(this);
        if(arguments != null)
            arguments.apply(analyzer);
        analyzer.callOut(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Call ").append(procedureName.getText());
        if(arguments != null)
            sb.append("[").append(arguments).append("];");
        sb.append("\n");
        return sb.toString();
    }

    public int argumentsCount(){
        return (arguments == null) ? 0 : arguments.size();
    }
}
