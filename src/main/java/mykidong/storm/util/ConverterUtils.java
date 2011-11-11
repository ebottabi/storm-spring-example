package mykidong.storm.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ConverterUtils {

    private static Map<Class, Converter> converterMap ;

    private static ConverterUtils INSTANCE = new ConverterUtils();

    private ConverterUtils(){
        converterMap = new HashMap<Class, Converter>();
        converterMap.put(Integer.class, new IntegerConverter());
        converterMap.put(int.class, new PIntConverter());
        converterMap.put(Long.class, new LongConverter());
        converterMap.put(long.class, new PLongConverter());
        converterMap.put(String.class, new StringConverter());
        converterMap.put(BigDecimal.class, new BigDecimalConverter());
        converterMap.put(Boolean.class, new BooleanConverter());
        converterMap.put(boolean.class, new PBooleanConverter());
        converterMap.put(CharSequence.class, new StringConverter());
        //converterMap.put(CharSequence.class, new CharSequenceConverter());
    }

    public static ConverterUtils getInstance(){
        if(INSTANCE == null) new ConverterUtils();
        return INSTANCE;
    }

    public Object[] getValue(Class[] componentType, Object value){
        if(componentType == null || componentType.length <= 0){
            return null;
        }
        return getValue(componentType[0], value);
    }

    public Object[] getValue(Class componentType, Object value){
        if(componentType == null){
            return null;
        }else{
            if(converterMap.containsKey(componentType)){
                Converter convert = (Converter)converterMap.get(componentType);
                return convert.convert(componentType, value);
            }else{
                return new Object[]{value};
            }
        }
    }
}
