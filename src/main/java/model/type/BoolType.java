package model.type;

import model.value.BoolValue;
import model.value.Value;

public final class BoolType implements Type{
    public static final BoolType BOOL = new BoolType();

    private BoolType(){

    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public Value defaultValue() {
        return BoolValue.FALSE;
    }
}
