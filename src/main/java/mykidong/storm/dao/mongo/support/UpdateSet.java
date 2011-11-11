package mykidong.storm.dao.mongo.support;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import mykidong.storm.util.BaseObject;
import mykidong.storm.util.DevBsonUtils;

public class UpdateSet{

    private final static String COMMAND_SET = "$set";
    private final static String COMMAND_UNSET = "$unset";
    private final static String COMMAND_RENAME = "$rename";
    private final static String COMMAND_INC = "$inc";

    private DBObject updateMap ;

    public UpdateSet() {
        updateMap = new BasicDBObject();
    }

    public static UpdateSet update(String key, Object value) {
        return new UpdateSet().set(key, value);
    }

    public static UpdateSet update(Object value) {
        return new UpdateSet().set(value);
    }

    public UpdateSet set(String key, Object obj){
        addUpdateSet(COMMAND_SET, key, obj);
        return this;
    }

    public UpdateSet set(Object obj){
        addUpdateSet(COMMAND_SET, obj);
        return this;
    }

    public UpdateSet unset(String key) {
        addUpdateSet(COMMAND_UNSET, key, 1);
        return this;
    }

    public UpdateSet rename(String oldName, String newName) {
        addUpdateSet(COMMAND_RENAME, oldName, newName);
        return this;
    }

    public static UpdateSet all(Object obj){
        return new UpdateSet().set(obj);
    }

    public UpdateSet inc(String key, Object obj){
        addUpdateSet(COMMAND_INC, key, obj);
        return this;
    }

    public DBObject getDbObject(){
        return updateMap;
    }

    @SuppressWarnings("unchecked")
    protected void addUpdateSet(String operator, String key, Object value) {
        DBObject dbObject = (DBObject)this.updateMap.get(operator);
        if (dbObject == null) {
            dbObject = new BasicDBObject(key, value);
            this.updateMap.put(operator, dbObject);
        } else {
            dbObject.put(key, value);
        }
    }

    protected void addUpdateSet(String operator, Object value) {
        DBObject dbObject = (DBObject)this.updateMap.get(operator);
        if (dbObject == null) {
            dbObject = DevBsonUtils.convertBson(value);
            this.updateMap.put(operator, dbObject);
        } else {
            dbObject.putAll(DevBsonUtils.convertBson(value));
        }
    }

}