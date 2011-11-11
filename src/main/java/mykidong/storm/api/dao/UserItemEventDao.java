package mykidong.storm.api.dao;

import java.util.List;

import mykidong.storm.domain.context.ItemViewEvent;

public interface UserItemEventDao {

	public void addItemViewEvent(ItemViewEvent event);
	
	public void addItemViewEvents(List<ItemViewEvent> events);

	public ItemViewEvent getItemViewEvent(String eventId);

	public long getAllItemViewEventCount();

	public List<ItemViewEvent> getItemViewEventList(int pageNum, int limit);

	public List<ItemViewEvent> getItemViewEventList(int pageNum, int limit, long fromTimestamp, long toTimestamp);

	public long getItemViewEventListCount(long fromTimestamp, long toTimestamp);

	public void removeItemViewEvent(String eventId);

	public void removeItemViewEventAll();

}
