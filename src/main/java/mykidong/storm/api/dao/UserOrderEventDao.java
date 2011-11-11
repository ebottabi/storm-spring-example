package mykidong.storm.api.dao;

import java.util.List;

import mykidong.storm.domain.context.OrderViewEvent;

public interface UserOrderEventDao {

	public void addOrderViewEvent(OrderViewEvent event);

	public void addOrderViewEvents(List<OrderViewEvent> events);

	public OrderViewEvent getOrderViewEvent(String eventId);

	public long getAllOrderViewEventCount();

	public List<OrderViewEvent> getOrderViewEventList(int pageNum, int limit);

	public List<OrderViewEvent> getOrderViewEventList(int pageNum, int limit, long fromTimestamp, long toTimestamp);

	public long getOrderViewEventListCount(long fromTimestamp, long toTimestamp);

	public void removeOrderViewEvent(String eventId);

	public void removeOrderViewEventAll();

}
