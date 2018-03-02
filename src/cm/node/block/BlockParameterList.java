package cm.node.block;

import cm.analyzer.Analyzer;

public class BlockParameterList extends BlockVariableList {

    public BlockParameterList() {
        super();
    }

    @Override
    public void apply(Analyzer analyzer) {
        analyzer.parameterListIn(this);
        for (BlockVariable v: super.variables){
            v.apply(analyzer);
        }
        analyzer.parameterListOut(this);
    }
}
