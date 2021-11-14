package model.adt;

import java.util.Map;

public interface MyIDictionary<key_type, value_type> {
    boolean isDefined(key_type key);
    value_type lookup(key_type key);
    MyIDictionary<key_type, value_type> update(key_type key, value_type value);
    MyIDictionary<key_type, value_type> delete(key_type key);
    Map<key_type, value_type> getContent();
}
