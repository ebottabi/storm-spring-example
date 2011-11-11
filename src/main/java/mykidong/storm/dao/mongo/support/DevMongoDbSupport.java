package mykidong.storm.dao.mongo.support;

import static mykidong.storm.dao.mongo.support.Field.include;

import java.util.ArrayList;
import java.util.List;

import mykidong.storm.util.Base64Utils;
import mykidong.storm.util.DevBsonUtils;
import mykidong.storm.util.DoNotAllowedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;


public abstract class DevMongoDbSupport implements InitializingBean {

    private static Logger log = LoggerFactory.getLogger(DevMongoDbSupport.class);

    private String dbName;

    private Mongo mongo;

    private DB db;

    private String user;

    private String password;

    private DBCollection dbCollection;

    private String dbCollectionName;

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DBCollection getDbCollection() {
        return dbCollection;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void setMongo(Mongo mongo) {
        this.mongo = mongo;
        //mongo.
    }

    public void setDbCollectionName(String dbCollectionName) {
        this.dbCollectionName = dbCollectionName;
    }


    public Mongo getMongo() {
        return mongo;
    }

    public DB getDb() {
        return db;
    }

    public void setDb(DB db) {
        this.db = db;
    }

    public void setDbCollection(DBCollection dbCollection) {
        this.dbCollection = dbCollection;
    }

    public void afterPropertiesSet() throws Exception {
        init();
    }

    protected void init() throws Exception{
        db = mongo.getDB(this.dbName);
        if(this.user != null && this.password != null && !db.isAuthenticated()){
            boolean auth = db.authenticate(Base64Utils.base64decoding(this.user), Base64Utils.base64decoding(this.password).toCharArray());
            if(log.isDebugEnabled()){
                log.debug("@@@@@@@@@@@@@@@@@@@@");
                log.debug("@@@@@@@@@@@@@@@@@@@@");
                log.debug("@@@@@@@@@@@@@@@@@@@@");
                log.debug(this.dbName);
                log.debug(Base64Utils.base64decoding(this.user));
                log.debug(Base64Utils.base64decoding(this.password));
                log.debug("auth : "+auth);
                log.debug("@@@@@@@@@@@@@@@@@@@@");
                log.debug("@@@@@@@@@@@@@@@@@@@@");
                log.debug("@@@@@@@@@@@@@@@@@@@@");
            }
        }
        dbCollection = db.getCollection(this.dbCollectionName);
    }

    protected WriteResult insertCommon(DBObject item){
        return dbCollection.insert(item);
    }

    public WriteResult insertCommon(Object itm) {
        return insertCommon(DevBsonUtils.convertBson(itm));
    }

    protected WriteResult insertCommon(DBObject[] itmList){
        return dbCollection.insert(itmList);
    }

    public WriteResult insertCommon(Object[] itmList) {
        DBObject[] dbObjectList = new BasicDBObject[itmList.length];
        for(int i=0;i<itmList.length;i++){
            dbObjectList[i] = DevBsonUtils.convertBson(itmList[i]);
        }
        return insertCommon(dbObjectList);
    }

    public <T> WriteResult insertCommon(List<T> itemList) {
        DBObject[] dbObjectList = new BasicDBObject[itemList.size()];
        for(int i=0; i<itemList.size();i++){
            dbObjectList[i] = DevBsonUtils.convertBson(itemList.get(i));
        }
        return insertCommon(dbObjectList);
    }

    protected WriteResult removeCommon(DBObject item){
        return dbCollection.remove(item);
    }

    public WriteResult removeCommon(Object itm) {
        return removeCommon(DevBsonUtils.convertBson(itm));
    }

    public WriteResult removeCommon(Query query) {
        return removeCommon(query.getDbObject());
    }

    protected <T> T findOneCommon(DBObject dbObject, Class<T> clazz){
        if(log.isDebugEnabled()){
            if(dbObject != null)
                log.debug("findOneCommon query : " +dbObject.toString());
        }
        DBObject dbObj = getDbCollection().findOne(dbObject);
        if(dbObj == null) return null;
        return DevBsonUtils.dbObjectToBean(dbObj, clazz);
    }

    protected <T> T findOneCommon(QueryMng cond, Class<T> clazz){
        cond.setLimit(1); // 무조건 고정 시킴.
        List<T> resultList = listCommon(cond, clazz);
        if(resultList.isEmpty()) return null;
        else if(resultList.size() == 0) return null;
        else return resultList.get(0);
//        DBObject dbObj = getDbCollection().findOne(cond.getQuery().getDbObject(), cond.getField().getDbObject());
//        if(dbObj == null) return null;
//        return DevBsonUtils.dbObjectToBean(dbObj, clazz);
    }

    protected <T> T findOneCommon(Query query, Class<T> clazz){
        if(log.isDebugEnabled()){
            if(query != null && query.getDbObject() != null)
                log.debug("findOneCommon query : " +query.getDbObject().toString());
        }
        if(query == null || query.getDbObject() == null) return null;
        DBObject dbObj = getDbCollection().findOne(query.getDbObject());
        if(dbObj == null) return null;
        return DevBsonUtils.dbObjectToBean(dbObj, clazz);
    }

    protected <T> T findOneCommon(Object obj, Class<T> clazz){
        Query query = new Query(obj);
        return findOneCommon(query, clazz);
    }

    protected WriteResult updateCommon(Query query, UpdateSet updateSet){
      return dbCollection.update(query.getDbObject(), updateSet.getDbObject());
    }

    protected <T> List<T> listCommon(QueryMng cond, Class<T> clazz){
        if(log.isDebugEnabled()){
            if(cond != null && cond.getQuery() != null && cond.getQuery().getDbObject() != null){
                log.debug("listCommon query : " +cond.getQuery().getDbObject().toString());
            }
            if(cond.getField() != null){
                log.debug("listCommon field : " + ((cond.getField() != null)? cond.getField().getDbObject() : null));
            }
            if(cond.getSort() != null){
                log.debug("listCommon sort : " + cond.getSort().getDbObject().toString());
            }
            if(cond.getLimit() != null){
                log.debug("listCommon limit : " + Integer.toString(cond.getLimit().intValue()));
            }
            if(cond.getSkip() != null){
                log.debug("listCommon skip : " + cond.getSkip().intValue());
            }
        }

        DBCursor cursor = getDbCollection().find(
                (cond.getQuery() != null)? cond.getQuery().getDbObject():null
                , (cond.getField() != null)? cond.getField().getDbObject():null
                );

        if(cursor == null) return null;

        if(cond.getSort() != null){
            cursor.sort(cond.getSort().getDbObject());
        }
        if(cond.getLimit() != null){
            cursor.limit(cond.getLimit().intValue());
        }
        if(cond.getSkip() != null){
            cursor.skip(cond.getSkip().intValue());
        }

        List<T> resultList = new ArrayList<T>();

        for(int i=0;cursor.hasNext();i++){
            resultList.add(DevBsonUtils.dbObjectToBean(cursor.next(), clazz));
        }
        return resultList;
    }

    protected <T> List<T> listCommon(Query where, Class<T> clazz){
        QueryMng cond = new QueryMng();
        cond.setQuery(where);
        return listCommon(cond, clazz);
    }

    protected <T> List<T> listCommon(Object obj, Class<T> clazz){
        QueryMng cond = new QueryMng();
        Query where = new Query(obj);
        cond.setQuery(where);
        return listCommon(cond, clazz);
    }

    public int maxMinCommon(Query query, String fieldName, int sortingType) {
        if(fieldName.contains(".")){
            throw new DoNotAllowedException("key에 '.'은 허용하지 않습니다.");
        }
        if(query == null) query = new Query();

        DBObject sort = new BasicDBObject(fieldName, sortingType);
        if(log.isDebugEnabled()){
            if(query != null && query.getDbObject() != null)
                log.debug("maxMinCommon query : " +query.toString());
            if(sort != null)
                log.debug("maxMinCommon sort : " + sort.toString());
        }

        DBCursor cursor = getDbCollection().find(query.getDbObject(), include(fieldName).getDbObject());
        if(cursor == null) return 0;
        cursor.sort(sort);
        cursor.limit(1);
        try{
            return ((Integer)((DBObject)cursor.next()).get(fieldName)).intValue();
        }catch(Exception e){
            return 0;
        }
    }

    public int maxCommon(String fieldName) {
        return maxMinCommon(null, fieldName, -1);
    }

    public int maxCommon(Query query, String fieldName) {
        return maxMinCommon(query, fieldName, -1);
    }

    public int minCommon(String fieldName) {
        return maxMinCommon(null, fieldName, 1);
    }

    public int minCommon(Query query, String fieldName) {
        return maxMinCommon(query, fieldName, 1);
    }

    protected <T> List<T> distinctCommon(String key, DBObject cond, Class<T> clazz){
        List<DBObject> list = null;
        if(cond != null)
            list = getDbCollection().distinct(key, cond);
        else
            list = getDbCollection().distinct(key);

        if(list == null || list.isEmpty()) return null;

        List<T> resultList = new ArrayList<T>();

        for(int i=0;i< resultList.size();i++){
            resultList.add(DevBsonUtils.dbObjectToBean(list.get(i), clazz));
        }
        return resultList;
    }

    public long countCommon() {
        return getDbCollection().find().count();
    }

    protected long countCommon(Query query){
      return getDbCollection().find(
              (query != null)? query.getDbObject():null
              ).count();
    }

}
