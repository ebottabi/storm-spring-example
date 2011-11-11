package mykidong.storm.api.dao;

import java.util.List;

import mykidong.storm.domain.context.CartViewEvent;

public interface UserCartEventDao {

	public void addCartViewEvent(CartViewEvent event);

	public void addCartViewEvents(List<CartViewEvent> events);

	public CartViewEvent getCartViewEvent(String eventId);

	public long getAllCartViewEventCount();

	public List<CartViewEvent> getCartViewEventList(int pageNum, int limit);

	public List<CartViewEvent> getCartViewEventList(int pageNum, int limit, long fromTimestamp, long toTimestamp);

	public long getCartViewEventListCount(long fromTimestamp, long toTimestamp);

	public void removeCartViewEvent(String eventId);

	public void removeCartViewEventAll();
}
