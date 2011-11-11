package mykidong.storm.dao.mongo.support;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Field {
    private final static int INCLUDE_FIELD = 1;
    private final static int EXCLUDE_FIELD = 0;

    private final DBObject useField;
    private final int fieldType;

    private Field(int fieldType){
        this.fieldType = fieldType;
        useField = new BasicDBObject();
    }

    public DBObject getDbObject() {
        return useField;
    }

    public static Field include(String field){
        Field fld = new Field(INCLUDE_FIELD);
        fld.add(field);
        return fld;
    }

    public static Field exclude(String field){
        Field fld =  new Field(EXCLUDE_FIELD);
        fld.add(field);
        return fld;
    }

    public Field add(String field) {
        this.useField.put(field, this.fieldType);
        return this;
    }
}
