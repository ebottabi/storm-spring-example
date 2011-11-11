package mykidong.storm.domain.context;

import java.io.Serializable;

public class ItemViewEvent implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 6967058814481919405L;

	private BaseInfo baseInfo;

	private String itemId;
	private String itemContents;
	private long scrollRange;
	private long stayTerm;
	private long scrollUpDownCount;
	public BaseInfo getBaseInfo() {
		return baseInfo;
	}
	public void setBaseInfo(BaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemContents() {
		return itemContents;
	}
	public void setItemContents(String itemContents) {
		this.itemContents = itemContents;
	}
	public long getScrollRange() {
		return scrollRange;
	}
	public void setScrollRange(long scrollRange) {
		this.scrollRange = scrollRange;
	}
	public long getStayTerm() {
		return stayTerm;
	}
	public void setStayTerm(long stayTerm) {
		this.stayTerm = stayTerm;
	}
	public long getScrollUpDownCount() {
		return scrollUpDownCount;
	}
	public void setScrollUpDownCount(long scrollUpDownCount) {
		this.scrollUpDownCount = scrollUpDownCount;
	}

}
