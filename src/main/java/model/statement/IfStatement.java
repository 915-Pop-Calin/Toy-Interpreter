package model.statement;

import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.exceptions.InvalidBooleanEvalException;
import model.exceptions.MyException;
import model.expression.Expression;
import model.expression.LogicExpression;
import model.value.BoolValue;
import model.value.Value;
import repository.ProgramState;

import java.util.LinkedList;
import java.util.List;

public final class IfStatement implements IStatement {
    private final Expression expression;
    private final IStatement thenStatement;
    private final IStatement elseStatement;

    public IfStatement(Expression expr, IStatement then, IStatement els){
        expression = expr;
        thenStatement = then;
        elseStatement = els;
    }

    @Override
    public List<ProgramState> execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        Value value = expression.evaluate(symbolTable, heap);
        if (!(value instanceof BoolValue boolValue))
            throw new InvalidBooleanEvalException("expression does not eval to bool");
        if (boolValue.getWrappedValue()){
            return thenStatement.execute(state);
        }
        else{
            return elseStatement.execute(state);
        }
    }

    @Override
    public String toString() {
        return "if (" + expression.toString() + ") then (" + thenStatement.toString() + ") else (" +
                elseStatement.toString() + "))";
    }
}
