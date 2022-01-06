package model.statement;

import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.exceptions.MyException;
import model.exceptions.TypeCheckException;
import model.exceptions.WrongTypeOfVariableException;
import model.expression.Expression;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;
import repository.ProgramState;

import java.util.LinkedList;
import java.util.List;

public final class WhileStatement implements IStatement{
    private final Expression expression;
    private final IStatement statement;

    public WhileStatement(Expression expression, IStatement statement){
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public List<ProgramState>  execute(ProgramState state) throws MyException {
        Value evaluateExpression = expression.evaluate(state.getSymbolTable(), state.getHeap());
        if (evaluateExpression.getType() != BoolType.BOOL)
            throw new WrongTypeOfVariableException("expression doesn't evaluate to bool");
        BoolValue boolValue = (BoolValue) evaluateExpression;
        if (boolValue.getWrappedValue()){
            MyIStack<IStatement> stack = state.getExecutionStack();
            stack = stack.push(this);
            stack = stack.push(statement);
            state = state.setExecutionStack(stack);
        }
        LinkedList<ProgramState> linkedList = new LinkedList<>();
        linkedList.add(state);
        return linkedList;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type expressionType = expression.typeCheck(typeEnv);
        if (expressionType != BoolType.BOOL)
            throw new TypeCheckException("WHILE STATEMENT: the condition does not have the type bool");
        statement.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "while (" + expression.toString() + ") {" + statement.toString() + " }";
    }
}
