package mykidong.storm.dao.mongo.support;

import java.util.ArrayList;
import java.util.List;

import mykidong.storm.util.DevBsonUtils;

import org.springframework.util.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Query {

    private final static String COMMAND_NE = "$ne";
    private final static String COMMAND_LT = "$lt";
    private final static String COMMAND_LTE = "$lte";
    private final static String COMMAND_GT = "$gt";
    private final static String COMMAND_GTE = "$gte";
    private final static String COMMAND_NIN = "$nin";
    private final static String COMMAND_IN = "$in";
    private final static String COMMAND_OR = "$or";
    private final static String COMMAND_NOR = "$nor";
    private final static String COMMAND_ALL = "$all";
    private final static String COMMAND_MOD = "$mod";
    private final static String COMMAND_SIZE = "$size";
    private final static String COMMAND_EXISTS = "$exists";
    private final static String COMMAND_TYPE = "$type";
    private final static String COMMAND_NOT = "$not";
    private final static String COMMAND_REGEX = "$regex";
    private final static String COMMAND_OPTIONS = "$options";

    private String key;

    private DBObject queryDBObject;

    public void setQueryDBObject(DBObject queryDBObject) {
        this.queryDBObject = queryDBObject;
    }

    public Query(){
        queryDBObject = new BasicDBObject();
    }

    public Query(String key){
        queryDBObject = new BasicDBObject();
        this.key = key;
    }

    public Query(Object obj){
        queryDBObject = DevBsonUtils.convertBson(obj);
    }

    public static Query where(String key){
        return new Query(key);
    }

    public Query is(Object o) {
        queryDBObject.put(key, o);
        return this;
    }

    private void put(String cmd, Object o) {
        queryDBObject.put(key, new BasicDBObject(cmd, o));
    }

    public Query notEquals(Object o) {
        put(COMMAND_NE, o);
        return this;
    }

    public Query lessThan(Object o) {
        put(COMMAND_LT, o);
        return this;
    }

    public Query lessThanEquals(Object o) {
        put(COMMAND_LTE, o);
        return this;
    }

    public Query greaterThan(Object o) {
        put(COMMAND_GT, o);
        return this;
    }

    public Query greaterThanEquals(Object o) {
        put(COMMAND_GTE, o);
        return this;
    }

    public DBObject getDbObject(){
        return queryDBObject;
    }

    public Query between(Object obj1, Object obj2) {
        DBObject cond = new BasicDBObject();
        cond.put(COMMAND_LT, obj2);
        cond.put(COMMAND_GT, obj1);
        queryDBObject.put(key, cond);
        return this;
    }

    public Query betweenEquals(Object obj1, Object obj2) {
        DBObject cond = new BasicDBObject();
        cond.put(COMMAND_LTE, obj2);
        cond.put(COMMAND_GTE, obj1);
        queryDBObject.put(key, cond);
        return this;
    }

    public Query notIn(Object... o) {
        queryDBObject.put(key, new BasicDBObject(COMMAND_NIN, o));
        return this;
    }

    public Query in(Object... o) {
        queryDBObject.put(key, new BasicDBObject(COMMAND_IN, o));
        return this;
    }

    public Query all(Object... o) {
        queryDBObject.put(key, new BasicDBObject(COMMAND_ALL, o));
        return this;
    }

    public Query or(Query query) {
        List<DBObject> orList = new ArrayList<DBObject>();
        orList.add(query.getDbObject());
        orList.add(this.getDbObject());
        DBObject dbObject = new BasicDBObject();
        dbObject.put(COMMAND_OR, orList);
        Query itm = new Query();
        itm.setQueryDBObject(dbObject);
        return itm;
    }

    public Query nor(Query query) {
        List<DBObject> orList = new ArrayList<DBObject>();
        orList.add(query.getDbObject());
        orList.add(this.getDbObject());
        DBObject dbObject = new BasicDBObject();
        dbObject.put(COMMAND_NOR, orList);
        Query itm = new Query();
        itm.setQueryDBObject(dbObject);
        return itm;
    }

    public Query mod(Integer devidedByNum, Integer restNum) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(devidedByNum);
        list.add(restNum);
        queryDBObject.put(key, new BasicDBObject(COMMAND_MOD, list));
        return this;
    }


    public Query size(int size) {
        queryDBObject.put(key, new BasicDBObject(COMMAND_SIZE,size));
        return this;
    }

    public Query exists(boolean bln) {
        queryDBObject.put(key, new BasicDBObject(COMMAND_EXISTS, bln));
        return this;
    }

    public Query type(int type) {
        queryDBObject.put(key, new BasicDBObject(COMMAND_TYPE, type));
        return this;
    }

//    public Query notMod(Integer devidedByNum, Integer restNum) {
//        List<Integer> list = new ArrayList<Integer>();
//        list.add(devidedByNum);
//        list.add(restNum);
//        queryDBObject.put(key, new BasicDBObject(COMMAND_NOT, new BasicDBObject(COMMAND_MOD, list)));
//        return this;
//    }

    public Query notValue(Query query) {
        DBObject dbObj = query.getDbObject();
        Object val = dbObj.get(key);
        queryDBObject.put(key, new BasicDBObject(COMMAND_NOT, val));
        return this;
    }

    public Query regex(String reg) {
        queryDBObject.put(key, new BasicDBObject(COMMAND_REGEX, reg));
        return this;
    }

    public Query regex(String reg, String options) {
        queryDBObject.put(key, new BasicDBObject(COMMAND_REGEX, reg));
        if (StringUtils.hasText(options)) {
            queryDBObject.put(key, new BasicDBObject(COMMAND_OPTIONS, options));
        }
        return this;
    }

    public Query and(String key) {
        this.key = key;
        return this;
    }
}
