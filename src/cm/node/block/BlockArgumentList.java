package cm.node.block;

import cm.analyzer.Analyzer;

public class BlockArgumentList extends BlockVariableList {

    public BlockArgumentList() {
        super();
    }

    @Override
    public void apply(Analyzer analyzer) {
        analyzer.argumentListIn(this);
        for (BlockVariable v: super.variables){
            v.apply(analyzer);
        }
        analyzer.argumentListOut(this);
    }
}
