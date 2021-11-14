package model.statement;

import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.exceptions.InvalidOperandException;
import model.exceptions.MyException;
import model.exceptions.UndeclaredVariableException;
import model.exceptions.WrongTypeOfVariableException;
import model.expression.Expression;
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
            throw new MyException(address + " is not defined in the heap");

        Value evaluatedValue = expression.evaluate(table, heap);
        if (!(evaluatedValue.getType().equals(locationType)))
            throw new InvalidOperandException(expression.toString() + " does not evaluate to " + locationType.toString());

        MyIHeap<Integer, Value> newHeap = heap.update(address, evaluatedValue);

        LinkedList<ProgramState> linkedList = new LinkedList<>();
        ProgramState newState =  state.setHeap(newHeap);
        linkedList.add(newState);
        return linkedList;
    }

    @Override
    public String toString() {
        return "*(" + variableName + ")=" + expression.toString();
    }
}
