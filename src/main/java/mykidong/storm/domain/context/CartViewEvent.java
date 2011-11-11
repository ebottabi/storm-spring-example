package mykidong.storm.domain.context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartViewEvent implements Serializable {

	private BaseInfo baseInfo;

	private String[] cartItemList;

	public CartViewEvent(){
	}

	public BaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(BaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public String[] getCartItemList() {
		return cartItemList;
	}

	public void setCartItemList(String[] cartItemList) {
		this.cartItemList = cartItemList;
	}

}
