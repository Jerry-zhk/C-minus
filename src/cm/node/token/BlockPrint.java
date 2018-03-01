package cm.node.token;

public class BlockPrint extends BlockStatement {

    private SingleValued singleValued;

    public BlockPrint(SingleValued singleValued) {
        this.singleValued = singleValued;
    }

    @Override
    public String toString() {
        return "Print " + singleValued + ";";
    }
}
