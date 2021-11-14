package model.adt;

import model.exceptions.MyException;

public interface MyIStack<type> {
    MyIStack<type> pop() throws MyException;
    MyIStack<type> push(type value);
    MyIStack<type> insert(type value, int position);
    type top();
    boolean isEmpty();
}
