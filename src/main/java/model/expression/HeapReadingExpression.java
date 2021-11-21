package model.expression;

import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.exceptions.*;
import model.type.ReferenceType;
import model.type.Type;
import model.value.ReferenceValue;
import model.value.Value;

public final class HeapReadingExpression implements Expression{
    private final Expression expression;

    public HeapReadingExpression(Expression expression){
        this.expression = expression;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> table, MyIHeap<Integer, Value> heap) throws MyException {
        Value evaluatedValue = expression.evaluate(table, heap);
        if (!(evaluatedValue instanceof ReferenceValue referenceValue))
            throw new InvalidOperandException(expression.toString() + " must evaluate to reference type");

        int address = referenceValue.getAddress();
        if (!(heap.isDefined(address)))
            throw new UnusedAddressException("address is not in use");
        return heap.find(address);
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type = expression.typeCheck(typeEnv);
        if (type instanceof ReferenceType referenceType){
            return referenceType.getInnerType();
        }
        throw new TypeCheckException("HEAP READING EXPRESSION: operand is not a reference type");
    }

    @Override
    public String toString() {
        return "*(" + expression + ")";
    }
}
