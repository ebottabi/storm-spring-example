package mykidong.storm.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;

public class BeanInfoManager {

    public final static BeanInfoManager INSTANCE = new BeanInfoManager();

    private Map<String, ClassInfo> classInfoMap = new HashMap<String, ClassInfo>();

    private BeanInfoManager(){
    }

    public ClassInfoMap<String, ClassInfoSet> getClassInfoMap(Class clazz) {
        ClassInfo classInfo = describeClass(clazz);
        return classInfo.getClassInfoMap();
    }

    public List<String> getFieldName(Class clazz) {
        ClassInfoMap<String, ClassInfoSet> classInfo = getClassInfoMap(clazz);
        Iterator entries = classInfo.keySet().iterator();
        List<String> list = new ArrayList<String>();
        while(entries.hasNext()) {
            list.add((String)entries.next());
        }
        return list;
    }

    private ClassInfo describeClass(Class clazz) {
        String className = clazz.getName();
        if(classInfoMap.containsKey(className)){
            return classInfoMap.get(className);
        }
        ClassInfo classInfo = new ClassInfo();

        PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(clazz);
        for (int i = 0; i < descriptors.length; ++i) {
            String name = descriptors[i].getName();
            if (PropertyUtils.getReadMethod(descriptors[i]) != null
                    && PropertyUtils.getWriteMethod(descriptors[i]) != null) {
                Field field = null;
                try {
                    field = clazz.getDeclaredField(name);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                IgnoreField ignore = field.getAnnotation(IgnoreField.class);
                if(ignore == null || (ignore != null && !ignore.isIgnore())){
                    PropertyMapper mapper = field.getAnnotation(PropertyMapper.class);
                    if(mapper != null){
                        name = mapper.name();
                    }
                    classInfo.putClassInfoSet(name
                            , MethodUtils.getAccessibleMethod(clazz, descriptors[i].getReadMethod())
                            , MethodUtils.getAccessibleMethod(clazz, descriptors[i].getWriteMethod())
                            , descriptors[i].getPropertyType()
                            );
                }
            }
        }
        classInfoMap.put(className, classInfo);
        return classInfo;
    }

}
