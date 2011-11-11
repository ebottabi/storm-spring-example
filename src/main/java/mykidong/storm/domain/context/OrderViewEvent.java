package mykidong.storm.domain.context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderViewEvent implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2520026399122865135L;

	private BaseInfo baseInfo;

	private OrderItem[] orderItemList;
	private String orderNum;
	private long totalPay;

	public OrderViewEvent(){
	}

	public BaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(BaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public OrderItem[] getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(OrderItem[] orderItemList) {
		this.orderItemList = orderItemList;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public long getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(long totalPay) {
		this.totalPay = totalPay;
	}

	public static class OrderItem implements Serializable {

		/**
		 *
		 */
		private static final long serialVersionUID = -3357228033084678639L;

		private String itemId;
		private int quantity;

		private long price;

		public String getItemId() {
			return itemId;
		}

		public void setItemId(String itemId) {
			this.itemId = itemId;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		public long getPrice() {
			return price;
		}

		public void setPrice(long price) {
			this.price = price;
		}
	}

}
