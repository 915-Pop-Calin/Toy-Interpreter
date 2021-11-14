package repository;

import model.adt.*;
import model.exceptions.EmptyStackException;
import model.exceptions.MyException;
import model.statement.CompoundStatement;
import model.statement.IStatement;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.util.List;
import java.util.stream.Collectors;

public class ProgramState {
    private static int static_ID = 1;
    private final int ID;
    private MyIStack<IStatement> executionStack;
    private final MyIDictionary<String, Value> symbolTable;
    private final MyIList<Value> out;
    private final MyIDictionary<StringValue, BufferedReader> fileTable;
    private final IStatement originalProgram;
    private final MyIHeap<Integer, Value> heap;

    public ProgramState(){
        ID = static_ID;
        static_ID += 1;
        executionStack = new MyStack<IStatement>();
        symbolTable = new MyDictionary<String, Value>();
        out = new MyList<Value>();
        fileTable = new MyDictionary<StringValue, BufferedReader>();
        heap  = new MyHeap();
        originalProgram = null;
    }

    public Boolean isNotCompleted(){
        return !executionStack.isEmpty();
    }

    public List<ProgramState> oneStep() throws MyException{
        if (executionStack.isEmpty())
            throw new EmptyStackException("program state stack is empty");
        IStatement currentStatement = executionStack.top();
        MyIStack<IStatement> poppedStack = executionStack.pop();
        executionStack = poppedStack;
        return currentStatement.execute(this);
    }

    public MyIStack<IStatement> getExecutionStack() {
        return executionStack;
    }

    public ProgramState setExecutionStack(MyIStack<IStatement> executionStack) {
        return new ProgramState(ID, executionStack, symbolTable, out, fileTable, originalProgram, heap);
    }

    public MyIDictionary<String, Value> getSymbolTable() {
        return symbolTable;
    }

    public ProgramState setSymbolTable(MyIDictionary<String, Value> symbolTable) {
        return new ProgramState(ID, executionStack, symbolTable, out, fileTable, originalProgram, heap);
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public ProgramState setOut(MyIList<Value> out) {
        return new ProgramState(ID, executionStack, symbolTable, out, fileTable, originalProgram, heap);
    }

    public IStatement getOriginalProgram() {
        return originalProgram;
    }

    public ProgramState setOriginalProgram(IStatement originalProgram) {
        return new ProgramState(ID, executionStack, symbolTable, out, fileTable, originalProgram, heap);
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable(){
        return fileTable;
    }

    public ProgramState setFileTable(MyIDictionary<StringValue, BufferedReader> fileTable){
        return new ProgramState(ID, executionStack, symbolTable, out, fileTable, originalProgram, heap);
    }

    public MyIHeap<Integer, Value> getHeap(){
        return heap;
    }

    public ProgramState setHeap(MyIHeap<Integer, Value> heap){
        return new ProgramState(ID, executionStack, symbolTable, out, fileTable, originalProgram, heap);
    }

    public Integer getID(){
        return ID;
    }

    public ProgramState(int ID, MyIStack<IStatement> stack, MyIDictionary<String, Value> symbolTable, MyIList<Value> out,
                        MyIDictionary<StringValue, BufferedReader> fileTable, IStatement program, MyIHeap<Integer, Value> heap){
        this.ID = ID;
        this.executionStack = stack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.fileTable = fileTable;
        this.originalProgram = program;
        this.heap = heap;
        stack.push(program);
    }

    @Override
    public String toString() {
        return "ID:" + ID + "\n" +
                "ExeStack:\n" + StackToString() +
                "SymTable:\n" + symbolTable +
                "Out:\n" + out +
                "FileTable:\n" + fileTable +
                "Heap:\n" + heap + "\n";
    }

    private static String InorderTraversal(IStatement currentStatement){
        if (!(currentStatement instanceof CompoundStatement compoundStatement))
            return currentStatement.toString() + ";\n";
        String leftString, rightString;
        IStatement leftStatement = compoundStatement.getFirst(), rightStatement = compoundStatement.getSecond();
        if (!(leftStatement instanceof CompoundStatement)){
            leftString = leftStatement.toString() + ";\n";
        }
        else{
            leftString = InorderTraversal(leftStatement);
        }

        if (!(rightStatement instanceof CompoundStatement)){
            rightString = rightStatement.toString() + ";\n";
        }
        else{
            rightString = InorderTraversal(rightStatement);
        }
        return leftString + rightString;

    }

    private String StackToString(){
        String toStr = "";
        MyIStack<IStatement> currentStack = executionStack;
        while (!currentStack.isEmpty()) {
            toStr += InorderTraversal(currentStack.top());
            try {
                currentStack = currentStack.pop();
            }
            catch (Exception exception) {

            }
        }
        return toStr;

    }
}
