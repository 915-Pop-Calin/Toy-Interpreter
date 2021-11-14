package model.value;

import model.type.StringType;
import model.type.Type;

public final class StringValue implements Value{
    private final String value;

    public StringValue(String string){
        value = string;
    }

    public String getValue(){
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public Type getType() {
        return StringType.STRING;
    }

    @Override
    public boolean equals(Object object){
        if (!(object instanceof StringValue stringValue))
            return false;
        return stringValue.getValue().equals(value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
