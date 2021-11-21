package model.statement;

import model.adt.MyIDictionary;
import model.adt.MyIMap;
import model.exceptions.*;
import model.expression.Expression;
import model.type.StringType;
import model.type.Type;
import model.value.StringValue;
import model.value.Value;
import repository.ProgramState;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public final class OpenReadFile implements IStatement{
    private Expression expression;

    public OpenReadFile(Expression expression){
        this.expression = expression;
    }

    @Override
    public List<ProgramState> execute(ProgramState state) throws MyException {
        Value value = expression.evaluate(state.getSymbolTable(), state.getHeap());
        if (value.getType() != StringType.STRING)
            throw new InvalidFileNameException(value.getType().toString() + " is not a valid type for filename");
        StringValue stringValue = (StringValue) value;
        MyIMap<StringValue, BufferedReader> fileTable = state.getFileTable();
        if (fileTable.isDefined(stringValue))
            throw new AlreadyOpenedFileException(stringValue.toString() + " has already been opened");
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(stringValue.toString()));
        }
        catch (IOException ioException){
            throw new MyIOException("opening " + stringValue + " was not possible");
        }
        fileTable.update(stringValue, bufferedReader);
        LinkedList<ProgramState> linkedList = new LinkedList<>();
        linkedList.add(state);
        return linkedList;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type expressionType = expression.typeCheck(typeEnv);
        if (expressionType != StringType.STRING)
            throw new TypeCheckException("OPEN READ FILE: file to open isn't a string");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "OpenFile(" + expression.toString() + ",r)";
    }
}
