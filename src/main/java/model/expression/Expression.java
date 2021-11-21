package model.expression;

import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.exceptions.MyException;
import model.type.Type;
import model.value.Value;

public interface Expression{
    Value evaluate(MyIDictionary<String, Value> table, MyIHeap<Integer, Value> heap) throws MyException;
    Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
