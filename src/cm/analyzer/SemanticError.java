package cm.analyzer;

public class SemanticError {

    private String message;

    public SemanticError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
