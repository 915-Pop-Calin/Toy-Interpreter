package model.expression;

import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.exceptions.InvalidOperandException;
import model.exceptions.InvalidOperandTypesException;
import model.exceptions.MyException;
import model.exceptions.TypeCheckException;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

public class RelationalExpression implements Expression{
    private final Expression expression1;
    private final Expression expression2;

    @Override
    public Value evaluate(MyIDictionary<String, Value> table, MyIHeap<Integer, Value> heap) throws MyException {
        Value value1, value2;
        value1 = expression1.evaluate(table, heap);
        value2 = expression2.evaluate(table, heap);
        if (value1.getType() != IntType.INTEGER)
            throw new InvalidOperandTypesException("first operand is not an integer");
        if (value2.getType() != IntType.INTEGER)
            throw new InvalidOperandTypesException("second operand is not an integer");
        IntValue intValue1 = (IntValue) value1, intValue2 = (IntValue) value2;
        int wrappedValue1 = intValue1.getValue(), wrappedValue2 = intValue2.getValue();
        switch (operation){
            case LESS ->{
                return BoolValue.toFrom(wrappedValue1 < wrappedValue2);
            }
            case LESS_OR_EQUAL -> {
                return BoolValue.toFrom(wrappedValue1 <= wrappedValue2);
            }
            case EQUALS -> {
                return BoolValue.toFrom(wrappedValue1 == wrappedValue2);
            }
            case DIFFERENT -> {
                return BoolValue.toFrom(wrappedValue1 != wrappedValue2);
            }
            case GREATER -> {
                return BoolValue.toFrom(wrappedValue1 > wrappedValue2);
            }
            case GREATER_OR_EQUAL -> {
                return BoolValue.toFrom(wrappedValue1 >= wrappedValue2);
            }
            default ->{
                throw new InvalidOperandException("invalid operand");
            }
        }
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = expression1.typeCheck(typeEnv);
        if (type1 != IntType.INTEGER)
            throw new TypeCheckException("RELATIONAL EXPRESSION: first operand is not an integer");
        type2 = expression2.typeCheck(typeEnv);
        if (type2 != IntType.INTEGER)
            throw new InvalidOperandTypesException("RELATIONAL EXPRESSION: second operand is not an integer");
        return BoolType.BOOL;
    }

    public enum Operation{
        LESS,
        LESS_OR_EQUAL,
        EQUALS,
        DIFFERENT,
        GREATER,
        GREATER_OR_EQUAL
    }

    private final Operation operation;

    public RelationalExpression(Operation operation, Expression expression1, Expression expression2){
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public String toString() {
        String operationSign;
        switch (operation) {
            case LESS -> operationSign = "<";
            case LESS_OR_EQUAL -> operationSign = "<=";
            case EQUALS -> operationSign = "==";
            case DIFFERENT -> operationSign = "!=";
            case GREATER -> operationSign = ">";
            case GREATER_OR_EQUAL -> operationSign = ">=";
            default -> operationSign = "";
        }
        return expression1.toString() + operationSign + expression2.toString();
    }
}
