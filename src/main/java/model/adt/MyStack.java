package model.adt;

import model.exceptions.EmptyStackException;
import model.exceptions.MyException;
import org.pcollections.*;

public final class MyStack<type> implements MyIStack<type>{
    private final PStack<type> stack;

    public MyStack(){
        stack = ConsPStack.empty();
    }

    public MyStack(PStack<type> stack){
        this.stack = stack;
    }

    @Override
    public MyIStack<type> pop() throws MyException {
        if (stack.isEmpty())
            throw new EmptyStackException("cannot pop empty stack\n");
        PStack<type> newStack = stack.minus(0);
        return new MyStack<type>(newStack);
    }

    @Override
    public MyIStack<type> push(type value) {
        PStack<type> newStack = stack.plus(value);
        return new MyStack<type>(newStack);
    }

    @Override
    public MyIStack<type> insert(type value, int position) {
        PStack<type> newStack = stack.plus(position, value);
        return new MyStack<type>(newStack);
    }

    @Override
    public type top() {
        return stack.get(0);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        String toStr = "";
        for (type value: stack)
            toStr += value.toString() + "\n";
        return toStr;
    }
}
