package cm.lexer;

public class LexicalHelper {

    public enum State {Initial, Digit, Letter, Symbol, EOF}

    public static boolean isDigit(int c){
        return c >= '0' && c <= '9';
    }

    public static boolean isLetter(int c){
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public static boolean isValidSymbol(int c){
        return c == '=' || c == '+' || c == '-' || c == ',' || c == ';' || c == '[' || c == ']';
    }

    public static boolean isSpace(int c){
        return c == ' ' || c == '\n' || c == '\f' || c == '\r' || c == '\t';
    }
}
