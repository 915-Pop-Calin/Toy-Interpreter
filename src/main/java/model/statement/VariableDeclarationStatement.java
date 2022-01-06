package model.statement;

import model.adt.MyIDictionary;
import model.exceptions.AlreadyExistingVariableException;
import model.exceptions.MyException;
import model.type.Type;
import model.value.Value;
import repository.ProgramState;

import java.util.LinkedList;
import java.util.List;

public final class VariableDeclarationStatement implements IStatement {
    private final String name;
    private final Type type;

    public VariableDeclarationStatement(String name, Type type){
        this.name = name;
        this.type = type;
    }

    @Override
    public List<ProgramState> execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        if (symbolTable.isDefined(name))
            throw new AlreadyExistingVariableException("variable to be defined " + name + " is already defined");
        Value defaultValue = type.defaultValue();
        MyIDictionary<String, Value> modifiedSymbolTable = symbolTable.update(name, defaultValue);
        LinkedList<ProgramState> linkedList = new LinkedList<>();
        ProgramState newState =  state.setSymbolTable(modifiedSymbolTable);
        linkedList.add(newState);
        return linkedList;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.update(name, type);
    }

    @Override
    public String toString() {
        return type + " " + name;
    }
}
