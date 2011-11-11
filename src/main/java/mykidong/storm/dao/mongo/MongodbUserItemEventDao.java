package mykidong.storm.dao.mongo;

import static mykidong.storm.dao.mongo.support.Query.where;
import static mykidong.storm.dao.mongo.support.QueryMng.page;
import static mykidong.storm.dao.mongo.support.Sort.desc;

import java.util.List;

import mykidong.storm.api.dao.UserItemEventDao;
import mykidong.storm.dao.mongo.support.DevMongoDbSupport;
import mykidong.storm.dao.mongo.support.Query;
import mykidong.storm.domain.context.ItemViewEvent;
import mykidong.storm.util.DevDaoException;

import org.springframework.beans.factory.InitializingBean;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.WriteResult;

public class MongodbUserItemEventDao extends DevMongoDbSupport implements UserItemEventDao, InitializingBean{

	@Override
	public void afterPropertiesSet() throws Exception {
		init();
		DBCollection itemViewEventCollection = getDbCollection();
		itemViewEventCollection.ensureIndex(new BasicDBObject("baseInfo.eventId", 1).append("unique", true));
		itemViewEventCollection.createIndex(new BasicDBObject("baseInfo.eventTimestamp", -1));
	}

	@Override
	public void addItemViewEvent(ItemViewEvent event) {
		WriteResult result = insertCommon(event);
		if(result.getError() != null)
		{
			throw new DevDaoException(result.toString());
		}
	}

	@Override
	public ItemViewEvent getItemViewEvent(String eventId) {
		return findOneCommon(where("baseInfo.eventId").is(eventId), ItemViewEvent.class);
	}

	@Override
	public List<ItemViewEvent> getItemViewEventList(int pageNum, int limit) {
		return listCommon(page(pageNum, limit).setSort(desc("baseInfo.eventTimestamp")), ItemViewEvent.class);
	}

	@Override
	public List<ItemViewEvent> getItemViewEventList(int pageNum, int limit,
			long fromTimestamp, long toTimestamp) {

		return listCommon(page(pageNum, limit).setQuery(where("baseInfo.eventTimestamp").betweenEquals(fromTimestamp, toTimestamp)), ItemViewEvent.class);
	}

	@Override
	public long getAllItemViewEventCount() {
		return countCommon();
	}

	@Override
	public long getItemViewEventListCount(long fromTimestamp, long toTimestamp) {
		Query query = where("baseInfo.eventTimestamp").betweenEquals(fromTimestamp, toTimestamp);
		System.out.println(query.getDbObject().toString());
        return countCommon(query);
	}

	@Override
	public void removeItemViewEvent(String eventId) {
        WriteResult result = removeCommon(where("baseInfo.eventId").is(eventId));
        if(result.getError() != null){
            throw new DevDaoException(result.toString());
        }
	}

	@Override
	public void removeItemViewEventAll() {
		getDbCollection().drop();
	}

	@Override
	public void addItemViewEvents(List<ItemViewEvent> events) {
		WriteResult result = insertCommon((ItemViewEvent[])events.toArray(new ItemViewEvent[0]));
		if(result.getError() != null)
		{
			throw new DevDaoException(result.toString());
		}		
	}

}
