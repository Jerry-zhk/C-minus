package cm.node.block;

import cm.analyzer.Analyzer;
import cm.node.token.Identifier;

public class BlockVariable extends Block {

    private Identifier identifier;

    public BlockVariable(Identifier identifier) {
        this.identifier = identifier;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public void apply(Analyzer analyzer) {
        analyzer.variableIn(this);
        // nothing to do
        analyzer.variableOut(this);
    }

    @Override
    public String toString() {
        return identifier.getText();
    }
}
