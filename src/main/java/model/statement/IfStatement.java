package model.statement;

import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.exceptions.InvalidBooleanEvalException;
import model.exceptions.InvalidOperandTypesException;
import model.exceptions.MyException;
import model.exceptions.TypeCheckException;
import model.expression.Expression;
import model.expression.LogicExpression;
import model.type.BoolType;
import model.type.Type;
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
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type expressionType = expression.typeCheck(typeEnv);
        if (expressionType != BoolType.BOOL){
            throw new TypeCheckException("IF STATEMENT: the condition doesn't have the type bool");
        }
        thenStatement.typeCheck(typeEnv);
        elseStatement.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "if (" + expression.toString() + ") then (" + thenStatement.toString() + ") else (" +
                elseStatement.toString() + "))";
    }
}
