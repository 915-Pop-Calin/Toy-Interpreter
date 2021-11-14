package model.value;

import model.type.ReferenceType;
import model.type.Type;

public final class ReferenceValue implements Value{
    private final int address;
    private final Type locationType;

    public ReferenceValue(int address, Type locationType){
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress(){
        return address;
    }

    public Type getLocationType(){
        return locationType;
    }

    public ReferenceValue setAddress(int address){
        return new ReferenceValue(address, locationType);
    }

    @Override
    public Type getType() {
        return new ReferenceType(locationType);
    }

    @Override
    public String toString() {
        return "(" + String.valueOf(address) + "," + locationType.toString() + ")";
    }
}
