package mykidong.storm.util;

import java.math.BigDecimal;
import java.util.List;

public class BigDecimalConverter implements Converter{

    public Object[] convert(Class componentType, Object value){
        if(BigDecimal.class.isAssignableFrom(componentType)){
            if(value == null) return null;
            if(List.class.isAssignableFrom(value.getClass())){
                List list = (List) value;
                if(list.isEmpty() || list.size() < 1) return null;
                return convertArray((Object[])list.toArray(new Object[0]));

            }else{
                if(value.getClass().isArray() ){ //|| value instanceof List
                    return convertArray((Object[])value);
                }else{
                    if(value instanceof BigDecimal){
                        return new BigDecimal[]{(BigDecimal)value};
                    }else if(value instanceof Integer){
                        return new BigDecimal[]{new BigDecimal(((Integer)value).longValue())};
                    }else if(value instanceof Long){
                        return new BigDecimal[]{new BigDecimal(((Long)value).longValue())};
                    }else if(value instanceof Float){
                        return new BigDecimal[]{new BigDecimal(((Float)value).floatValue())};
                    }else if(value instanceof Double){
                        return new BigDecimal[]{new BigDecimal(((Double)value).doubleValue())};
                    }
                }
            }
        }
        return null;
    }

    private Object[] convertArray(Object[] value) {
        if(BigDecimal.class.isAssignableFrom(value.getClass().getComponentType())){
            BigDecimal[] its = (BigDecimal[])value;
            Object[] returnObj = new Object[1];
            returnObj[0] = value;
            return returnObj;
        }else if(Integer.class.isAssignableFrom(value.getClass().getComponentType())){
            Integer[] its = (Integer[])value;
            Object[] returnObj = new Object[1];
            BigDecimal[] bd = new BigDecimal[its.length];
            for(int i=0; i< its.length; i++){
                bd[i] = new BigDecimal(((Integer)value[i]).longValue());
            }
            returnObj[0] = bd;
            return returnObj;
        }else if(Long.class.isAssignableFrom(value.getClass().getComponentType())){
            Long[] its = (Long[])value;
            Object[] returnObj = new Object[1];
            BigDecimal[] bd = new BigDecimal[its.length];
            for(int i=0; i< its.length; i++){
                bd[i] = new BigDecimal(((Long)value[i]).longValue());
            }
            returnObj[0] = bd;
            return returnObj;
        }else if(Float.class.isAssignableFrom(value.getClass().getComponentType())){
            Float[] its = (Float[])value;
            Object[] returnObj = new Object[1];
            BigDecimal[] bd = new BigDecimal[its.length];
            for(int i=0; i< its.length; i++){
                bd[i] = new BigDecimal(((Float)value[i]).floatValue());
            }
            returnObj[0] = bd;
            return returnObj;
        }else if(Double.class.isAssignableFrom(value.getClass().getComponentType())){
            Double[] its = (Double[])value;
            Object[] returnObj = new Object[1];
            BigDecimal[] bd = new BigDecimal[its.length];
            for(int i=0; i< its.length; i++){
                bd[i] = new BigDecimal(((Double)value[i]).doubleValue());
            }
            returnObj[0] = bd;
            return returnObj;
        }
        return null;
    }
}