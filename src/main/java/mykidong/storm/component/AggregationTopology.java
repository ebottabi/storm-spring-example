package mykidong.storm.component;

import java.util.List;

import mykidong.storm.util.SpringApplicationContextFactory;

import org.springframework.context.ApplicationContext;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class AggregationTopology {

	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
		
		ApplicationContext applicationContext = SpringApplicationContextFactory.newInstance();  
		List<String> urlList = applicationContext.getBean("zeroMqUrlList", List.class);
		
		TopologyBuilder builder = new TopologyBuilder();

		int urlSize = urlList.size();
		int num = 1;
		
		int boltNum = urlSize + 1;
		
		for(String url : urlList)
		{
			builder.setSpout(num, new ZeroMqSpout(url), 4);
			
			String stmt = "SELECT eventType AS eventType, event AS event FROM mykidong.storm.domain.cep.EventWrapper.win:time_batch(5 sec) GROUP BY eventType output every 5 seconds";
			ThrottlingBolt bolt = new ThrottlingBolt(new Fields("eventsAdded"), stmt);				
			builder.setBolt(boltNum++, bolt, 4).shuffleGrouping(num);
			
			num++;
		}	
		
		Config conf = new Config();			
		conf.setDebug(false);			
		conf.setNumWorkers(5);

		StormSubmitter.submitTopology("aggregator-dist", conf, builder.createTopology());		
	}
}
