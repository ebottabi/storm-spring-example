package mykidong.storm.util;

import java.util.List;

public class BooleanConverter implements Converter{


    public Object[] convert(Class componentType, Object value){
        if(Boolean.class.isAssignableFrom(componentType)){
            if(List.class.isAssignableFrom(value.getClass())){
                List list = (List) value;
                if(list.isEmpty() || list.size() < 1) return null;
                return convertArray((Boolean[])list.toArray(new Boolean[0]));
            }else{
                if(value.getClass().isArray()){
                    return convertArray((Boolean[])value);
                }else{
                    return new Boolean[]{(Boolean)value};
                }
            }
        }
        return null;
    }

    private Object[] convertArray(Boolean[] value){
        Object[] returnObj = new Object[1];
        returnObj[0] = value;
        return returnObj;
    }
}





