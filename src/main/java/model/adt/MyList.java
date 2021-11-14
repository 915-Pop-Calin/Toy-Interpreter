package model.adt;

import java.util.LinkedList;
import org.pcollections.*;

//public final class MyList<type> implements MyIList<type>{
//    private final LinkedList<type> linkedList;
//
//    public MyList(){
//        this.linkedList = new LinkedList<type>();
//    }
//
//    public MyList(LinkedList<type> linkedList){
//        this.linkedList = linkedList;
//    }
//
//    @Override
//    public MyIList<type> add(type element) {
//        LinkedList<type> newLinkedList = (LinkedList<type>) linkedList.clone();
//        newLinkedList.add(element);
//        return new MyList<type>(newLinkedList);
//    }
//
//    @Override
//    public String toString() {
//        return linkedList.toString();
//    }
//}

public final class MyList<type> implements MyIList<type>{
    private final PStack<type> linkedList;

    public MyList(){
        this.linkedList = ConsPStack.empty();
    }

    public MyList(PStack<type> linkedList){
        this.linkedList = linkedList;
    }

    @Override
    public MyIList<type> add(type element) {
        int position = linkedList.size();
        PStack<type> newLinkedList = linkedList.plus(position, element);
        return new MyList<type>(newLinkedList);
    }

    @Override
    public String toString() {
        var toStr = "";
        for (type element : linkedList)
            toStr += element.toString() + "\n";
        return toStr;
    }
}