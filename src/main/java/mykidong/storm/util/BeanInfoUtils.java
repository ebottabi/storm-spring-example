package mykidong.storm.util;

import java.util.List;

public class BeanInfoUtils {

    public static ClassInfoMap<String, ClassInfoSet> getClassInfoMap(Class clazz){
        return BeanInfoManager.INSTANCE.getClassInfoMap(clazz);
    }

    public static List<String> getFieldName(Class clazz){
        return BeanInfoManager.INSTANCE.getFieldName(clazz);
    }
}
