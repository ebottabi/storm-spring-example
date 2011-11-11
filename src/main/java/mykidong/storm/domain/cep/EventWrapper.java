package mykidong.storm.domain.cep;

import java.io.Serializable;

public class EventWrapper implements Serializable {
	
	String eventType;
	
	Object event;

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public Object getEvent() {
		return event;
	}

	public void setEvent(Object event) {
		this.event = event;
	}
}
