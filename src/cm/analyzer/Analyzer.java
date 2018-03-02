package cm.analyzer;

import cm.node.block.*;

public class Analyzer {

    public Analyzer() {

    }

    public void defaultIn(){

    }

    public void defaultOut(){

    }

    public void programIn(BlockProgram program){
        defaultIn();
    }

    public void programOut(BlockProgram program){
        defaultOut();
    }

    public void procedureListIn(BlockProcedureList procedureList){
        defaultIn();
    }

    public void procedureListOut(BlockProcedureList procedureList){
        defaultOut();
    }

    public void procedureIn(BlockProcedure procedure){
        defaultIn();
    }

    public void procedureOut(BlockProcedure procedure){
        defaultOut();
    }

    public void procedureNameIn(BlockProcedureName procedureName){
        defaultIn();
    }

    public void procedureNameOut(BlockProcedureName procedureName){
        defaultOut();
    }

    public void parameterListIn(BlockVariableList variableList){
        defaultIn();
    }

    public void parameterListOut(BlockVariableList variableList){
        defaultOut();
    }

    public void declarationIn(BlockVariableList variableList){
        defaultIn();
    }

    public void declarationOut(BlockVariableList variableList){
        defaultIn();
    }


    public void variableIn(BlockVariable variable){
        defaultIn();
    }

    public void variableOut(BlockVariable variable){
        defaultOut();
    }

    public void statementListIn(BlockStatementList statementList){
        defaultIn();
    }

    public void statementListOut(BlockStatementList statementList){
        defaultOut();
    }

    public void assignmentIn(BlockAssignment assignment){
        defaultIn();
    }

    public void assignmentOut(BlockAssignment assignment){
        defaultOut();
    }

    public void expressionIn(BlockExpression expression){
        defaultIn();
    }

    public void expressionOut(BlockExpression expression){
        defaultOut();
    }

    public void expressionRestIn(BlockExpressionRest expressionRest){
        defaultIn();
    }

    public void expressionRestOut(BlockExpressionRest expressionRest){
        defaultOut();
    }

    public void printIn(BlockPrint print){
        defaultIn();
    }

    public void printOut(BlockPrint print){
        defaultOut();
    }

    public void callIn(BlockCall call){
        defaultIn();
    }

    public void callOut(BlockCall call){
        defaultOut();
    }

    public void  argumentListIn(BlockVariableList variableList){
        defaultIn();
    }

    public void  argumentListOut(BlockVariableList variableList){
        defaultOut();
    }

    public void exitZeroIn(BlockExitZero exitZero){
        defaultIn();
    }

    public void exitZeroOut(BlockExitZero exitZero){
        defaultOut();
    }
}
