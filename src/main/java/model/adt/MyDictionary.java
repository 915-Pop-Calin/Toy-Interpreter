package model.adt;

import java.util.HashMap;
import java.util.Map;

import org.pcollections.*;

public final class MyDictionary<key_type, value_type>  implements MyIDictionary<key_type, value_type>{
    private final PMap<key_type, value_type> dictionary;

    public MyDictionary(){
        dictionary = HashTreePMap.empty();
    }

    public MyDictionary(PMap<key_type, value_type> myDictionary){
        this.dictionary = myDictionary;
    }

    @Override
    public boolean isDefined(key_type key) {
        return dictionary.containsKey(key);
    }

    @Override
    public value_type lookup(key_type key) {
        return dictionary.get(key);
    }

    @Override
    public MyIDictionary<key_type, value_type> update(key_type key, value_type value) {
        PMap<key_type, value_type> newDictionary = dictionary.plus(key, value);
        return new MyDictionary<key_type, value_type>(newDictionary);
    }

    @Override
    public MyIDictionary<key_type, value_type> delete(key_type key) {
        PMap<key_type, value_type> newDictionary = dictionary.minus(key);
        return new MyDictionary<key_type, value_type>(newDictionary);
    }

    @Override
    public Map<key_type, value_type> getContent() {
        return dictionary;
    }

    @Override
    public String toString() {
        var toStr = "";
        for (key_type key : dictionary.keySet())
            toStr += key.toString() + " --> " + dictionary.get(key).toString() + "\n";
        return toStr;
    }
}