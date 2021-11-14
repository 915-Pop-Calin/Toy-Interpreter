package model.type;

import model.value.IntValue;
import model.value.Value;

public final class IntType implements Type{
    public static final IntType INTEGER = new IntType();

    private IntType(){

    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }
}
