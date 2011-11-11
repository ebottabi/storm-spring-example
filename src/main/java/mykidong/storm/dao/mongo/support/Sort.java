package mykidong.storm.dao.mongo.support;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Sort{

    public final static int SORT_ASC = 1;
    public final static int SORT_DESC = -1;
    private final DBObject sortField;

    public Sort(){
        sortField = new BasicDBObject();
    }

    public static Sort asc(String sortField) {
        Sort sortMng = new Sort();
        sortMng.addAsc(sortField);
        return sortMng;
    }

    public static Sort desc(String sortField) {
        Sort sortMng = new Sort();
        sortMng.addDesc(sortField);
        return sortMng;
    }

    public Sort addAsc(String sortField) {
        this.sortField.put(sortField, SORT_ASC);
        return this;
    }

    public Sort addDesc(String sortField) {
        this.sortField.put(sortField, SORT_DESC);
        return this;
    }

    public DBObject getDbObject(){
        return sortField;
    }

}
