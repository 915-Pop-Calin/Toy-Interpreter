package model.adt;

import model.value.Value;
import org.pcollections.HashTreePMap;
import org.pcollections.OrderedPSet;
import org.pcollections.PMap;

import java.util.*;

public class MyHeap implements MyIHeap<Integer, Value>{
    private Set<Integer> emptiedPositions;
    private int lastEmptyPosition;
    private Map<Integer, Value> values;

    public MyHeap(Set<Integer> emptiedPositions, int lastEmptyPosition, Map<Integer, Value> values){
        this.emptiedPositions = emptiedPositions;
        this.lastEmptyPosition = lastEmptyPosition;
        this.values = values;
    }

    public MyHeap(){
        emptiedPositions = new HashSet<>();
        lastEmptyPosition = 1;
        values = new HashMap<>();
    }

    @Override
    public Integer getNextEmpty(){
        if (emptiedPositions.isEmpty())
            return lastEmptyPosition;
        else
            return emptiedPositions.iterator().next();
    }

    @Override
    public void add(Value value) {
        if (emptiedPositions.isEmpty()){
            values.put(lastEmptyPosition, value);
            lastEmptyPosition += 1;
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
        this.values = values;
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
