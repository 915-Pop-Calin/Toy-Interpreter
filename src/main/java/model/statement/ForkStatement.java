package model.statement;

import model.adt.MyIStack;
import model.exceptions.MyException;
import repository.ProgramState;

import java.util.LinkedList;
import java.util.List;

public class ForkStatement implements IStatement{
    private final IStatement statement;

    public ForkStatement(IStatement statement){
        this.statement = statement;
    }

    @Override
    public List<ProgramState> execute(ProgramState state) throws MyException {
        MyIStack<IStatement> newStack = state.getExecutionStack().push(statement);
        ProgramState newProgramState = new ProgramState(state.getID() + 1, newStack, state.getSymbolTable(), state.getOut(), state.getFileTable(),
                state.getOriginalProgram(), state.getHeap());
        LinkedList<ProgramState> linkedList = new LinkedList<>();
        linkedList.add(newProgramState);
        linkedList.add(state);
        return linkedList;
    }

    @Override
    public String toString() {
        return "if (fork() == 0){" + statement + "}";
    }
}
