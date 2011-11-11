package mykidong.test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import mykidong.storm.api.dao.UserCartEventDao;
import mykidong.storm.api.dao.UserItemEventDao;
import mykidong.storm.api.dao.UserOrderEventDao;
import mykidong.storm.dao.mongo.support.MongoDbFactory.MongoDb;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.DBCollection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/META-INF/spring/spring-application-storm-context.xml", 		
		"classpath:/META-INF/spring/spring-dao-mongodb-context.xml"
		})
public class AggregatorTest extends AbstractJUnit4SpringContextTests{
	
	@Autowired
	private UserItemEventDao userItemEventDao;
	
	@Autowired
	private UserCartEventDao userCartEventDao;
	
	@Autowired
	private UserOrderEventDao userOrderEventDao;
	
	@Autowired
	@Qualifier("testMongoDbFactory")
	private MongoDb mongoDb;

	
	/**
	 * Node JS HTTP Host Address.
	 */
	private static final String HOST = "localhost";	
	
	
	@Test
	public void sendEvents() throws InterruptedException, URISyntaxException, IllegalStateException, IOException
	{
		String nodeJsHost = System.getProperty("host", HOST);
		
		// first, drop all mongodb event collections.
		dropEventCollections();		
		
		// sample event string.
		String itemViewEvent = "{\"baseInfo\":{\"visitorId\":\"13179688882812076312147\",\"loginId\":\"239350101\",\"localipAddress\":\"192.168.223.114\",\"version\":\"1_0_0\",\"referer\":\"http://local.mykidong.com/prd/prd.gs?prdid=5855647\",\"url\":\"http://local.mykidong.com/prd/prd.gs?prdid=5865740&svcid=pc&dseq=0&lseq=357608&bnclick=prd_best_gs&baynote=prdbest_null\"},\"itemId\":\"5865740\",\"itemContents\":\"Romeo & Juliet Couture Knitted Skull Sweater\",\"scrollRange\":131,\"stayTerm\":16266,\"scrollUpDownCount\":7}";
		String cartViewEvent = "{\"baseInfo\":{\"visitorId\":\"13179688882812076312147\",\"loginId\":\"239350101\",\"localipAddress\":\"192.168.223.114\",\"version\":\"1_0_0\",\"referer\":\"null\",\"url\":\"http://local.mykidong.com/cart/viewCart.gs\"},\"cartItemList\":[\"5855647\",\"5154116\",\"5701707\"]}";
		String orderViewEvent = "{\"baseInfo\":{\"visitorId\":\"13179688882812076312147\",\"loginId\":\"239350101\",\"localipAddress\":\"192.168.223.114\",\"version\":\"1_0_0\",\"referer\":\"null\",\"url\":\"http://local.mykidong.com/cart/viewCart.gs?gsid=gnb-AU356817-AU356817-25&svcid=gc&lseq=356817-25&dseq=1062650\"},\"orderNum\":\"\",\"totalPay\":0,\"orderItemList\":[{\"itemId\":\"5855647\",\"quantity\":1,\"price\":\"19800\"},{\"itemId\":\"5154116\",\"quantity\":1,\"price\":\"98000\"},{\"itemId\":\"5701707\",\"quantity\":1,\"price\":\"29800\"}]}";
		
		// event path.
		String itemViewEventPath = "/ec_p/1_0_0/addItemViewEvent";
		String cartViewEventPath = "/ec_p/1_0_0/addCartViewEvent";
		String orderViewEventPath = "/ec_p/1_0_0/addOrderViewEvent";
		
		List<EventSample> eventSampleList = new ArrayList<EventSample>();
		eventSampleList.add(new EventSample(itemViewEvent, itemViewEventPath));
		eventSampleList.add(new EventSample(cartViewEvent, cartViewEventPath));
		eventSampleList.add(new EventSample(orderViewEvent, orderViewEventPath));
		
		int MAX_COUNT = 100;
		
		for(int i = 0; i < MAX_COUNT; i++)
		{
			for(EventSample eventSample : eventSampleList)
			{
				List<NameValuePair> qparams = new ArrayList<NameValuePair>();
				qparams.add(new BasicNameValuePair("eventString", eventSample.getEvent()));			
				
				URI uri = URIUtils.createURI("http", nodeJsHost, 80, eventSample.getPath(), URLEncodedUtils.format(qparams, "UTF-8"), null);
				HttpGet httpget = new HttpGet(uri);
				
				HttpClient httpclient = new DefaultHttpClient();
				httpclient.execute(httpget);	
			}
		}
		
		
		System.out.println("wait for a while ...");
		Thread.sleep(15000);
				
		Assert.assertTrue(this.userItemEventDao.getAllItemViewEventCount() == MAX_COUNT);
		Assert.assertTrue(this.userCartEventDao.getAllCartViewEventCount() == MAX_COUNT);
		Assert.assertTrue(this.userOrderEventDao.getAllOrderViewEventCount() == MAX_COUNT);			
	}

	private void dropEventCollections() {
		String collectionName = "ItemViewEvent";
		DBCollection collection = this.mongoDb.getDb().getCollection(collectionName);
		collection.drop();
		
		collectionName = "CartViewEvent";
		collection = this.mongoDb.getDb().getCollection(collectionName);
		collection.drop();
		
		collectionName = "OrderViewEvent";
		collection = this.mongoDb.getDb().getCollection(collectionName);
		collection.drop();
	}
	
	private static class EventSample
	{
		private String event;
		
		private String path;
		
		public EventSample(String event, String path)
		{
			this.event = event;
			this.path = path;
		}

		public String getEvent() {
			return event;
		}

		public void setEvent(String event) {
			this.event = event;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}	
	}	
}
