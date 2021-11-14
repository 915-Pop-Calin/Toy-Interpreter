package model.statement;

import model.exceptions.MyException;
import model.expression.Expression;
import repository.ProgramState;

import java.util.LinkedList;
import java.util.List;

public final class NopStatement implements IStatement {

    @Override
    public List<ProgramState> execute(ProgramState state) throws MyException {
        LinkedList<ProgramState> linkedList = new LinkedList<>();
        linkedList.add(state);
        return linkedList;
    }

    @Override
    public String toString() {
        return "nop";
    }
}
