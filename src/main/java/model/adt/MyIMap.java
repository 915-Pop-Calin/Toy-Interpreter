package model.adt;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MyIMap<key_type, value_type> {
    boolean isDefined(key_type key);
    value_type lookup(key_type key);
    void update(key_type key, value_type value);
    void delete(key_type key);
    Set<key_type> getKeys();
}
