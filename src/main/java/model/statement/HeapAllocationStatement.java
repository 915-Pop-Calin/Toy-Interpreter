package model.statement;

import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.exceptions.MyException;
import model.exceptions.UndeclaredVariableException;
import model.exceptions.WrongTypeOfVariableException;
import model.expression.Expression;
import model.type.ReferenceType;
import model.type.Type;
import model.value.ReferenceValue;
import model.value.Value;
import repository.ProgramState;

import java.util.LinkedList;
import java.util.List;

public final class HeapAllocationStatement implements IStatement{
    String variableName;
    Expression expression;

    public HeapAllocationStatement(String variableName, Expression expression){
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public List<ProgramState> execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();

        if (!symbolTable.isDefined(variableName))
            throw new UndeclaredVariableException("invalid variable name");

        Value currentValue = symbolTable.lookup(variableName);
        Type currentType = currentValue.getType();
        if (!(currentType instanceof ReferenceType))
            throw new WrongTypeOfVariableException("invalid variable type: " + currentType.toString() + " instead of Reference");
        Value result = expression.evaluate(symbolTable, state.getHeap());
        ReferenceValue referenceValue = (ReferenceValue) currentValue;

        Type resultType = result.getType();

        ReferenceType referencedType = (ReferenceType)referenceValue.getType();
        if (!resultType.equals(referencedType.getInnerType()))
            throw new WrongTypeOfVariableException("expected reference of " + referenceValue.getType().toString() + ", got " +
                    resultType.toString() + " instead");
        MyIHeap<Integer, Value> heap = state.getHeap();
        Integer allocatedPosition = heap.getNextEmpty();
        MyIHeap<Integer, Value> newHeap = heap.add(result);

        ReferenceValue newReferenceValue = referenceValue.setAddress(allocatedPosition);
        MyIDictionary<String, Value> newSymbolTable = symbolTable.update(variableName, newReferenceValue);
        ProgramState newProgramState = state.setSymbolTable(newSymbolTable);

        LinkedList<ProgramState> linkedList = new LinkedList<>();
        ProgramState newState =  newProgramState.setHeap(newHeap);
        linkedList.add(newState);
        return linkedList;
    }

    @Override
    public String toString() {
        return "new(" + variableName + "," + expression.toString() + ")";
    }
}
