package mykidong.storm.domain.context;

import java.io.Serializable;

public class BaseInfo implements Serializable {


	private static final long serialVersionUID = -5054472288245131241L;

	private String eventId;

	private String loginId;

	private String visitorId;

	private String localipAddress;

	private String version;

	private String referer;

	private String url;

	private long eventTimestamp;

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}

	public String getLocalipAddress() {
		return localipAddress;
	}

	public void setLocalipAddress(String localipAddress) {
		this.localipAddress = localipAddress;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getEventTimestamp() {
		return eventTimestamp;
	}

	public void setEventTimestamp(long eventTimestamp) {
		this.eventTimestamp = eventTimestamp;
	}

}
