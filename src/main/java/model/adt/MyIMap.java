package model.adt;

public interface MyIMap<key_type, value_type> {
    boolean isDefined(key_type key);
    value_type lookup(key_type key);
    void update(key_type key, value_type value);
    void delete(key_type key);

}
