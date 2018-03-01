package cm.parser;

import cm.lexer.Lexer;
import cm.node.token.*;

import java.io.*;

public class Parser {

    private Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public void setLexer(Lexer lexer) {
        this.lexer = lexer;
    }

    public BlockProgram parse() throws IOException, ParserException {
        return parseProgram();
    }


    private BlockProgram parseProgram() throws IOException, ParserException {
        return new BlockProgram(parseProcedureList());
    }

    private BlockProcedureList parseProcedureList() throws IOException, ParserException {
        BlockProcedureList list = new BlockProcedureList();
        BlockProcedure procedure;
        while ((procedure = parseProcedure()) != null)
            list.add(procedure);
        if (list.size() == 0)
            throw new ParserException("No procedure found!");
        return list;
    }

    private BlockProcedure parseProcedure() throws IOException, ParserException {
        Token token = lexer.peek();

        Identifier procedureName;
        BlockVariableList parameters = null;
        BlockVariableList declaredVariables;
        BlockStatementList statements;

        if (token instanceof KeywordProcedure) {
            lexer.next();
            token = lexer.peek();
            if (token instanceof Identifier) {
                lexer.next();
                procedureName = (Identifier) token;
            } else {
                throw new ParserException("Unexpected token: " + token + ". Expecting identifier"); // expecting identifier
            }

            token = lexer.peek();
            if (token instanceof OperatorBracketLeft) {
                lexer.next();

                parameters = parseVariableList(true);
                if (parameters == null || parameters.size() == 0)
                    throw new ParserException("Expecting parameter list."); // expecting parameters

                token = lexer.peek();
                if (token instanceof OperatorBracketRight) {
                    lexer.next();
                } else {
                    throw new ParserException("Unexpected token: " + token + ". Expecting ]"); // expecting ]
                }
            }

            declaredVariables = parseDeclaration();

            token = lexer.peek();
            if (token instanceof KeywordStart) {
                lexer.next();
            } else {
                throw new ParserException("Unexpected token: " + token + ". Expecting keyword Start"); // expecting keyword Start
            }

            statements = parseStatementList();

            token = lexer.peek();
            if (token instanceof KeywordEnd) {
                lexer.next();
            } else {
                throw new ParserException("Unexpected token: " + token + ". Expecting keyword End"); // expecting keyword End
            }

            BlockProcedure procedure = new BlockProcedure(procedureName, statements);
            if (parameters != null)
                procedure.setParameters(parameters);
            if (declaredVariables != null)
                procedure.setDeclaredVariables(declaredVariables);
            return procedure;

        } else {
            if (token instanceof EOF)
                return null;
            else
                throw new ParserException("Unexpected token: " + token + ". Expecting keyword Procedure"); // expecting keyword Procedure
        }


    }

    private BlockVariableList parseDeclaration() throws IOException, ParserException {
        BlockVariableList declaredVariables = parseVariableList(false);
        if (declaredVariables == null) return null;
        Token token = lexer.peek();
        if (token instanceof OperatorSemicolon) {
            lexer.next();
        } else {
            throw new ParserException("Unexpected token: " + token + ". Expecting ;"); // expecting ;
        }
        return declaredVariables;
    }

    /** parameters / declarations
     *  boolean must: control whether to (true)throw exception or (false)return null
     */
    private BlockVariableList parseVariableList(boolean must) throws IOException, ParserException {
        BlockVariableList variableList = new BlockVariableList();
        Token token = lexer.peek();
        if (token instanceof KeywordVar) {
            lexer.next();
        } else {
            if (must)
                throw new ParserException("Unexpected token: " + token + ". Expecting keyword Var"); // expecting keyword Var
            else
                return null;
        }

        token = lexer.peek();
        if (token instanceof Identifier) {
            lexer.next();
            variableList.add(new BlockVariable((Identifier) token));
        } else {
            if (must)
                throw new ParserException("Unexpected token: " + token + ". Expecting identifier"); // expecting Identifier
            else
                return null;
        }

        token = lexer.peek();
        while (token instanceof OperatorComma) {
            lexer.next();
            token = lexer.peek();
            if (token instanceof Identifier) {
                lexer.next();
                variableList.add(new BlockVariable((Identifier) token));
            } else {
                if (must)
                    throw new ParserException("Unexpected token: " + token + ". Expecting identifier"); // expecting Identifier
                else
                    return null;
            }
            token = lexer.peek();
        }

        return variableList;
    }

    private BlockStatementList parseStatementList() throws IOException, ParserException {
        BlockStatementList statementList = new BlockStatementList();
        BlockStatement statement;
        while ((statement = parseStatement()) != null)
            statementList.add(statement);
        return statementList;
    }

