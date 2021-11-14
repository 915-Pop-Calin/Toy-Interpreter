package model.statement;

import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIList;
import model.exceptions.MyException;
import model.expression.Expression;
import model.expression.VariableExpression;
import model.value.Value;
import repository.ProgramState;

import java.util.LinkedList;
import java.util.List;

public final class                                                                                                                                      PrintStatement implements IStatement {

    private final Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public List<ProgramState> execute(ProgramState state) throws MyException {
        MyIList<Value> out = state.getOut();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value printedValue = expression.evaluate(symbolTable, heap);
        MyIList<Value> modifiedOut = out.add(printedValue);

        LinkedList<ProgramState> linkedList = new LinkedList<>();
        ProgramState newState =  state.setOut(modifiedOut);
        linkedList.add(newState);
        return linkedList;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}
