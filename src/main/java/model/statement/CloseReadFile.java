package model.statement;

import model.adt.MyIDictionary;
import model.exceptions.InvalidFileNameException;
import model.exceptions.MyException;
import model.exceptions.MyIOException;
import model.expression.Expression;
import model.type.StringType;
import model.value.StringValue;
import model.value.Value;
import repository.ProgramState;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public final class CloseReadFile implements IStatement{

    private Expression expression;

    public CloseReadFile(Expression expression){
        this.expression = expression;
    }

    @Override
    public List<ProgramState> execute(ProgramState state) throws MyException {
        Value value = expression.evaluate(state.getSymbolTable(), state.getHeap());
        if (value.getType() != StringType.STRING)
            throw new InvalidFileNameException(value.getType().toString() + " is not a valid type for filename");
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        StringValue stringValue = (StringValue) value;
        if (!(fileTable.isDefined(stringValue)))
            throw new InvalidFileNameException(stringValue.toString() + " is not a valid file name");
        BufferedReader bufferedReader = fileTable.lookup(stringValue);
        try {
            bufferedReader.close();
        }
        catch (IOException ioException){
            throw new MyIOException("closing " + stringValue + " was not possible");
        }
        MyIDictionary<StringValue, BufferedReader> newFileTable = fileTable.delete(stringValue);

        LinkedList<ProgramState> linkedList = new LinkedList<>();
        ProgramState newState =  state.setFileTable(newFileTable);
        linkedList.add(newState);
        return linkedList;
    }

    public String toString() {
        return "CloseFile(" + expression.toString() + ")";
    }
}
