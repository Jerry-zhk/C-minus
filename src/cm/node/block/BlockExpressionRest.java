package cm.node.block;

import cm.analyzer.Analyzer;
import cm.node.base.ArithmeticOperator;
import cm.node.token.SimpleToken;

public class BlockExpressionRest extends Block{

    private ArithmeticOperator operator;
    private SimpleToken value;

    public BlockExpressionRest(ArithmeticOperator operator, SimpleToken value) {
        this.operator = operator;
        this.value = value;
    }

    public ArithmeticOperator getOperator() {
        return operator;
    }

    public SimpleToken getValue() {
        return value;
    }

    @Override
    public void apply(Analyzer analyzer) {
        analyzer.expressionRestIn(this);

        analyzer.expressionRestOut(this);
    }

    @Override
    public String toString() {
        return operator + " " + value;
    }
}
