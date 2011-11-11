package mykidong.storm.util;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.hazelcast.config.Config;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastClusterInstanceCreator implements InitializingBean, DisposableBean {

	/**
	 * log4j to log.
	 */
	private static Logger log = LoggerFactory
			.getLogger(HazelcastClusterInstanceCreator.class);

	private String configLocation;

	boolean superClient;

	public void setConfigLocation(String configLocation) {
		this.configLocation = configLocation;
	}


	public void setSuperClient(boolean superClient) {
		this.superClient = superClient;
	}

    //private HazelcastInstance hazelcast;

	@Override
	public void afterPropertiesSet() throws Exception {
		InputStream is = getClass().getResourceAsStream("/" + configLocation);

		Config config = new XmlConfigBuilder(is).build();
		config.setSuperClient(superClient);

		HazelcastInstance hazelcast = Hazelcast.newHazelcastInstance(config);

		log.info("cluster group: [" + hazelcast.getConfig().getGroupConfig().getName() + "] init...");
	}

	public void destroy() throws Exception {
		Hazelcast.shutdownAll();
	}
}


