package controller;

import model.adt.*;
import model.exceptions.MyException;
import model.exceptions.MyIOException;
import model.statement.IStatement;
import model.type.Type;
import model.value.ReferenceValue;
import model.value.StringValue;
import model.value.Value;
import repository.IRepository;
import repository.ProgramState;
import repository.Repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repository;
    private final boolean displayFlag;
    private ExecutorService executor;

    public Controller(Repository repository){
        this.repository = repository;
        displayFlag = true;
    }

    public List<ProgramState> removeCompletedPrograms(){
        return repository.getProgramList().stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void setProgramList(List<ProgramState> programList){
        repository.setProgramList(programList);
    }

    public void typeCheck() throws MyException{
        MyIDictionary<String, Type> typeEnv = new MyDictionary<>();
        List<ProgramState> programStates = repository.getProgramList();
        if (programStates.size() != 1)
            throw new MyException("bad moment to type check");
        ProgramState programState = programStates.get(0);
        MyIStack<IStatement> executionStack = programState.getExecutionStack();
        while (!executionStack.isEmpty()){
            IStatement topStatement = executionStack.top();
            executionStack = executionStack.pop();
            typeEnv = topStatement.typeCheck(typeEnv);
        }
    }

    public void oneStep() throws Exception{
        var listOfPrograms = repository.getProgramList();
        oneStepForAllPrograms(listOfPrograms);
    }

    public void oneStepForAllPrograms(List<ProgramState> programStates) throws MyException, InterruptedException {
        List<Callable<List<ProgramState>>> callList = programStates.stream()
                .map((ProgramState program) -> (Callable<List<ProgramState>>) (program::oneStep))
                .collect(Collectors.toList());

        programStates = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    }
                    catch (InterruptedException | ExecutionException exception) {
                        throw new RuntimeException(exception.getMessage());
                    }
                })
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        repository.setProgramList(programStates);
    }

    public ProgramState getProgramStateByID(int ID){
        for (ProgramState programState : repository.getProgramList())
            if (programState.getID() == ID)
                return programState;
        return null;
    }

    public int getNumberOfPrograms(){
        return repository.getProgramList().size();
    }

    public MyIHeap<Integer, Value> getHeap(){
        if (repository.getProgramList().size() == 0)
            return new MyHeap();
        return repository.getProgramList().get(0).getHeap();
    }

    public MyIList<Value> getOut() {
        if (repository.getProgramList().size() == 0)
            return new MyList<>();
        return repository.getProgramList().get(0).getOut();
    }

    public List<Integer> getProgramStateIdentifiers(){
        return repository.getProgramList().stream()
                .map(ProgramState::getID)
                .collect(Collectors.toList());
    }

    public Set<StringValue> getFileTable(){
        if (repository.getProgramList().size() == 0)
            return new HashSet<>();
        return repository.getProgramList().get(0).getFileTable().getKeys();
    }

    public void createExecutor(){
         executor = Executors.newFixedThreadPool(2);
    }

    public void destroyExecutor(){
        executor.shutdownNow();
    }

    private List<Integer> getAddressesFromSymbolTable(Collection<Value> symbolTableValues, Map<Integer, Value> heap){
        return new ArrayList<>(symbolTableValues.stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> {
                    Collection<Integer> addresses = new LinkedList<>();
                    while (v instanceof ReferenceValue v1) {
                        int address = v1.getAddress();
                        addresses.add(address);
                        v = heap.get(address);
                    }
                    return addresses;
                })
                .reduce(new LinkedList<>(), (Collection<Integer> e1, Collection<Integer> e2) -> {
                    LinkedList<Integer> l1 = new LinkedList<>();
                    l1.addAll(e1);
                    l1.addAll(e2);
                    return l1;
                }));
    }

    public Map<Integer, Value> safeGarbageCollector(List<ProgramState> programStateList, Map<Integer, Value> heap){
        List<Integer> addresses = programStateList.stream()
                .map(program -> getAddressesFromSymbolTable(program.getSymbolTable().getContent().values(), heap))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return heap.entrySet().stream()
                .filter(e-> addresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
