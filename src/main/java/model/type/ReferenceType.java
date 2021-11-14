package model.type;

import model.value.ReferenceValue;
import model.value.Value;

public class ReferenceType implements Type {
    private Type innerType;

    public ReferenceType(Type innerType){
        this.innerType = innerType;
    }

    public Type getInnerType(){
        return innerType;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ReferenceType referenceType))
            return false;
        return innerType.equals(referenceType.getInnerType());
    }

    @Override
    public String toString() {
        return "ref(" + innerType.toString() + ")";
    }

    @Override
    public Value defaultValue() {
        return new ReferenceValue(0, innerType);
    }
}
