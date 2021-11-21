package model.expression;

import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.exceptions.InvalidOperandException;
import model.exceptions.InvalidOperandTypesException;
import model.exceptions.MyException;
import model.exceptions.TypeCheckException;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public final class LogicExpression implements Expression{
    private final Expression expression1;
    private final Expression expression2;

    public enum Operation{
        AND,
        OR
    }
    private final Operation operation;

    public LogicExpression(Expression expression1, Expression expression2, Operation operation){
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> table, MyIHeap<Integer, Value> heap) throws MyException {
        Value value1, value2;
        value1 = expression1.evaluate(table, heap);
        if (value1.getType() == BoolType.BOOL){
            value2 = expression2.evaluate(table, heap);
            if (value2.getType() == BoolType.BOOL){
                BoolValue boolValue1 = (BoolValue)value1;
                BoolValue boolValue2 = (BoolValue)value2;
                boolean val1 = boolValue1.getWrappedValue();
                boolean val2 = boolValue2.getWrappedValue();
                switch (operation){
                    case AND ->{
                        return BoolValue.toFrom(val1 && val2);
                    }
                    case OR ->{
                        return BoolValue.toFrom(val1 || val2);
                    }
                    default ->{
                        throw new InvalidOperandException("invalid operand");
                    }
                }
            }
            else
                throw new InvalidOperandTypesException("second operand is not a boolean");
        }
        else
            throw new InvalidOperandTypesException("first operand is not a boolean");
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = expression1.typeCheck(typeEnv);
        if (type1 != BoolType.BOOL)
            throw new TypeCheckException("LOGIC EXPRESSION: first operand is not a boolean");
        type2 = expression2.typeCheck(typeEnv);
        if (type2 != BoolType.BOOL)
            throw new InvalidOperandTypesException("LOGIC EXPRESSION: second operand is not a boolean");
        return BoolType.BOOL;
    }

    @Override
    public String toString() {
        String operationSign;
        switch (operation){
            case AND -> operationSign = "&&";
            case OR -> operationSign = "||";
            default -> operationSign = "";
        }
        return expression1.toString() + operationSign + expression2.toString();
    }
}
