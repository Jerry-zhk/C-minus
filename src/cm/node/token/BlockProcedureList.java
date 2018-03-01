package cm.node.token;

import java.util.ArrayList;

public class BlockProcedureList extends Block {

    private ArrayList<BlockProcedure> procedures;

    public BlockProcedureList() {
        this.procedures = new ArrayList<>();
    }

    public BlockProcedureList(ArrayList<BlockProcedure> procedures) {
        this.procedures = procedures;
    }

    public boolean add(BlockProcedure procedure){
        return this.procedures.add(procedure);
    }

    public int size(){
        return this.procedures.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (BlockProcedure p: procedures) {
            sb.append(p.toString());
        }
        return sb.toString();
    }
}
