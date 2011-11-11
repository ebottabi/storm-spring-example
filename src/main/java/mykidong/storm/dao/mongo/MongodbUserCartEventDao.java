package mykidong.storm.dao.mongo;

import static mykidong.storm.dao.mongo.support.Query.where;
import static mykidong.storm.dao.mongo.support.QueryMng.page;

import java.util.List;

import mykidong.storm.api.dao.UserCartEventDao;
import mykidong.storm.dao.mongo.support.DevMongoDbSupport;
import mykidong.storm.domain.context.CartViewEvent;
import mykidong.storm.util.DevDaoException;

import org.springframework.beans.factory.InitializingBean;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.WriteResult;

public class MongodbUserCartEventDao extends DevMongoDbSupport implements UserCartEventDao, InitializingBean{

	@Override
	public void afterPropertiesSet() throws Exception {
		init();
		DBCollection cartViewEventCollection = getDbCollection();
		cartViewEventCollection.ensureIndex(new BasicDBObject("baseInfo.eventId", 1).append("unique", true));
		cartViewEventCollection.createIndex(new BasicDBObject("baseInfo.eventTimestamp", -1));
	}

	@Override
	public void addCartViewEvent(CartViewEvent event) {
		WriteResult result = insertCommon(event);
		if(result.getError() != null)
		{
			throw new DevDaoException(result.toString());
		}
	}

	@Override
	public CartViewEvent getCartViewEvent(String eventId) {
		return findOneCommon(where("baseInfo.eventId").is(eventId), CartViewEvent.class);
	}

	@Override
	public void removeCartViewEvent(String eventId) {
        WriteResult result = removeCommon(where("baseInfo.eventId").is(eventId));
        if(result.getError() != null){
            throw new DevDaoException(result.toString());
        }
	}

	@Override
	public void removeCartViewEventAll() {
		getDbCollection().drop();
	}

	@Override
	public List<CartViewEvent> getCartViewEventList(int pageNum, int limit) {
		return listCommon(page(pageNum, limit), CartViewEvent.class);
	}

	@Override
	public List<CartViewEvent> getCartViewEventList(int pageNum, int limit,
			long fromTimestamp, long toTimestamp) {
		return listCommon(
				page(pageNum, limit)
				.setQuery(where("baseInfo.eventTimestamp")
				.betweenEquals(fromTimestamp, toTimestamp))
				, CartViewEvent.class);
	}

	@Override
	public long getAllCartViewEventCount() {
		return countCommon();
	}

	@Override
	public long getCartViewEventListCount(long fromTimestamp, long toTimestamp) {
        return countCommon(where("baseInfo.eventTimestamp").betweenEquals(fromTimestamp, toTimestamp));
	}

	@Override
	public void addCartViewEvents(List<CartViewEvent> events) {
		WriteResult result = insertCommon((CartViewEvent[])events.toArray(new CartViewEvent[0]));
		if(result.getError() != null)
		{
			throw new DevDaoException(result.toString());
		}			
	}

}
