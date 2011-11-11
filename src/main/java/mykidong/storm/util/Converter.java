package mykidong.storm.util;

public interface Converter {

    public Object[] convert(Class componentType, Object value);

}
