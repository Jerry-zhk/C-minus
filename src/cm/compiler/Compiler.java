package cm.compiler;

import cm.analyzer.SemanticAnalyzer;
import cm.lexer.Lexer;
import cm.node.block.BlockProgram;
import cm.parser.Parser;
import cm.parser.ParserException;

import java.io.*;

public class Compiler {

    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Compiler requires program path.");
            return;
        }

        File file = new File(args[0]);
        try (InputStream in = new FileInputStream(file)) {
            Reader reader = new InputStreamReader(in);
            // buffer for efficiency
            Reader buffer = new BufferedReader(reader);
            PushbackReader pushbackReader = new PushbackReader(buffer);
            Lexer lexer = new Lexer(pushbackReader);
            Parser parser = new Parser(lexer);
            BlockProgram program = parser.parse();
            program.apply(new SemanticAnalyzer());
        } catch (ParserException e) {
            System.out.println("Program syntax error: " + e.getMessage());
        }

    }

}
