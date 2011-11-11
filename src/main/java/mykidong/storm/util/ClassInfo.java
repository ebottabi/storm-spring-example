package mykidong.storm.util;

import java.lang.reflect.Method;

public class ClassInfo {

    private ClassInfoMap<String, ClassInfoSet> classInfoMap;

    public void putClassInfoSet(String fieldName
            ,Method getter
            ,Method setter
            ,Class clazz){
        ClassInfoSet classInfoSet = new ClassInfoSet(fieldName, getter, setter, clazz);
        classInfoMap.put(fieldName, classInfoSet);
    }

    public ClassInfo(){
        classInfoMap= new ClassInfoMap<String, ClassInfoSet>();
    }

    public ClassInfoMap<String, ClassInfoSet> getClassInfoMap() {
        return classInfoMap;
    }
}
