package model.type;

import model.value.StringValue;
import model.value.Value;

public class StringType implements Type{

    public static final StringType STRING = new StringType();

    private StringType(){

    }

    @Override
    public String toString() {
        return "str";
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }
}
