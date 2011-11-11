package mykidong.storm.dao.mongo;

import static mykidong.storm.dao.mongo.support.Query.where;
import static mykidong.storm.dao.mongo.support.QueryMng.page;

import java.util.List;

import mykidong.storm.api.dao.UserOrderEventDao;
import mykidong.storm.dao.mongo.support.DevMongoDbSupport;
import mykidong.storm.domain.context.OrderViewEvent;
import mykidong.storm.util.DevDaoException;

import org.springframework.beans.factory.InitializingBean;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.WriteResult;

public class MongodbUserOrderEventDao extends DevMongoDbSupport implements UserOrderEventDao, InitializingBean{

	@Override
	public void afterPropertiesSet() throws Exception {
		init();
		DBCollection orderViewEventCollection = getDbCollection();
		orderViewEventCollection.ensureIndex(new BasicDBObject("baseInfo.eventId", 1).append("unique", true));
		orderViewEventCollection.createIndex(new BasicDBObject("baseInfo.eventTimestamp", -1));
	}

	@Override
	public void addOrderViewEvent(OrderViewEvent event) {
		WriteResult result = insertCommon(event);
		if(result.getError() != null)
		{
			throw new DevDaoException(result.toString());
		}
	}

	@Override
	public OrderViewEvent getOrderViewEvent(String eventId) {
		return findOneCommon(where("baseInfo.eventId").is(eventId), OrderViewEvent.class);
	}

	@Override
	public void removeOrderViewEvent(String eventId) {
        WriteResult result = removeCommon(where("baseInfo.eventId").is(eventId));
        if(result.getError() != null){
            throw new DevDaoException(result.toString());
        }
	}

	@Override
	public void removeOrderViewEventAll() {
		getDbCollection().drop();
	}

	@Override
	public List<OrderViewEvent> getOrderViewEventList(int pageNum, int limit) {
		return listCommon(page(pageNum, limit), OrderViewEvent.class);
	}

	@Override
	public List<OrderViewEvent> getOrderViewEventList(int pageNum, int limit,
			long fromTimestamp, long toTimestamp) {
		return listCommon(
				page(pageNum, limit)
				.setQuery(where("baseInfo.eventTimestamp")
				.betweenEquals(fromTimestamp, toTimestamp))
				, OrderViewEvent.class);
	}

	@Override
	public long getAllOrderViewEventCount() {
		return countCommon();
	}

	@Override
	public long getOrderViewEventListCount(long fromTimestamp, long toTimestamp) {
        return countCommon(where("baseInfo.eventTimestamp").betweenEquals(fromTimestamp, toTimestamp));
	}

	@Override
	public void addOrderViewEvents(List<OrderViewEvent> events) {
		WriteResult result = insertCommon((OrderViewEvent[])events.toArray(new OrderViewEvent[0]));
		if(result.getError() != null)
		{
			throw new DevDaoException(result.toString());
		}			
	}

}
