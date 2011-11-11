package mykidong.storm.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mykidong.storm.api.service.AggregationService;
import mykidong.storm.domain.cep.EventWrapper;
import mykidong.storm.domain.context.CartViewEvent;
import mykidong.storm.domain.context.ItemViewEvent;
import mykidong.storm.domain.context.OrderViewEvent;
import mykidong.storm.util.JsonMashaller;
import mykidong.storm.util.SpringApplicationContextFactory;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class ThrottlingBolt implements IRichBolt, UpdateListener{
	
	private static ApplicationContext applicationContext = SpringApplicationContextFactory.newInstance();  
	
	private static Logger log = LoggerFactory.getLogger(ThrottlingBolt.class);
	
	private static final long serialVersionUID = 1L;

    private final Fields outputFields;
    private final String[] statements;
    private transient EPRuntime runtime;
    private transient EPAdministrator admin;
    private transient OutputCollector collector;
    
    private AggregationService aggregationService; 

    public ThrottlingBolt(Fields outputFields, String... esperStmts)
    {
        this.outputFields = outputFields;
        this.statements = esperStmts;
    }
    
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer)
    {
        declarer.declare(outputFields);
    }

    @Override
    public void prepare(Map conf, TopologyContext context, OutputCollector collector)
    {
        this.collector = collector;
        
        EPServiceProvider esperSink = EPServiceProviderManager.getProvider(null);

        this.runtime = esperSink.getEPRuntime();
        this.admin = esperSink.getEPAdministrator();

        for (String stmt : statements) {
            EPStatement statement = admin.createEPL(stmt);

            statement.addListener(this);
        }
        
        this.aggregationService = applicationContext.getBean(AggregationService.class);
    }

    @Override
    public void execute(Tuple tuple)
    {
        String jsonText = (String)tuple.getValue(0);     
        jsonText = jsonText.replaceAll("'", "\"");      
        
        ObjectMapper m = new ObjectMapper();
      
		try {
			JsonNode rootNode = m.readValue(jsonText, JsonNode.class);
			
			JsonNode eventTypeNode = rootNode.path("eventType");
	        JsonNode eventStringNode = rootNode.path("eventString");
	        
	        String eventType = eventTypeNode.getTextValue();	       
	        String eventString = eventStringNode.toString();
	        
	        EventWrapper eventWrapper = new EventWrapper();
	        eventWrapper.setEventType(eventType);
	        
	        if(eventType.equals("item"))
	        {
	        	ItemViewEvent event = (ItemViewEvent)JsonMashaller.unmarshal(eventString, ItemViewEvent.class);
	        	
	        	eventWrapper.setEvent(event);
	        }
	        else if(eventType.equals("cart"))
	        {
	        	CartViewEvent event = (CartViewEvent)JsonMashaller.unmarshal(eventString, CartViewEvent.class);
	        	
	        	eventWrapper.setEvent(event);
	        }
	        else if(eventType.equals("order"))
	        {
	        	OrderViewEvent event = (OrderViewEvent)JsonMashaller.unmarshal(eventString, OrderViewEvent.class);
	        	
	        	eventWrapper.setEvent(event);
	        }        

	        runtime.sendEvent(eventWrapper);	     
	        
		} catch (JsonParseException e) {			
			throw new RuntimeException(e.getCause());
		} catch (JsonMappingException e) {				
			throw new RuntimeException(e.getCause());
		} catch (IOException e) {				
			throw new RuntimeException(e.getCause());
		}        
    }
 
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
    	log.info("flush ...");
    	
        if (newEvents != null) {
        	
        	List<ItemViewEvent> itemViewEventList = new ArrayList<ItemViewEvent>();
        	List<CartViewEvent> cartViewEventList = new ArrayList<CartViewEvent>();
        	List<OrderViewEvent> orderViewEventList = new ArrayList<OrderViewEvent>();
        	
            for (EventBean newEvent : newEvents) {
            	String eventType = (String)newEvent.get("eventType"); 
            	if(eventType.equals("item"))
                {
                	ItemViewEvent event = (ItemViewEvent)newEvent.get("event");
                	
                	itemViewEventList.add(event);
                }
                else if(eventType.equals("cart"))
                {
                	CartViewEvent event = (CartViewEvent)newEvent.get("event");
                	
                	cartViewEventList.add(event);
                }
                else if(eventType.equals("order"))
                {
                	OrderViewEvent event = (OrderViewEvent)newEvent.get("event");
                	
                	orderViewEventList.add(event);
                }              	     	
            }
            
            if(itemViewEventList.size() > 0)
            {
            	this.aggregationService.addItemViewEvents(itemViewEventList);
            	
            	log.info("itemViewEvents [" + itemViewEventList.size() + "] added...");
            }
            
            if(cartViewEventList.size() > 0)
            {
            	this.aggregationService.addCartViewEvents(cartViewEventList);
            	
            	log.info("cartViewEventList [" + cartViewEventList.size() + "] added...");
            }
            
            if(orderViewEventList.size() > 0)
            {
            	this.aggregationService.addOrderViewEvents(orderViewEventList);
            	
            	log.info("orderViewEventList [" + orderViewEventList.size() + "] added...");
            }              
        }
    }

  
    @Override
    public void cleanup()
    {}

}
