package cm.node.token;

public class BlockExitZero extends BlockStatement {

    private SingleValued value;

    public BlockExitZero(SingleValued variable) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ExitZero " + value + ";";
    }
}
