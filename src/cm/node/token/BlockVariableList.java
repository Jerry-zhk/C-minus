package cm.node.token;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class BlockVariableList extends Block {

    private ArrayList<BlockVariable> variables;

    public BlockVariableList() {
        this.variables = new ArrayList<>();
    }

    public BlockVariableList(ArrayList<BlockVariable> variables) {
        this.variables = variables;
    }

    public boolean add(BlockVariable parameter){
        return this.variables.add(parameter);
    }

    public int size(){
        return this.variables.size();
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
