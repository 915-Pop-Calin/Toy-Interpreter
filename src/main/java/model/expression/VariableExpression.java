package model.expression;

import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.exceptions.MyException;
import model.value.Value;

public final class VariableExpression implements Expression{
    private final String id;

    public VariableExpression(String id){
        this.id = id;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> table, MyIHeap<Integer, Value> heap) throws MyException {
        // throw
        return table.lookup(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
