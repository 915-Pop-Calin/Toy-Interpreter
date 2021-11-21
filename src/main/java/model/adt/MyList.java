package model.adt;

import java.util.LinkedList;
import java.util.List;

import org.pcollections.*;

public final class MyList<type> implements MyIList<type>{
    private final List<type> linkedList;

    public MyList(){
        this.linkedList = new LinkedList<>();
    }

    public MyList(List<type> newList){
        this.linkedList = newList;
    }

    @Override
    public void add(type element) {
        linkedList.add(element);
    }

    @Override
    public String toString() {
        var toStr = "";
        for (type element : linkedList)
            toStr += element.toString() + "\n";
        return toStr;
    }
}