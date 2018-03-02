package cm.node.block;

import cm.analyzer.Analyzer;
import cm.node.base.ArithmeticOperator;
import cm.node.base.Assignable;
import cm.node.token.SimpleToken;
import cm.node.token.Token;

import java.util.ArrayList;

public class BlockExpression extends Block implements Assignable {

    private SimpleToken firstValue;
    private ArrayList<BlockExpressionRest> followings;

    public BlockExpression(SimpleToken firstValue) {
        this.firstValue = firstValue;
        this.followings = new ArrayList<>();
    }

    public void addOperation(ArithmeticOperator operator, SimpleToken value){
        this.followings.add(new BlockExpressionRest(operator, value));
    }

    public SimpleToken getFirstValue() {
        return firstValue;
    }

    @Override
    public void apply(Analyzer analyzer) {
        analyzer.expressionIn(this);
        for(BlockExpressionRest er: followings){
            er.apply(analyzer);
        }
        analyzer.expressionOut(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(firstValue);
        for(BlockExpressionRest er: followings){
            sb.append(er);
        }
        return sb.toString();
    }
}
