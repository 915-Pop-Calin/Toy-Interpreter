package model.statement;

import model.adt.MyIDictionary;
import model.exceptions.InvalidFileNameException;
import model.exceptions.MyException;
import model.exceptions.UndeclaredVariableException;
import model.exceptions.WrongTypeOfVariableException;
import model.expression.Expression;
import model.type.IntType;
import model.type.StringType;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;
import repository.ProgramState;

import javax.lang.model.type.IntersectionType;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public final class ReadFile implements IStatement {
    private Expression expression;
    private String variableName;

    public ReadFile(Expression expression, String variableName){
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public List<ProgramState> execute(ProgramState state) throws MyException {
        Value value = expression.evaluate(state.getSymbolTable(), state.getHeap());
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        if (!(symbolTable.isDefined(variableName)))
            throw new UndeclaredVariableException(variableName + " is not declared");
        Value currentValue = symbolTable.lookup(variableName);
        if (currentValue.getType() != IntType.INTEGER)
            throw new WrongTypeOfVariableException(variableName + " is " + currentValue.getType().toString() + " , not integer");

        if (value.getType() != StringType.STRING)
            throw new InvalidFileNameException(value.getType().toString() + " is not a valid type for filename");
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        StringValue stringValue = (StringValue) value;
        if (!(fileTable.isDefined(stringValue)))
            throw new InvalidFileNameException(stringValue.toString() + " is not a valid file name");

        BufferedReader currentBuffer = fileTable.lookup(stringValue);
        String readLine;
        int currentVal;
        try {
            readLine = currentBuffer.readLine();
            if (readLine.isEmpty())
                currentVal = 0;
            else
                currentVal = Integer.parseInt(readLine);
        }
        catch (IOException ioException){
            readLine = null;
            currentVal = 0;
        }

        MyIDictionary<String, Value> updatedSymbolTable = symbolTable.update(variableName, new IntValue(currentVal));
        LinkedList<ProgramState> linkedList = new LinkedList<>();
        ProgramState newState =  state.setSymbolTable(updatedSymbolTable);
        linkedList.add(newState);
        return linkedList;
    }

    @Override
    public String toString() {
        return "ReadFile(" + expression.toString() + "," + variableName.toString() + ")";
    }
}
