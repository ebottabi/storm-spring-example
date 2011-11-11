package mykidong.storm.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ClassInfoMap<K,V> {

    private Map<K, V> infoMap;

    public void put(K fieldName, V method){
        infoMap.put(fieldName, method);
    }

    public ClassInfoMap(){
        infoMap= new HashMap<K, V>();
    }

    public V get(K fieldName) {
        return infoMap.get(fieldName);
    }

    public int size() {
        return infoMap.size();
    }

    public Set keySet() {
        return infoMap.keySet();
    }




}