    private BlockStatement parseStatement() throws IOException, ParserException {
        BlockStatement statement = null;
        Token token;
        token = lexer.peek();
        if (token instanceof Identifier) {
            lexer.next();
            Identifier variableBeingAssigned = (Identifier) token;

            token = lexer.peek();
            if (token instanceof OperatorEqual) {
                lexer.next();
            } else {
                throw new ParserException("Unexpected token: " + token + ". Expecting ="); // expecting =
            }

            BlockExpression expression = null;
            Assignable first, following;
            ArithmeticOperator op;
            token = lexer.peek();
            if (token instanceof Assignable) {
                lexer.next();
                first = (Assignable) token;
            } else {
                throw new ParserException("Unexpected token: " + token + ". Expecting variable"); // expecting variable
            }

            token = lexer.peek();
            if (token instanceof ArithmeticOperator) {
                while (token instanceof ArithmeticOperator) {
                    lexer.next();
                    op = (ArithmeticOperator) token;

                    token = lexer.peek();
                    if (token instanceof Assignable) {
                        lexer.next();
                        following = (Assignable) token;
                        if (expression == null) {
                            expression = new BlockExpression(first, op, following);
                        } else {
                            expression.addOperation(op, following);
                        }
                    } else {
                        throw new ParserException("Unexpected token: " + token + ". Expecting integer literal or variable"); // expecting integer literal or variable
                    }
                    token = lexer.peek();
                }
            }

            statement = new BlockAssignment(variableBeingAssigned, (expression == null) ? first : expression);

        } else if (token instanceof KeywordCall) {
            lexer.next();
            token = lexer.peek();
            Identifier procedureName;
            BlockVariableList arguments = null;
            if (token instanceof Identifier) {
                lexer.next();
                procedureName = (Identifier) token;
            } else {
                throw new ParserException("Unexpected token: " + token + ". Expecting variable"); // expecting variable
            }

            token = lexer.peek();
            if (token instanceof OperatorBracketLeft) {
                lexer.next();

                arguments = parseArgumentList();
                if (arguments == null || arguments.size() == 0)
                    throw new ParserException("Expecting argument list."); // expecting parameters

                token = lexer.peek();
                if (token instanceof OperatorBracketRight) {
                    lexer.next();
                } else {
                    throw new ParserException("Unexpected token: " + token + ". Expecting ]"); // expecting ]
                }
            }

            if (arguments == null) {
                statement = new BlockCall(procedureName);
            } else {
                statement = new BlockCall(procedureName, arguments);
            }
        } else if (token instanceof KeywordPrint) {
            lexer.next();
            token = lexer.peek();
            if (token instanceof SingleValued) {
                lexer.next();
                statement = new BlockPrint((SingleValued) token);
            } else {
                throw new ParserException("Unexpected token: " + token + ". Expecting integer literal or variable"); // expecting integer literal or variable
            }
        } else if (token instanceof KeywordExitZero) {
            lexer.next();
            token = lexer.peek();
            if (token instanceof SingleValued) {
                lexer.next();
                statement = new BlockExitZero((SingleValued) token);
            } else {
                throw new ParserException("Unexpected token: " + token + ". Expecting integer literal or variable"); // expecting integer literal or variable
            }
        } else {
            return null;
        }

        token = lexer.peek();
        if (token instanceof OperatorSemicolon) {
            lexer.next();
        } else {
            throw new ParserException("Unexpected token: " + token + ". Expecting ;"); // expecting ;
        }

        return statement;
    }

    private BlockVariableList parseArgumentList() throws IOException, ParserException {
        BlockVariableList variableList = new BlockVariableList();
        Token token = lexer.peek();
        if (token instanceof Identifier) {
            lexer.next();
            variableList.add(new BlockVariable((Identifier) token));
        } else {
            throw new ParserException("Unexpected token: " + token + ". Expecting identifier"); // expecting Identifier
        }

        token = lexer.peek();
        while (token instanceof OperatorComma) {
            lexer.next();
            token = lexer.peek();
            if (token instanceof Identifier) {
                lexer.next();
                variableList.add(new BlockVariable((Identifier) token));
            } else {
                throw new ParserException("Unexpected token: " + token + ". Expecting identifier"); // expecting Identifier
            }
            token = lexer.peek();
        }

        return variableList;
    }


    public static void main(String[] args) throws IOException, ParserException {
        File file = new File("program.txt");
        try (InputStream in = new FileInputStream(file)) {
            Reader reader = new InputStreamReader(in);
            // buffer for efficiency
            Reader buffer = new BufferedReader(reader);
            PushbackReader pushbackReader = new PushbackReader(buffer);
            Lexer lexer = new Lexer(pushbackReader);
            Parser parser = new Parser(lexer);
            System.out.println(parser.parse());
        } catch (ParserException e) {
            System.out.println("Program syntax error: " + e.getMessage());
        }
    }

}
