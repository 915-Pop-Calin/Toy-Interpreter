package model.statement;

import model.adt.MyIDictionary;
import model.exceptions.AlreadyOpenedFileException;
import model.exceptions.InvalidFileNameException;
import model.exceptions.MyException;
import model.exceptions.MyIOException;
import model.expression.Expression;
import model.type.StringType;
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
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        if (fileTable.isDefined(stringValue))
            throw new AlreadyOpenedFileException(stringValue.toString() + " has already been opened");
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(stringValue.toString()));
        }
        catch (IOException ioException){
            throw new MyIOException("opening " + stringValue + " was not possible");
        }
        MyIDictionary<StringValue, BufferedReader> newFileTable = fileTable.update(stringValue, bufferedReader);

        LinkedList<ProgramState> linkedList = new LinkedList<>();
        ProgramState newState =  state.setFileTable(newFileTable);
        linkedList.add(newState);
        return linkedList;
    }

    @Override
    public String toString() {
        return "OpenFile(" + expression.toString() + ",r)";
    }
}
