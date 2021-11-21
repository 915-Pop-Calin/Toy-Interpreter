package model.expression;

import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.exceptions.MyException;
import model.type.Type;
import model.value.Value;

public final class ValueExpression implements Expression{
    private final Value value;

    public ValueExpression(Value value){
        this.value = value;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> table, MyIHeap<Integer, Value> heap) throws MyException {
        return value;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return value.getType();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
