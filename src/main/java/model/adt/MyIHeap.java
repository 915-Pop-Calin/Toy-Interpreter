package model.adt;

import java.util.Map;

public interface MyIHeap<key_type, value_type> {
    void add(value_type value);
    void remove(key_type key);
    void update(key_type key, value_type value);
    Map<key_type, value_type> getContent();
    void setValues(Map<key_type, value_type> values);
    value_type find(key_type key);
    boolean isDefined(key_type key);
    key_type getNextEmpty();
}
