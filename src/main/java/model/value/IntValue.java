package model.value;

import model.type.IntType;
import model.type.Type;


public final class IntValue implements Value{
    private final int value;

    public IntValue(int val){
        value = val;
    }

    public int getValue(){
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public Type getType() {
        return IntType.INTEGER;
    }

    @Override
    public boolean equals(Object object){
        if (!(object instanceof IntValue intValue))
            return false;
        return intValue.getValue() == value;
    }

}
