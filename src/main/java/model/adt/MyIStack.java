package model.adt;

import model.exceptions.MyException;
import org.pcollections.PStack;

import java.util.List;

public interface MyIStack<type> {
    MyIStack<type> pop() throws MyException;
    MyIStack<type> push(type value);
    MyIStack<type> insert(type value, int position);
    type top();
    boolean isEmpty();
    List<type> getItems();
}
