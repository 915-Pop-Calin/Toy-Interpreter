package model.adt;

import model.value.Value;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MyHeap implements MyIHeap<Integer, Value>{
    private Set<Integer> emptiedPositions;
    private AtomicInteger lastEmptyPosition;
    private Map<Integer, Value> values;

    public MyHeap(){
        emptiedPositions = Collections.synchronizedSet(new HashSet<>());
        lastEmptyPosition = new AtomicInteger(1);
        values = new ConcurrentHashMap<>();
    }

    @Override
    public Integer getNextEmpty(){
        if (emptiedPositions.isEmpty())
            return lastEmptyPosition.get();
        else
            return emptiedPositions.iterator().next();
    }

    @Override
    public void add(Value value) {
        if (emptiedPositions.isEmpty()){
            values.put(lastEmptyPosition.get(), value);
            lastEmptyPosition.set(lastEmptyPosition.get() + 1);
        }
        else{
            int firstEmpty = emptiedPositions.iterator().next();
            values.put(firstEmpty, value);
            emptiedPositions.remove(firstEmpty);
        }
    }

    @Override
    public void remove(Integer key) {
        values.remove(key);
        emptiedPositions.add(key);
    }

    @Override
    public void update(Integer key, Value value) {
        values.put(key, value);
    }

    @Override
    public Map<Integer, Value> getContent() {
        return values;
    }

    @Override
    public void setValues(Map<Integer, Value> values) {
        this.values = new ConcurrentHashMap<>(values);
    }

    @Override
    public Value find(Integer key) {
        return values.get(key);
    }

    @Override
    public boolean isDefined(Integer key) {
        return values.containsKey(key);
    }

    @Override
    public String toString() {
        return values.toString();
    }
}
