package controller;

import model.exceptions.MyException;
import model.value.ReferenceValue;
import model.value.Value;
import repository.IRepository;
import repository.ProgramState;
import repository.Repository;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repository;
    private final boolean displayFlag;
    private ExecutorService executor;
    private HashSet<Integer> programStatesIDs;

    public Controller(Repository repository){
        this.repository = repository;
        displayFlag = true;
        programStatesIDs = new HashSet<>();

        for (ProgramState programState : repository.getProgramList())
            programStatesIDs.add(programState.getID());
    }

    List<ProgramState> removeCompletedPrograms(List<ProgramState> inProgramList){
        return inProgramList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    private void oneStepForAllPrograms(List<ProgramState> programStates) throws MyException, InterruptedException {
        programStates.forEach(program -> {
            try {
                repository.logProgramStateExecute(program);
            } catch (MyException e) {
                e.printStackTrace();
            }
        });
        List<Callable<ProgramState>> callList = programStates.stream()
                .map((ProgramState program) -> (Callable<ProgramState>) (program::oneStep))
                .collect(Collectors.toList());

        List<ProgramState> newProgramList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException exc) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        programStates = newProgramList;
        repository.setProgramList(programStates);
    }

    public void allStep() throws MyException, InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programList = removeCompletedPrograms(repository.getProgramList());

        while (programList.size() > 0){
            System.out.println(programList.size());
            oneStepForAllPrograms(programList);
            programList = removeCompletedPrograms(repository.getProgramList());
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



}
