package model.value;

import model.type.BoolType;
import model.type.Type;

public final class BoolValue implements Value{
    public static final BoolValue TRUE = new BoolValue(true);
    public static final BoolValue FALSE = new BoolValue(false);
    private final boolean wrappedValue;

    private BoolValue(boolean wrappedVal){
        wrappedValue = wrappedVal;
    }
    public Boolean getWrappedValue(){
        return wrappedValue;
    }

    @Override
    public Type getType() {
        return BoolType.BOOL;
    }

    public static boolean fromTo(BoolValue input){
        return input.getWrappedValue();
    }

    public static BoolValue toFrom(boolean input){
        return input ? TRUE : FALSE;
    }

    @Override
    public String toString() {
        return Boolean.toString(wrappedValue);
    }

    @Override
    public boolean equals(Object object){
        if (!(object instanceof BoolValue boolValue))
            return false;
        return boolValue.getWrappedValue() == wrappedValue;
    }
}
