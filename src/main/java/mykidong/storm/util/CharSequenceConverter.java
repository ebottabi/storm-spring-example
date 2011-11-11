package mykidong.storm.util;

import java.util.List;

public class CharSequenceConverter implements Converter {

    public Object[] convert(Class componentType, Object value) {
        if (CharSequence.class.isAssignableFrom(componentType)) {
            if(List.class.isAssignableFrom(value.getClass())){
                List list = (List) value;
                if(list.isEmpty() || list.size() < 1) return null;
                return convertArray((CharSequence[])list.toArray(new CharSequence[0]));
            }else{
                if (value.getClass().isArray()) {
                	if(CharSequence.class.isAssignableFrom(value.getClass().getComponentType())){
                    	CharSequence[] temp = (CharSequence[])value;
                    	CharSequence[] result = new CharSequence[temp.length];
                        for(int i=0; i<temp.length;i++){
                        	result[i] = temp[i].toString();
                        }
                        return convertArray(result);
                	}else if(String.class.isAssignableFrom(value.getClass().getComponentType())){
                	}

                    return convertArray((CharSequence[]) value);
                } else if(value instanceof CharSequence) {
                    return new CharSequence[] { ((CharSequence)value).toString() };
                } else {
                	return new CharSequence[] { (CharSequence) value };
                }
            }
        }
        return null;
    }

    private Object[] convertArray(CharSequence[] value) {
        Object[] returnObj = new Object[1];
        returnObj[0] = value;
        return returnObj;
    }
}
