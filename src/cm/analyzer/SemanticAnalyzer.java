package cm.analyzer;

import cm.lexer.Lexer;
import cm.node.block.*;
import cm.node.token.Identifier;
import cm.node.token.SimpleToken;
import cm.parser.Parser;
import cm.parser.ParserException;

import java.io.*;
import java.util.ArrayList;

public class SemanticAnalyzer extends Analyzer {


    private ArrayList<BlockProcedure> procedures;
    private ArrayList<BlockCall> calls;
    private ArrayList<Identifier> localVariables;
    private ArrayList<Identifier> localVariablesUsed;

    private ArrayList<SemanticError> errors;

    private boolean isMain;


    public SemanticAnalyzer() {
        procedures = new ArrayList<>();
        calls = new ArrayList<>();
        localVariables = new ArrayList<>();
        localVariablesUsed = new ArrayList<>();
        errors = new ArrayList<>();
        isMain = false;
    }

    @Override
    public void programIn(BlockProgram program) {
        super.programIn(program);
    }

    @Override
    public void programOut(BlockProgram program) {
        super.programOut(program);

        boolean hasMain = false;
        for (BlockProcedure p : procedures) {
            if (p.getName().getText().equals("Main"))
                hasMain = true;
        }
        if (!hasMain)
            addError("Missing < Main > procedure.");

        // check undefined procedure
        for (BlockCall c : calls) {
            boolean defined = false;
            for (BlockProcedure p : procedures) {
                if (p.getName().getText().equals(c.getProcedureName().getText())) {
                    defined = true;
                    if (p.expectingParametersCount() != c.argumentsCount()) {
                        addError("Incorrect arguments for procedure < " + c.getProcedureName().getText() + " > , expecting " + p.expectingParametersCount() + " arguments but " + c.argumentsCount() + " given.");
                    }
                    break;
                }
            }
            if (!defined)
                addError("Undefined procedure: " + c.getProcedureName().getText() + ".");

        }

        // final
        if (errors == null || errors.size() == 0) {
            System.out.println("Ok");
        } else {
            for (SemanticError err : errors) {
                System.out.println(err);
            }
        }

    }

    @Override
    public void procedureIn(BlockProcedure procedure) {
        super.procedureIn(procedure);

        if (procedures.contains(procedure))
            addError("Redefined procedure " + procedure.getName().getText());
        procedures.add(procedure);

        localVariables = new ArrayList<>();
        localVariablesUsed = new ArrayList<>();
        isMain = (procedure.getName().getText().equals("Main"));
    }

    @Override
    public void procedureOut(BlockProcedure procedure) {
        super.procedureOut(procedure);
        // check unused
        localVariables.removeAll(localVariablesUsed);
        for (Identifier var : localVariables) {
            addError("Unused variable " + var + ".");
        }
        localVariables = null;
        localVariablesUsed = null;

    }


    @Override
    public void parameterListIn(BlockVariableList variableList) {
        super.parameterListIn(variableList);
        if (isMain)
            addError("Procedure < Main > cannot have parameters.");
        else {
            for (BlockVariable v : variableList.getVariables()) {
                addLocalVariable(v.getIdentifier());
            }
        }
    }

    @Override
    public void parameterListOut(BlockVariableList variableList) {
        super.parameterListOut(variableList);
    }

    @Override
    public void declarationIn(BlockVariableList variableList) {
        super.declarationIn(variableList);
        for (BlockVariable v : variableList.getVariables()) {
            addLocalVariable(v.getIdentifier());
        }

    }

    @Override
    public void declarationOut(BlockVariableList variableList) {
        super.declarationOut(variableList);
    }

    @Override
    public void variableIn(BlockVariable variable) {
        super.variableIn(variable);
    }

    @Override
    public void statementListIn(BlockStatementList statementList) {
        super.statementListIn(statementList);

    }

    @Override
    public void statementListOut(BlockStatementList statementList) {
        super.statementListOut(statementList);
    }

    @Override
    public void assignmentIn(BlockAssignment assignment) {
        super.assignmentIn(assignment);
        Identifier id = assignment.getIdentifier();
        useLocalVariable(id);
    }

    @Override
    public void assignmentOut(BlockAssignment assignment) {
        super.assignmentOut(assignment);
    }

    @Override
    public void expressionIn(BlockExpression expression) {
        super.expressionIn(expression);
        SimpleToken first = expression.getFirstValue();
        if (first instanceof Identifier)
            useLocalVariable((Identifier) first);

    }

    @Override
    public void expressionOut(BlockExpression expression) {
        super.expressionOut(expression);
    }

    @Override
    public void expressionRestIn(BlockExpressionRest expressionRest) {
        super.expressionRestIn(expressionRest);
        SimpleToken value = expressionRest.getValue();
        if (value instanceof Identifier)
            useLocalVariable((Identifier) value);
    }

    @Override
    public void expressionRestOut(BlockExpressionRest expressionRest) {
        super.expressionRestOut(expressionRest);
    }

    @Override
    public void printIn(BlockPrint print) {
        super.printIn(print);
        SimpleToken token = print.getValue();
        if (token instanceof Identifier)
            useLocalVariable((Identifier) token);
    }

    @Override
    public void callIn(BlockCall call) {
        super.callIn(call);
        if (call.getProcedureName().getText().equals("Main"))
            addError("Procedure < Main > cannot be called.");
        else
            calls.add(call);
    }

    @Override
    public void argumentListIn(BlockArgumentList argumentList) {
        super.argumentListIn(argumentList);
        for (SimpleToken v : argumentList.getArguments()) {
            if(v instanceof Identifier)
                useLocalVariable((Identifier) v);
        }
    }

    @Override
    public void exitZeroIn(BlockExitZero exitZero) {
        super.exitZeroIn(exitZero);
        SimpleToken token = exitZero.getValue();
        if (token instanceof Identifier)
            useLocalVariable((Identifier) token);
    }

    private void addLocalVariable(Identifier variable) {
        if (localVariables.contains(variable))
            addError("Redefine variables: " + variable.getText() + " in parameter list or declaration. Line " + variable.getLine() + ", Position " + variable.getPos() + ".");
        else
            localVariables.add(variable);
    }

    private void useLocalVariable(Identifier variable) {
        reportIfUnDeclared(variable);
        if (!localVariablesUsed.contains(variable))
            localVariablesUsed.add(variable);
    }

    private void reportIfUnDeclared(Identifier variable) {
        if (!localVariables.contains(variable))
            addError("Undeclared variable: " + variable + ".");
    }

    private void addError(String message) {
        errors.add(new SemanticError(message));
    }

    public static void main(String[] args) throws IOException {
        File file = new File("task2/case5.txt");
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
