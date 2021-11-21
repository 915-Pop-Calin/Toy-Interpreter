package model.adt;

import java.util.HashMap;
import java.util.Map;

public class MyMap<key_type, value_type> implements MyIMap<key_type, value_type>{
    private Map<key_type, value_type> map;

    public MyMap(){
        map = new HashMap<>();
    }

    @Override
    public boolean isDefined(key_type key) {
        return map.containsKey(key);
    }

    @Override
    public value_type lookup(key_type key) {
        return map.get(key);
    }

    @Override
    public void update(key_type key, value_type value) {
        map.put(key, value);
    }

    @Override
    public void delete(key_type key) {
        map.remove(key);
    }

    @Override
    public String toString() {
        var toStr = "";
        for (key_type key: map.keySet()) {
            toStr += key.toString() + ":" + map.get(key).toString() + "\n";
        }
        return toStr;
    }
}
