package model.expression;

import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.exceptions.DivisionOverflowException;
import model.exceptions.InvalidOperandException;
import model.exceptions.InvalidOperandTypesException;
import model.exceptions.MyException;
import model.type.IntType;
import model.value.IntValue;
import model.value.Value;

public final class ArithmeticExpression implements Expression{
    private final Expression expression1;
    private final Expression expression2;
    public enum Operation{
        PLUS,
        MINUS,
        MULTIPLICATION,
        DIVISION
    }
    private final Operation operation;

    public ArithmeticExpression(Operation operation, Expression expression1, Expression expression2){
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> table, MyIHeap<Integer, Value> heap) throws MyException{
        Value value1, value2;
        value1 = expression1.evaluate(table, heap);
        if (value1.getType() == IntType.INTEGER){
            value2 = expression2.evaluate(table, heap);
            if (value2.getType() == IntType.INTEGER){
                IntValue intValue1 = (IntValue)value1;
                IntValue intValue2 = (IntValue)value2;
                int val1 = intValue1.getValue();
                int val2 = intValue2.getValue();
                switch (operation) {
                    case PLUS -> {
                        return new IntValue(val1 + val2);
                    }
                    case MINUS -> {
                        return new IntValue(val1 - val2);
                    }
                    case MULTIPLICATION -> {
                        return new IntValue(val1 * val2);
                    }
                    case DIVISION -> {
                        if (val2 == 0)
                            throw new DivisionOverflowException("division by zero");
                        else
                            return new IntValue(val1 / val2);
                    }
                    default -> {
                        throw new InvalidOperandException("invalid operand");
                    }


                }
            }
            else
                throw new InvalidOperandTypesException("second operand is not an integer");
        }
        else
            throw new InvalidOperandTypesException("first operand is not an integer");
    }

    @Override
    public String toString() {
        String operationSign;
        switch (operation){
            case PLUS -> operationSign = "+";
            case MINUS ->  operationSign = "-";
            case MULTIPLICATION -> operationSign = "*";
            case DIVISION ->  operationSign = "/";
            default -> operationSign = "";
        }
        return expression1.toString() + operationSign + expression2.toString();
    }
}
