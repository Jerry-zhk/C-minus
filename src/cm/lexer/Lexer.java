package cm.lexer;

import cm.node.token.*;

import java.io.*;

public class Lexer {

    private Token token;
    private PushbackReader reader;
    private boolean eof;
    private boolean hasNextToken;
    private int line;
    private int pos;

    public Lexer(PushbackReader reader) {
        this.reader = reader;
        this.eof = false;
        this.hasNextToken = true;
        this.line = 1;
        this.pos = 1;
    }

    private int getChar() throws IOException {
        if (this.eof) return -1;

        int result = this.reader.read();

        if (result == -1) this.eof = true;

        return result;
    }

    private void unread(int c) throws IOException {
        // unread if c is not space
        if (LexicalHelper.isSpace(c))
            moveCursor(c);
        else if(c != -1)
            this.reader.unread(c);

    }

    public Token peek() throws IOException {
        if (this.token == null)
            this.token = getToken();
        return this.token;
    }

    public Token next() throws IOException {
        if (this.token == null)
            this.token = getToken();
        Token result = this.token;
        this.token = null;
        return result;
    }

    private Token getToken() throws IOException {


        LexicalHelper.State textState = LexicalHelper.State.Initial;
        StringBuilder text = new StringBuilder();

        int c = getChar();
        while (LexicalHelper.isSpace(c)) {
            moveCursor(c);
            c = getChar();
        }
        unread(c);

        int start_line = this.line;
        int start_pos = this.pos;

        while (true) {
            c = getChar();
            if (c == -1) {
                hasNextToken = false;
            }

            switch (textState) {
                case Digit:
                    if (!LexicalHelper.isDigit(c)) {
                        unread(c);
                        return getClassifiedToken(text.toString(), start_line, start_pos, textState);
                    }
                    break;
                case Letter:
                    if (!LexicalHelper.isLetter(c)) {
                        unread(c);
                        return getClassifiedToken(text.toString(), start_line, start_pos, textState);
                    }
                    break;
                    // case Symbol: handled in case Initial
                case Initial:
                    if (LexicalHelper.isDigit(c)) textState = LexicalHelper.State.Digit;
                    else if (LexicalHelper.isLetter(c)) textState = LexicalHelper.State.Letter;
                    else if (LexicalHelper.isValidSymbol(c)) {
                        moveCursor(c);
                        return getClassifiedToken(String.valueOf((char) c), start_line, start_pos, LexicalHelper.State.Symbol);
                    }
                    break;

            }

            if (hasNextToken)
                moveCursor(c);
            else
                return new EOF(this.line, this.pos);




            if (!LexicalHelper.isSpace(c))
                text.append((char) c);


        }
    }

    private void moveCursor(int c) {
        switch (c) {
            case '\t': // Horizontal tab, ascii.9
                this.pos += 8;
                break;
            case '\n':
                this.line++;
                this.pos = 1;
                break;
            default:
                this.pos++;
                break;
        }
    }

    private Token getClassifiedToken(String text, int line, int pos, LexicalHelper.State state) {

        switch (state) {
            case Symbol:
                switch (text) {
                    case "=":
                        return new OperatorEqual(line, pos);
                    case "+":
                        return new OperatorAddition(line, pos);
                    case "-":
                        return new OperatorSubtraction(line, pos);
                    case ",":
                        return new OperatorComma(line, pos);
                    case ";":
                        return new OperatorSemicolon(line, pos);
                    case "[":
                        return new OperatorBracketLeft(line, pos);
                    case "]":
                        return new OperatorBracketRight(line, pos);
                    default:
                        return null;
                }
            case Digit:
                return new IntegerLiteral(text, line, pos);
            case Letter:
                switch (text) {
                    case "Procedure":
                        return new KeywordProcedure(line, pos);
                    case "Var":
                        return new KeywordVar(line, pos);
                    case "Start":
                        return new KeywordStart(line, pos);
                    case "End":
                        return new KeywordEnd(line, pos);
                    case "Call":
                        return new KeywordCall(line, pos);
                    case "Print":
                        return new KeywordPrint(line, pos);
                    case "ExitZero":
                        return new KeywordExitZero(line, pos);
                    default:
                        return new Identifier(text, line, pos);
                }
            default:
                return null;
        }

    }

    public static void main(String[] args) throws IOException {
        File file = new File("program.txt");
        try (InputStream in = new FileInputStream(file)) {
            Reader reader = new InputStreamReader(in);
            // buffer for efficiency
            Reader buffer = new BufferedReader(reader);
            PushbackReader pushbackReader = new PushbackReader(buffer);
            Lexer lexer = new Lexer(pushbackReader);
            Token token = lexer.getToken();
            while (!(token instanceof EOF)) {
                System.out.println(token);
                token = lexer.getToken();
            }
        }
    }

}
