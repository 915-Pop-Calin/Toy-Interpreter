package model.adt;

import model.value.Value;
import org.pcollections.HashTreePMap;
import org.pcollections.OrderedPSet;
import org.pcollections.PMap;

import java.util.AbstractSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class MyHeap implements MyIHeap<Integer, Value>{
    private final OrderedPSet<Integer> emptiedPositions;
    private final int lastEmptyPosition;
    private final PMap<Integer, Value> values;

    public MyHeap(OrderedPSet<Integer> emptiedPositions, int lastEmptyPosition, PMap<Integer, Value> values){
        this.emptiedPositions = emptiedPositions;
        this.lastEmptyPosition = lastEmptyPosition;
        this.values = values;
    }

    public MyHeap(){
        emptiedPositions = OrderedPSet.empty();
        lastEmptyPosition = 1;
        values = HashTreePMap.empty();
    }

    @Override
    public Integer getNextEmpty(){
        if (emptiedPositions.isEmpty())
            return lastEmptyPosition;
        else
            return emptiedPositions.iterator().next();
    }

    @Override
    public MyIHeap<Integer, Value> add(Value value) {
        if (emptiedPositions.isEmpty()){
            PMap<Integer, Value> newValues = values.plus(lastEmptyPosition, value);
            int newLastEmptyPosition = lastEmptyPosition + 1;
            return new MyHeap(emptiedPositions, newLastEmptyPosition, newValues);
        }
        else{
            int firstEmpty = emptiedPositions.iterator().next();
            PMap<Integer, Value> newValues = values.plus(firstEmpty, value);
            OrderedPSet<Integer> newEmptyPositions = emptiedPositions.minus(firstEmpty);
            return new MyHeap(newEmptyPositions, lastEmptyPosition, newValues);
        }
    }

    @Override
    public MyIHeap<Integer, Value> remove(Integer key) {
        PMap<Integer, Value> newValues = values.minus(key);
        OrderedPSet<Integer> newEmptyPositions = emptiedPositions.plus(key);
        return new MyHeap(newEmptyPositions, lastEmptyPosition, newValues);
    }

    @Override
    public MyIHeap<Integer, Value> update(Integer key, Value value) {
        PMap<Integer, Value> newValues = values.plus(key, value);
        return new MyHeap(emptiedPositions, lastEmptyPosition, newValues);
    }

    @Override
    public Map<Integer, Value> getContent() {
        return values;
    }

    @Override
    public MyIHeap<Integer, Value> setValues(Map<Integer, Value> values) {
        Set<Integer> keys = values.keySet();
        PMap<Integer, Value> newValues = HashTreePMap.empty();
        for (Integer key: keys){
            newValues = newValues.plus(key, values.get(key));
        }
        return new MyHeap(emptiedPositions, lastEmptyPosition, newValues);
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
