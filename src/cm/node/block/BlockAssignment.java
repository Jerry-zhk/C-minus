package cm.node.block;

import cm.analyzer.Analyzer;
import cm.node.token.Identifier;

public class BlockAssignment extends BlockStatement {

    private Identifier identifier;
    private BlockExpression assigningValue;

    public BlockAssignment(Identifier identifier, BlockExpression assigningValue) {
        this.identifier = identifier;
        this.assigningValue = assigningValue;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public void apply(Analyzer analyzer) {
        analyzer.assignmentIn(this);
        assigningValue.apply(analyzer);
        analyzer.assignmentOut(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(identifier.getText()).append(" = ").append(assigningValue).append(";");
        return sb.toString();
    }
}
