package model.statement;

import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.adt.MyStack;
import model.exceptions.MyException;
import model.type.Type;
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
        MyIStack<IStatement> newStack = new MyStack<>();
        newStack = newStack.push(statement);
        ProgramState newProgramState = new ProgramState(newStack, state.getSymbolTable(), state.getOut(), state.getFileTable(),
                state.getOriginalProgram(), state.getHeap());
        LinkedList<ProgramState> linkedList = new LinkedList<>();
        linkedList.add(newProgramState);
        linkedList.add(state);
        return linkedList;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        statement.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "if (fork()==0){ " + statement + "}";
    }
}
