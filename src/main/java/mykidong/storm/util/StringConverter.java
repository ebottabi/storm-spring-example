package mykidong.storm.util;

import java.util.List;

public class StringConverter implements Converter {

    public Object[] convert(Class componentType, Object value) {
        if (String.class.isAssignableFrom(componentType)) {
            if(List.class.isAssignableFrom(value.getClass())){
                List list = (List) value;
                if(list.isEmpty() || list.size() < 1) return null;
                return convertArray((String[])list.toArray(new String[0]));
            }else{
                if (value.getClass().isArray()) {
                    if(CharSequence.class.isAssignableFrom(value.getClass().getComponentType())){
                        CharSequence[] temp = (CharSequence[])value;
                        String[] result = new String[temp.length];
                        for(int i=0; i<temp.length;i++){
                            result[i] = temp[i].toString();
                        }
                        return convertArray(result);
                    }

                    return convertArray((String[]) value);
                } else if(value instanceof CharSequence) {
                    return new String[] { ((CharSequence)value).toString() };
                } else {
                    return new String[] { (String) value };
                }
            }
        }
        return null;
    }

    private Object[] convertArray(String[] value) {
        Object[] returnObj = new Object[1];
        returnObj[0] = value;
        return returnObj;
    }
}
