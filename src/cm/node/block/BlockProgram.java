package cm.node.block;

import cm.analyzer.Analyzer;

public class BlockProgram extends Block {

    BlockProcedureList procedures;

    public BlockProgram(BlockProcedureList procedures) {
        this.procedures = procedures;
    }

    @Override
    public void apply(Analyzer analyzer) {
        analyzer.programIn(this);
        procedures.apply(analyzer);
        analyzer.programOut(this);
    }

    @Override
    public String toString() {
        return procedures.toString();
    }
}
