package cm.node.block;

import cm.analyzer.Analyzer;
import cm.node.token.Identifier;

public class BlockProcedureName extends Block {

    private Identifier identifier;

    public BlockProcedureName(Identifier identifier) {
        this.identifier = identifier;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public void apply(Analyzer analyzer) {
        analyzer.procedureNameIn(this);
        // nothing to do
        analyzer.procedureNameOut(this);
    }

    @Override
    public String toString() {
        return this.identifier.getText();
    }
}
