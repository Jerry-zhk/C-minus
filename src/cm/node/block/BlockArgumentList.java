package cm.node.block;

import cm.analyzer.Analyzer;
import cm.node.token.SimpleToken;

import java.util.ArrayList;

public class BlockArgumentList extends Block {

    private ArrayList<SimpleToken> arguments;

    public BlockArgumentList() {
        this.arguments = new ArrayList<>();
    }

    public boolean add(SimpleToken token){
        return this.arguments.add(token);
    }

    public int size(){
        return this.arguments.size();
    }

    public ArrayList<SimpleToken> getArguments() {
        return arguments;
    }

    @Override
    public void apply(Analyzer analyzer) {
        analyzer.argumentListIn(this);
        analyzer.argumentListOut(this);
    }
}
