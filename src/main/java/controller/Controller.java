package controller;

import model.adt.MyDictionary;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIStack;
import model.exceptions.MyException;
import model.exceptions.MyIOException;
import model.statement.IStatement;
import model.type.Type;
import model.value.ReferenceValue;
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

    List<ProgramState> removeCompletedPrograms(List<ProgramState> inProgramList){
        return inProgramList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    private void typeCheck() throws MyException{
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

    private void oneStepForAllPrograms(List<ProgramState> programStates) throws MyIOException, InterruptedException {
        List<Callable<List<ProgramState>>> callList = programStates.stream()
                .map((ProgramState program) -> (Callable<List<ProgramState>>) (program::oneStep))
                .collect(Collectors.toList());

        programStates = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    }
                    catch (InterruptedException | ExecutionException exception) {
                        printErrorsToFile(exception);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        repository.setProgramList(programStates);

        programStates.forEach(program -> {
            try {
                repository.logProgramStateExecute(program);
            } catch (MyException exception) {
                printErrorsToFile(exception);
            }
        });
    }

    private void printErrorsToFile(Exception exception){
        try (var logFile = new PrintWriter(new BufferedWriter(new FileWriter((repository).getLogFilePath(), true)))){
            String toStr = exception.getMessage();
            logFile.println(toStr);
            logFile.close();
        }
        catch (IOException myIOException){
            System.out.println(myIOException.getMessage());
        }
    }

    public void allStep() throws MyException, InterruptedException {
        try {
            typeCheck();
        }
        catch (MyException myException){
            printErrorsToFile(myException);
            return;
        }
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programList = removeCompletedPrograms(repository.getProgramList());

        programList.forEach(program -> {
            try {
                repository.logProgramStateExecute(program);
            } catch (MyException exception) {
                printErrorsToFile(exception);
            }
        });

        while (programList.size() > 0){
            MyIHeap<Integer, Value> heap = programList.get(0).getHeap();
            oneStepForAllPrograms(programList);
            programList = removeCompletedPrograms(repository.getProgramList());
            heap.setValues(safeGarbageCollector(programList, heap.getContent()));
        }
        executor.shutdownNow();
        repository.setProgramList(programList);
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

    private Map<Integer, Value> unsafeGarbageCollector(List<Integer> symbolTableAddresses, Map<Integer, Value> heap){
        return heap.entrySet().stream()
                .filter(e->symbolTableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<Integer, Value> safeGarbageCollector(List<ProgramState> programStateList, Map<Integer, Value> heap){
        List<Integer> addresses = programStateList.stream()
                .map(program -> getAddressesFromSymbolTable(program.getSymbolTable().getContent().values(), heap))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return heap.entrySet().stream()
                .filter(e-> addresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
