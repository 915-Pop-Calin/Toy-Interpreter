package model.statement;

import model.adt.MyIDictionary;
import model.exceptions.MyException;
import model.type.Type;
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
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "nop";
    }
}
