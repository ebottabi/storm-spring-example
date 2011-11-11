package mykidong.storm.util;

import java.util.List;

public class PIntConverter implements Converter{

    public Object[] convert(Class componentType, Object value){
        if(int.class.isAssignableFrom(componentType)){
            if(List.class.isAssignableFrom(value.getClass())){
                List list = (List) value;
                if(list.isEmpty() || list.size() < 1) return null;
                return convertArray((Integer[])list.toArray(new Integer[0]));
            }else{
                if(value.getClass().isArray()){
                    return convertArray((Integer[])value);
                }else{
                    return new Integer[]{(Integer)value};
                }
            }
        }
        return null;
    }

    private Object[] convertArray(Integer[] value){
        Object[] returnObj = new Object[1];
        int size = value.length;
        int[] tempObj = new int[size];
        for(int i=0; i<size;i++){
            tempObj[i]  = ((Integer)value[i]).intValue();
        }
        returnObj[0] = tempObj;
        return returnObj;
    }
}


