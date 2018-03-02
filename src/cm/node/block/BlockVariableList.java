package cm.node.block;

import cm.analyzer.Analyzer;

import java.util.ArrayList;

public abstract class BlockVariableList extends Block {

    protected ArrayList<BlockVariable> variables;

    public BlockVariableList() {
        this.variables = new ArrayList<>();
    }

    public boolean add(BlockVariable parameter){
        return this.variables.add(parameter);
    }

    public int size(){
        return this.variables.size();
    }

    public ArrayList<BlockVariable> getVariables() {
        return variables;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (BlockVariable v: variables) {
            sb.append(v.toString()).append(",");
        }
        if (sb.length() > 0)
            sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
