package cm.node.token;

public class BlockExpression extends Block implements Assignable {

    private Assignable lhs;
    private ArithmeticOperator operator;
    private Assignable rhs;

    public BlockExpression(Assignable lhs, ArithmeticOperator operator, Assignable rhs) {
        this.lhs = lhs;
        this.operator = operator;
        this.rhs = rhs;
    }

    public void addOperation(ArithmeticOperator operator, Assignable value){
        this.lhs = new BlockExpression(this.lhs, this.operator, this.rhs);
        this.operator = operator;
        this.rhs = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(lhs).append(" ").append(operator).append(" ").append(rhs);
        return sb.toString();
    }
}
