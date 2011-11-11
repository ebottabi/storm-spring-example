package mykidong.storm.api.service;

import java.util.List;

import mykidong.storm.domain.context.CartViewEvent;
import mykidong.storm.domain.context.ItemViewEvent;
import mykidong.storm.domain.context.OrderViewEvent;

public interface AggregationService {

	public void addItemViewEvent(ItemViewEvent event);
	public void addItemViewEvents(List<ItemViewEvent> events);

	public void addCartViewEvent(CartViewEvent event);
	public void addCartViewEvents(List<CartViewEvent> events);

	public void addOrderViewEvent(OrderViewEvent event);
	public void addOrderViewEvents(List<OrderViewEvent> events);

}
