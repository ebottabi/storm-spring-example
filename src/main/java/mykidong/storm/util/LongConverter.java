package mykidong.storm.util;

import java.util.List;

public class LongConverter implements Converter{

     public Object[] convert(Class componentType, Object value){
            if(Long.class.isAssignableFrom(componentType)){
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
            returnObj[0] = value;
            return returnObj;
        }
}


