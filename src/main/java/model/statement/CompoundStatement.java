package model.statement;

import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.exceptions.MyException;
import model.type.Type;
import repository.ProgramState;

import java.util.LinkedList;
import java.util.List;

public final class CompoundStatement implements IStatement {
    private final IStatement first;
    private final IStatement second;

    public CompoundStatement(IStatement first, IStatement second){
        this.first = first;
        this.second = second;
    }

    @Override
    public List<ProgramState> execute(ProgramState state) throws MyException {
        MyIStack<IStatement> stack = state.getExecutionStack();
        MyIStack<IStatement> finalStack = stack.push(second).push(first);

        LinkedList<ProgramState> linkedList = new LinkedList<>();
        ProgramState newState =  state.setExecutionStack(finalStack);
        linkedList.add(newState);
        return linkedList;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return second.typeCheck(first.typeCheck(typeEnv));
    }

    @Override
    public String toString() {
        return "(" + first.toString() + ";" + second.toString() + ")";
    }

    public IStatement getFirst(){
        return first;
    }

    public IStatement getSecond(){
        return second;
    }
}
