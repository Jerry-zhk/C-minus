package cm.node.token;

import java.util.ArrayList;

public class BlockProgram extends Block {

    BlockProcedureList procedures;

    public BlockProgram(BlockProcedureList procedures) {
        this.procedures = procedures;
    }

    @Override
    public String toString() {
        return procedures.toString();
    }
}
