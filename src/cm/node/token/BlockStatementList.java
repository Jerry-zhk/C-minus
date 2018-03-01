package cm.node.token;

import java.util.ArrayList;

public class BlockStatementList extends Block {

    private ArrayList<BlockStatement> statements;

    public BlockStatementList() {
        this.statements = new ArrayList<>();
    }

    public BlockStatementList(ArrayList<BlockStatement> statements) {
        this.statements = statements;
    }

    public boolean add(BlockStatement statement){
        return this.statements.add(statement);
    }

    public int size(){
        return this.statements.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (BlockStatement s: statements){
            sb.append(s).append("\n");
        }
        return sb.toString();
    }
}
