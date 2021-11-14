package model.adt;

import java.util.Map;

public interface MyIHeap<key_type, value_type> {
    MyIHeap<key_type, value_type> add(value_type value);
    MyIHeap<key_type, value_type> remove(key_type key);
    MyIHeap<key_type, value_type> update(key_type key, value_type value);
    Map<key_type, value_type> getContent();
    MyIHeap<key_type, value_type> setValues(Map<key_type, value_type> values);
    value_type find(key_type key);
    boolean isDefined(key_type key);
    key_type getNextEmpty();
}
