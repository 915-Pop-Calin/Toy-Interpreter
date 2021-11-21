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
        MyIMap<StringValue, BufferedReader> fileTable = state.getFileTable();

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
        fileTable.delete(stringValue);

        LinkedList<ProgramState> linkedList = new LinkedList<>();
        linkedList.add(state);
        return linkedList;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type expressionType = expression.typeCheck(typeEnv);
        if (expressionType != StringType.STRING)
            throw new TypeCheckException("CLOSE READ FILE: file to close isn't a string");
        return typeEnv;
    }

    public String toString() {
        return "CloseFile(" + expression.toString() + ")";
    }
}
