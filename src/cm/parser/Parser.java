package cm.parser;

import cm.lexer.Lexer;
import cm.node.base.ArithmeticOperator;
import cm.node.base.Assignable;
import cm.node.token.SimpleToken;
import cm.node.block.*;
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

        BlockProcedureName procedureName;
        BlockParameterList parameters = null;
        BlockDeclaration declaration;
        BlockStatementList statements;

        if (token instanceof KeywordProcedure) {
            lexer.next();
            token = lexer.peek();
            if (token instanceof Identifier) {
                lexer.next();
                procedureName = new BlockProcedureName((Identifier) token);
            } else {
                throw new ParserException("Unexpected token: " + token + ". Expecting identifier"); // expecting identifier
            }

            token = lexer.peek();
            if (token instanceof OperatorBracketLeft) {
                lexer.next();

                parameters = parseParameterList();
                if (parameters == null || parameters.size() == 0)
                    throw new ParserException("Expecting parameter list."); // expecting parameters

                token = lexer.peek();
                if (token instanceof OperatorBracketRight) {
                    lexer.next();
                } else {
                    throw new ParserException("Unexpected token: " + token + ". Expecting ]"); // expecting ]
                }
            }

            declaration = parseDeclaration();

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
            if (declaration != null)
                procedure.setDeclaredVariables(declaration);
            return procedure;

        } else {
            if (token instanceof EOF)
                return null;
            else
                throw new ParserException("Unexpected token: " + token + ". Expecting keyword Procedure"); // expecting keyword Procedure
        }

    }

    private BlockDeclaration parseDeclaration() throws IOException, ParserException {
        BlockDeclaration declaration = new BlockDeclaration();

        Token token = lexer.peek();
        if (token instanceof KeywordVar) {
            lexer.next();
        }

        token = lexer.peek();
        if (token instanceof Identifier) {
            lexer.next();
            declaration.add(new BlockVariable((Identifier) token));
        } else {
            throw new ParserException("Unexpected token: " + token + ". Expecting identifier"); // expecting Identifier
        }

        token = lexer.peek();
        while (token instanceof OperatorComma) {
            lexer.next();
            token = lexer.peek();
            if (token instanceof Identifier) {
                lexer.next();
                declaration.add(new BlockVariable((Identifier) token));
            } else {
                throw new ParserException("Unexpected token: " + token + ". Expecting identifier"); // expecting Identifier
            }
            token = lexer.peek();
        }

        token = lexer.peek();
        if (token instanceof OperatorSemicolon) {
            lexer.next();
        } else {
            throw new ParserException("Unexpected token: " + token + ". Expecting ;"); // expecting ;
        }

        if(declaration.size() == 0) return null;
        return declaration;
    }


    private BlockParameterList parseParameterList() throws IOException, ParserException {
        BlockParameterList parameterList = new BlockParameterList();
        Token token = lexer.peek();
        if (token instanceof KeywordVar) {
            lexer.next();
        } else {
            throw new ParserException("Unexpected token: " + token + ". Expecting keyword Var"); // expecting keyword Var
        }

        token = lexer.peek();
        if (token instanceof Identifier) {
            lexer.next();
            parameterList.add(new BlockVariable((Identifier) token));
        } else {
                throw new ParserException("Unexpected token: " + token + ". Expecting identifier"); // expecting Identifier
        }

        token = lexer.peek();
        while (token instanceof OperatorComma) {
            lexer.next();
            token = lexer.peek();
            if (token instanceof Identifier) {
                lexer.next();
                parameterList.add(new BlockVariable((Identifier) token));
            } else {
                throw new ParserException("Unexpected token: " + token + ". Expecting identifier"); // expecting Identifier
            }
            token = lexer.peek();
        }

        return parameterList;
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
            SimpleToken first;
            ArithmeticOperator op;
            token = lexer.peek();
            if (token instanceof SimpleToken) {
                lexer.next();
                first = (SimpleToken) token;
            } else {
                throw new ParserException("Unexpected token: " + token + ". Expecting variable"); // expecting variable
            }

            expression = new BlockExpression(first);
            token = lexer.peek();
            if (token instanceof ArithmeticOperator) {
                while (token instanceof ArithmeticOperator) {
                    lexer.next();
                    op = (ArithmeticOperator) token;

                    token = lexer.peek();
                    if (token instanceof Assignable) {
                        lexer.next();
                        expression.addOperation(op, (SimpleToken) token);
                    } else {
                        throw new ParserException("Unexpected token: " + token + ". Expecting integer literal or variable"); // expecting integer literal or variable
                    }
                    token = lexer.peek();
                }
            }

            statement = new BlockAssignment(variableBeingAssigned, expression);

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
            if (token instanceof SimpleToken) {
                lexer.next();
                statement = new BlockPrint((SimpleToken) token);
            } else {
                throw new ParserException("Unexpected token: " + token + ". Expecting integer literal or variable"); // expecting integer literal or variable
            }
        } else if (token instanceof KeywordExitZero) {
            lexer.next();
            token = lexer.peek();
            if (token instanceof SimpleToken) {
                lexer.next();
                statement = new BlockExitZero((SimpleToken) token);
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

    private BlockArgumentList parseArgumentList() throws IOException, ParserException {
        BlockArgumentList argumentList = new BlockArgumentList();
        Token token = lexer.peek();
        if (token instanceof Identifier) {
            lexer.next();
            argumentList.add(new BlockVariable((Identifier) token));
        } else {
            throw new ParserException("Unexpected token: " + token + ". Expecting identifier"); // expecting Identifier
        }

        token = lexer.peek();
        while (token instanceof OperatorComma) {
            lexer.next();
            token = lexer.peek();
            if (token instanceof Identifier) {
                lexer.next();
                argumentList.add(new BlockVariable((Identifier) token));
            } else {
                throw new ParserException("Unexpected token: " + token + ". Expecting identifier"); // expecting Identifier
            }
            token = lexer.peek();
        }

        return argumentList;
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
