package cm.node.block;

import cm.analyzer.Analyzer;

import java.util.ArrayList;

public class BlockDeclaration extends BlockVariableList {

    public BlockDeclaration() {
        super();
    }

    @Override
    public void apply(Analyzer analyzer) {
        analyzer.declarationIn(this);
        for (BlockVariable v: super.variables){
            v.apply(analyzer);
        }
        analyzer.declarationOut(this);
    }

}
