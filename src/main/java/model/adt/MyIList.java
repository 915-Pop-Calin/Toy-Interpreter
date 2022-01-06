package model.adt;

import java.util.List;

public interface MyIList<type> {
    public void add(type element);
    public List<type> getContents();
}
