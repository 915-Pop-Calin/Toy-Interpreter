package model.statement;

import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIStack;
import model.exceptions.*;
import model.expression.Expression;
import model.expression.ValueExpression;
import model.type.Type;
import model.value.Value;
import repository.ProgramState;

import java.util.LinkedList;
import java.util.List;

public final class AssignStatement implements IStatement {
    private final String id;
    private final Expression expression;

    public AssignStatement(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public List<ProgramState> execute(ProgramState state) throws MyException {
        // e sus si asta
        MyIStack<IStatement> stack = state.getExecutionStack();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIDictionary<String, Value> newSymbolTable = null;
        MyIHeap<Integer, Value> heap = state.getHeap();


        if (symbolTable.isDefined(id)) {
            Value val = expression.evaluate(symbolTable, heap);
            Type typeID = (symbolTable.lookup(id)).getType();
            if ((val.getType()).equals(typeID)) {
                newSymbolTable = symbolTable.update(id, val);
            } else
                throw new WrongTypeOfVariableException("declared type of variable" + id + " and type of the assigned expression do not match");
        }
        else
            throw new UndeclaredVariableException("the used variable " + id + " was not declared before");

        LinkedList<ProgramState> linkedList = new LinkedList<>();
        ProgramState newState =  state.setSymbolTable(newSymbolTable);
        linkedList.add(newState);
        return linkedList;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type variableType = typeEnv.lookup(id);
        Type expressionType = expression.typeCheck(typeEnv);
        if (variableType.equals(expressionType))
            return typeEnv;
        throw new TypeCheckException("ASSIGN STATEMENT: right hand side and left hand side have different types");
    }

    @Override
    public String toString() {
        return id + "=" + expression.toString();
    }
}
