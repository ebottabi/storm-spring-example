package mykidong.storm.util;

import java.util.List;

public class PLongConverter implements Converter{

    public Object[] convert(Class componentType, Object value){
        if(long.class.isAssignableFrom(componentType)){
            if(List.class.isAssignableFrom(value.getClass())){
                List list = (List) value;
                if(list.isEmpty() || list.size() < 1) return null;
                return convertArray((Long[])list.toArray(new Long[0]));
            }else{
                if(value.getClass().isArray()){
                    return convertArray((Long[])value);
                }else{
                    return new Long[]{(Long)value};
                }
            }
        }
        return null;
    }

    private Object[] convertArray(Long[] value){
        Object[] returnObj = new Object[1];
        int size = value.length;
        long[] tempObj = new long[size];
        for(int i=0; i<size;i++){
            tempObj[i]  = ((Long)value[i]).longValue();
        }
        returnObj[0] = tempObj;
        return returnObj;
    }
}


