package model.statement;

import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.exceptions.*;
import model.expression.Expression;
import model.type.ReferenceType;
import model.type.Type;
import model.value.ReferenceValue;
import model.value.Value;
import repository.ProgramState;

import java.util.LinkedList;
import java.util.List;

public final class HeapWritingStatement implements IStatement{
    private final Expression expression;
    private final String variableName;

    public HeapWritingStatement(String variableName, Expression expression){
        this.variableName = variableName;
        this.expression = expression;
    }


    @Override
    public List<ProgramState> execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> table = state.getSymbolTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        if (!table.isDefined(variableName))
            throw new UndeclaredVariableException(variableName + " is undeclared");
        Value currentValue = table.lookup(variableName);
        if (!(currentValue instanceof ReferenceValue referenceValue))
            throw new WrongTypeOfVariableException(variableName + " is not a reference");

        int address = referenceValue.getAddress();
        Type locationType = referenceValue.getLocationType();
        if (!(heap.isDefined(address)))
            throw new UnusedAddressException(address + " is not defined in the heap");

        Value evaluatedValue = expression.evaluate(table, heap);
        if (!(evaluatedValue.getType().equals(locationType)))
            throw new WrongTypeOfVariableException(expression.toString() + " does not evaluate to " + locationType.toString());

        heap.update(address, evaluatedValue);
        LinkedList<ProgramState> linkedList = new LinkedList<>();
        linkedList.add(state);
        return linkedList;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type expressionType = expression.typeCheck(typeEnv);
        Type variableType = typeEnv.lookup(variableName);
        if (!(variableType instanceof ReferenceType referenceType))
            throw new TypeCheckException("HEAP WRITING STATEMENT: variable is not a reference type");
        Type wrappedType = referenceType.getInnerType();
        if (!(expressionType.equals(wrappedType)))
            throw new InvalidOperandTypesException("HEAP WRITING STATEMENT: the wrapped type is not the same as the expression type");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "*(" + variableName + ")=" + expression.toString();
    }
}
