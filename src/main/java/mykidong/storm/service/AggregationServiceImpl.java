package mykidong.storm.service;

import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import mykidong.storm.api.dao.GlobalUniqueIdDao;
import mykidong.storm.api.dao.UserCartEventDao;
import mykidong.storm.api.dao.UserItemEventDao;
import mykidong.storm.api.dao.UserOrderEventDao;
import mykidong.storm.api.service.AggregationService;
import mykidong.storm.domain.context.CartViewEvent;
import mykidong.storm.domain.context.Constants;
import mykidong.storm.domain.context.ItemViewEvent;
import mykidong.storm.domain.context.OrderViewEvent;

public class AggregationServiceImpl implements AggregationService {

	private static final String ENCODING = "UTF-8";

	private UserItemEventDao userItemEventDao;
	private UserCartEventDao userCartEventDao;
	private UserOrderEventDao userOrderEventDao;
	private GlobalUniqueIdDao globalUniqueIdDao;

	public void setUserItemEventDao(UserItemEventDao userItemEventDao) {
		this.userItemEventDao = userItemEventDao;
	}

	public void setUserCartEventDao(UserCartEventDao userCartEventDao) {
		this.userCartEventDao = userCartEventDao;
	}

	public void setUserOrderEventDao(UserOrderEventDao userOrderEventDao) {
		this.userOrderEventDao = userOrderEventDao;
	}


	public void setGlobalUniqueIdDao(GlobalUniqueIdDao globalUniqueIdDao) {
		this.globalUniqueIdDao = globalUniqueIdDao;
	}

	@Override
	public void addItemViewEvent(ItemViewEvent event) {
		buildItemViewEvent(event);

		this.userItemEventDao.addItemViewEvent(event);
	}

	private void buildItemViewEvent(ItemViewEvent event) {
		String eventId = generateEventId(Constants.ITEM_VIEW_EVENT_ID_NAME, "ITEM_VIEW_EVENT");
		event.getBaseInfo().setEventId(eventId);
		event.getBaseInfo().setEventTimestamp(new Date().getTime());

		// remove html elements from item contents.
		Document itemContentsDoc = Jsoup.parse(event.getItemContents(), ENCODING);
		event.setItemContents(itemContentsDoc.text());
	}

	@Override
	public void addCartViewEvent(CartViewEvent event) {
		buildCartViewEvent(event);

		this.userCartEventDao.addCartViewEvent(event);
	}

	private void buildCartViewEvent(CartViewEvent event) {
		String eventId = generateEventId(Constants.CART_VIEW_EVENT_ID_NAME, "CART_VIEW_EVENT");
		event.getBaseInfo().setEventId(eventId);
		event.getBaseInfo().setEventTimestamp(new Date().getTime());
	}

	@Override
	public void addOrderViewEvent(OrderViewEvent event) {
		buildOrderViewEvent(event);

		this.userOrderEventDao.addOrderViewEvent(event);
	}

	private void buildOrderViewEvent(OrderViewEvent event) {
		String eventId = generateEventId(Constants.ORDER_VIEW_EVENT_ID_NAME, "ORDER_VIEW_EVENT");
		event.getBaseInfo().setEventId(eventId);
		event.getBaseInfo().setEventTimestamp(new Date().getTime());
	}

	private String generateEventId(String idName, String prefix) {
		long eventIdLong = this.globalUniqueIdDao.getGlobalUniqueId(idName);
		String eventId = prefix + "-" + new Date().getTime() + "-" + eventIdLong;

		return eventId;
	}

	@Override
	public void addItemViewEvents(List<ItemViewEvent> events) {
		for(ItemViewEvent event : events)
		{
			this.buildItemViewEvent(event);
		}
		
		this.userItemEventDao.addItemViewEvents(events);
		
	}

	@Override
	public void addCartViewEvents(List<CartViewEvent> events) {
		for(CartViewEvent event : events)
		{
			this.buildCartViewEvent(event);
		}
		
		this.userCartEventDao.addCartViewEvents(events);
		
	}

	@Override
	public void addOrderViewEvents(List<OrderViewEvent> events) {
		for(OrderViewEvent event : events)
		{
			this.buildOrderViewEvent(event);
		}
		
		this.userOrderEventDao.addOrderViewEvents(events);		
	}
}
