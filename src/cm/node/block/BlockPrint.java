package cm.node.block;

import cm.analyzer.Analyzer;
import cm.node.token.SimpleToken;

public class BlockPrint extends BlockStatement {

    private SimpleToken value;

    public BlockPrint(SimpleToken value) {
        this.value = value;
    }

    public SimpleToken getValue() {
        return value;
    }

    @Override
    public void apply(Analyzer analyzer) {
        analyzer.printIn(this);
        // nothing to do
        analyzer.printOut(this);

    }

    @Override
    public String toString() {
        return "Print " + value.getText() + ";";
    }
}
