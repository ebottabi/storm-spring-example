package mykidong.storm.component;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeromq.ZMQ;
import org.zeromq.ZMQException;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;


public class ZeroMqSpout implements IRichSpout{		

	private static final long serialVersionUID = 1L;
	
	private static Logger log = LoggerFactory.getLogger(ZeroMqSpout.class);
	
	private SpoutOutputCollector collector;
	
	private ZMQ.Context zeroMqContext;	
	
	private ZMQ.Socket socket;

	private String url;
	
	public ZeroMqSpout(String url)
	{
		this.url = url;		
	}

	@Override
	public void open(Map conf, TopologyContext zeroMqContext,
			SpoutOutputCollector collector) {			
		this.collector = collector;	
		
		this.zeroMqContext = ZMQ.context(1);
		
		socket = this.zeroMqContext.socket(ZMQ.PULL);	
		socket.connect(url);		
	}
	

	@Override
	public void close() {	
		
		if(socket != null)
		{
			socket.close();
			socket = null;
		}
	
		if(this.zeroMqContext != null)
		{
			this.zeroMqContext.term();
			this.zeroMqContext = null;
		}
	}


	@Override
	public void nextTuple() {	
		byte[] msg = null;
	      
        if(this.socket != null) {
            try {
                msg = socket.recv(0);	                
                String msgStr = new String(msg);           
                
                collector.emit(new Values(msgStr));                
            } catch (ZMQException e) {                
                throw new RuntimeException(e.getCause());
            }
        }		
	}

	@Override
	public void ack(Object msgId) {		
	}

	@Override
	public void fail(Object msgId) {		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("event"));		
	}

	@Override
	public boolean isDistributed() {		
		return true;
	}
}
