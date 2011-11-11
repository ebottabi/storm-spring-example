package mykidong.storm.util;

import java.lang.reflect.Method;

public class ClassInfoSet {

    private String fieldName;
    private Method getMethod;
    private Method setMethod;
    private Class fieldType;

    public ClassInfoSet(String fieldName, Method getMethod, Method setMethod, Class fieldType){
        this.fieldName = fieldName;
        this.getMethod = getMethod;
        this.setMethod = setMethod;
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    public Method getGetMethod() {
        return getMethod;
    }
    public void setGetMethod(Method getMethod) {
        this.getMethod = getMethod;
    }
    public Method getSetMethod() {
        return setMethod;
    }
    public void setSetMethod(Method setMethod) {
        this.setMethod = setMethod;
    }
    public Class getFieldType() {
        return fieldType;
    }
    public void setFieldType(Class fieldType) {
        this.fieldType = fieldType;
    }

}
