package mykidong.storm.util;

import java.util.List;

public class PBooleanConverter implements Converter{

    public Object[] convert(Class componentType, Object value){
        if(boolean.class.isAssignableFrom(componentType)){
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
        int size = value.length;
        boolean[] tempObj = new boolean[size];

        for(int i=0; i<size;i++){
            tempObj[i]  = ((Boolean)value[i]).booleanValue();
        }
        returnObj[0] = tempObj;
        return returnObj;
    }
}





