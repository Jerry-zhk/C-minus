package cm.node.block;

import cm.analyzer.Analyzer;
import cm.node.token.SimpleToken;

public class BlockExitZero extends BlockStatement {

    private SimpleToken value;

    public BlockExitZero(SimpleToken value) {
        this.value = value;
    }

    public SimpleToken getValue() {
        return value;
    }

    @Override
    public void apply(Analyzer analyzer) {
        analyzer.exitZeroIn(this);
        // nothing to do
        analyzer.exitZeroOut(this);
    }

    @Override
    public String toString() {
        return "ExitZero " + this.value + ";";
    }
}
