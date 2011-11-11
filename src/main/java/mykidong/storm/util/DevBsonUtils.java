package mykidong.storm.util;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;


import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class DevBsonUtils {

    private static final Object EMPTY_OBJECT_ARRAY[] = new Object[0];

    @SuppressWarnings("unchecked")
    public static <T> T dbObjectToBean(DBObject dbObject, Class<T> clazz) {
        if(dbObject == null) return null;
        return (T)mapToBean(dbObject.toMap(), clazz);
    }

    public static <T, M extends Map<String, ?>> T mapToBean(M properties, Class<T> clazz) {
        // null 체크
        if (properties == null || properties.isEmpty()) {
            return null;
        }
        // 객체 생성
        T bean = null;
        try {
            bean = clazz.newInstance();
        } catch (Exception e1) {
            throw new DevBsonException("fail to create an Object", e1);
        }

        ClassInfoMap<String, ClassInfoSet> classInfo = BeanInfoUtils.getClassInfoMap(bean.getClass());
        for(Iterator<String> names = properties.keySet().iterator(); names.hasNext();) {
            String name = names.next();
            if(name != null) {
                Object value = properties.get(name);
                try {
                    if (value != null) {
                        ClassInfoSet classInfoSet = classInfo.get(name);
                        if(classInfoSet == null) continue;
                        Method method = classInfoSet.getSetMethod();
                        // list array 값인 경우
                        //System.out.println(name +":"+ value.getClass()+" : "+value.toString());
                        if (value instanceof BasicDBList) {
                            Class subClass = (getPropertyType(method, bean, name)).getComponentType();
                            BasicDBList bsonArray = (BasicDBList)value;
                            int size = bsonArray.size();
                            // 배열인 primitive type 처리
                            if(subClass.isPrimitive()){
                                invoke(method, bean, bsonArray);
                            }else{
                               Object[] tempObj = null;
                               try {
                                   tempObj = (Object [])Array.newInstance(subClass, size);
                               } catch (Exception e) {
                                   throw new DevBsonException("fail to create an Object", e);
                               }
                               for(int i=0; i<size;i++){
                                   Object bsonArrayValue = bsonArray.get(i);
                                   if(DBObject.class.isAssignableFrom(bsonArrayValue.getClass())){
                                       DBObject subMap = (DBObject)bsonArrayValue;
                                       tempObj[i]  = mapToBean(subMap.toMap(),subClass);
                                   }else if(BigDecimal.class.isAssignableFrom(subClass)){
                                       if(bsonArrayValue instanceof Integer)
                                           tempObj[i]  = BigDecimal.valueOf(((Integer)bsonArrayValue).longValue());
                                       else if(bsonArrayValue instanceof Long)
                                           tempObj[i]  = BigDecimal.valueOf(((Long)bsonArrayValue).longValue());
                                       else if(bsonArrayValue instanceof Float)
                                           tempObj[i]  = new BigDecimal(((Float)bsonArrayValue).floatValue());
                                       else if(bsonArrayValue instanceof Double)
                                           tempObj[i]  = new BigDecimal(((Double)bsonArrayValue).doubleValue());
                                   }else if(String.class.isAssignableFrom(subClass)
                                           || Boolean.class.isAssignableFrom(subClass)
                                           ){
                                       tempObj[i] = bsonArrayValue;
                                   }
                               }
                               invoke(method, bean, tempObj);
                           }
                        }else if(value instanceof DBObject){
                            Map valueMap =  ((DBObject)value).toMap();
                            if(valueMap.isEmpty()) continue;
                            Object obj = mapToBean(valueMap, getPropertyType(method, bean, name));
                            invoke(method, bean, obj);
                        } else {
                            invoke(method, bean, value);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new DevBsonException("Error while setting property=" + name + " type" + clazz, e);
                }
            }
        }

        return bean;
    }

    public static DBObject convertBson(Object bean) {
        if (bean == null) {
            return new BasicDBObject();
        }

        DBObject description = new BasicDBObject();

        ClassInfoMap<String, ClassInfoSet> classInfo = BeanInfoUtils.getClassInfoMap(bean.getClass());
        Iterator entries = classInfo.keySet().iterator();
        while(entries.hasNext()) {
            String name = (String)entries.next();
            ClassInfoSet classInfoSet = classInfo.get(name);
            Method invokeMethod = classInfoSet.getGetMethod();
            Class type = invokeMethod.getReturnType();

            if(invokeMethod != null){
                Object value = null;
                try {
                    value = invokeMethod.invoke(bean, EMPTY_OBJECT_ARRAY);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(value == null) continue;
                if( invokeMethod.getClass().isPrimitive()
                        || BigDecimal.class.isAssignableFrom(type)
                      || String.class.isAssignableFrom(type)
                      || Boolean.class.isAssignableFrom(type)
                      || Date.class.isAssignableFrom(type)
                      || isNumber(type)
                      || Character.class.isAssignableFrom(type)
                      || ObjectId.class.isAssignableFrom(type)
                ){
                }else if(!type.isArray()){
                    value = convertBson(value);
                }else{
                    value=toCollection((Object[])value);
                }
                if (value != null){
                    description.put(name, value);
                }
            }
        }
        return description;
    }

    private static boolean isNumber(Class clazz)
    {
        return clazz != null && (Byte.TYPE.isAssignableFrom(clazz) || Short.TYPE.isAssignableFrom(clazz) || Integer.TYPE.isAssignableFrom(clazz) || Long.TYPE.isAssignableFrom(clazz) || Float.TYPE.isAssignableFrom(clazz) || Double.TYPE.isAssignableFrom(clazz) || java.lang.Number.class.isAssignableFrom(clazz));
    }

    private static Class getPropertyType(Method method, Object bean, String name){
        Class[] parameterTypes = method.getParameterTypes();
        if(parameterTypes != null && parameterTypes.length > 0){
            return parameterTypes[0];
        }
        return null;
    }

    private static void invoke(Method method, Object bean, Object value) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
        if(method != null){
            Class[] parameterTypes = method.getParameterTypes();
            if(parameterTypes != null && parameterTypes.length > 0){
                Object[] paramValue = null;
                if(value.getClass().isArray() || value instanceof BasicDBList){
                    paramValue = ConverterUtils.getInstance().getValue(parameterTypes[0].getComponentType(), value);
                }else{
                    paramValue = ConverterUtils.getInstance().getValue(parameterTypes, value);
                }
                if(paramValue != null)
                    method.invoke(bean, paramValue);
            }
        }
    }

    public static BasicDBList toCollection( Object[] obj) {
        if(obj == null) return null;
        BasicDBList collection = new BasicDBList();

        int size = obj.length;
        for( int i = 0; i < size; i++ ){
            Class type = obj[i].getClass();
            if( obj[i].getClass().isPrimitive()
                    || BigDecimal.class.isAssignableFrom(type)
                  || String.class.isAssignableFrom(type)
                  || Boolean.class.isAssignableFrom(type)
                  || Date.class.isAssignableFrom(type)
                  || isNumber(type)
                  || Character.class.isAssignableFrom(type)
                  || ObjectId.class.isAssignableFrom(type)
            ){
                collection.add(obj[i]);
            }else{
                collection.add(convertBson(obj[i]));
            }
        }
        return collection;
     }

    //private static Map<String, ClassInfo> CLASS_NAME_TYPE_MAP = new HashMap<String, ClassInfo>();
//    private static ClassInfoMap<String, ClassInfoSet> getClassInfoMap(Class clazz) {
//        ClassInfo classInfo = describeClass(clazz);
//        return classInfo.getClassInfoMap();
//    }
//    private static ClassInfo describeClass(Class clazz) {
//        String className = clazz.getName();
//        if(CLASS_NAME_TYPE_MAP.containsKey(className)){
//            return CLASS_NAME_TYPE_MAP.get(className);
//        }
//        ClassInfo classInfo = new ClassInfo();
//        PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(clazz);
//        for (int i = 0; i < descriptors.length; ++i) {
//            String name = descriptors[i].getName();
//            if (PropertyUtils.getReadMethod(descriptors[i]) != null
//                    && PropertyUtils.getWriteMethod(descriptors[i]) != null) {
//                classInfo.putClassInfoSet(name
//                        , MethodUtils.getAccessibleMethod(clazz, descriptors[i].getReadMethod())
//                        , MethodUtils.getAccessibleMethod(clazz, descriptors[i].getWriteMethod())
//                        , descriptors[i].getPropertyType()
//                        );
//            }
//        }
//        CLASS_NAME_TYPE_MAP.put(className, classInfo);
//        return classInfo;
//    }


//    public static void main(String[] agrs){
//        DevJsonUtils test = new DevJsonUtils();
//        Board board = new Board();
//        board.setTitle("제목입니다.5");
//        board.setContent("내용입니다.5");
//        board.setRegDate(new Date());
//        BoardAttachInfo attach = new BoardAttachInfo();
//        attach.setAttachFileName("attach1.log");
//        attach.setAttachFileSize(123);
//        board.setAttach(attach);
//
//        BoardAttachInfo[] attachList = new BoardAttachInfo[2];
//        BoardAttachInfo attach1 = new BoardAttachInfo();
//        attach1.setAttachFileName("attach1.log");
//        attach1.setAttachFileSize(123);
//        attachList[0] = attach1;
//
//        BoardAttachInfo attach2 = new BoardAttachInfo();
//        attach2.setAttachFileName("attach1.log");
//        attach2.setAttachFileSize(123);
//        attachList[1] = attach2;
//
//        board.setAttachList(attachList);
////        test.insert(board);
////        System.out.println("##########################");
//        BasicDBObject map = test.describe(board);
//        System.out.println(map.toString());
//    }
//    public void insert(Board board) {
//        BasicDBObject itm = new BasicDBObject();
//        itm.put("title", board.getTitle());
//        itm.put("content", board.getContent());
//        itm.put("regDate", board.getRegDate());
//        BasicDBObject attach = new BasicDBObject();
//        attach.put("attachFileName", board.getAttach().getAttachFileName());
//        attach.put("attachFileSize", board.getAttach().getAttachFileSize());
//        itm.put("attach", attach);
//
//        BasicDBList attachList = new BasicDBList();
//        BoardAttachInfo[] oAttachList = board.getAttachList();
//        for(BoardAttachInfo attachInfo : oAttachList){
//            BasicDBObject attach1 = new BasicDBObject();
//            attach1.put("attachFileName", attachInfo.getAttachFileName());
//            attach1.put("attachFileSize", attachInfo.getAttachFileSize());
//            attachList.add(attach1);
//        }
//        itm.put("attachList", attachList);
//
//        System.out.println(itm.toString());
//    }

}
