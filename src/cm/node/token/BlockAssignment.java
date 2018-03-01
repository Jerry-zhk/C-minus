package cm.node.token;

public class BlockAssignment extends BlockStatement {

    private Identifier identifierBeingAssigned;
    private Assignable assigningValue;

    public BlockAssignment(Identifier identifierBeingAssigned, Assignable assigningValue) {
        this.identifierBeingAssigned = identifierBeingAssigned;
        this.assigningValue = assigningValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(identifierBeingAssigned.getText()).append(" = ").append(assigningValue).append(";");
        return sb.toString();
    }
}
