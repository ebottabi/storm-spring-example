package mykidong.storm.dao.mongo.support;

import java.io.Serializable;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.mongodb.DB;
import com.mongodb.DBAddress;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

public class MongoDbFactory implements FactoryBean<MongoDbFactory.MongoDb>, InitializingBean, DisposableBean {
	
	private MongoDb mongoDb;
	
	private String dbName;

	private String host;

	private boolean autoConnectRetry;

	private int connectionsPerHost;	
	
	private int threadsAllowedToBlockForConnectionMultiplier = 5;
	
	private boolean fsync = false;	

	public void setThreadsAllowedToBlockForConnectionMultiplier(
			int threadsAllowedToBlockForConnectionMultiplier) {
		this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
	}

	public void setFsync(boolean fsync) {
		this.fsync = fsync;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setAutoConnectRetry(boolean autoConnectRetry) {
		this.autoConnectRetry = autoConnectRetry;
	}

	public void setConnectionsPerHost(int connectionsPerHost) {
		this.connectionsPerHost = connectionsPerHost;
	}

	@Override
	public MongoDb getObject() throws Exception {	
		return mongoDb;
	}

	@Override
	public Class<?> getObjectType() {	
		return MongoDb.class;
	}

	@Override
	public boolean isSingleton() {	
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		ServerAddress server = new DBAddress(host, this.dbName);

		MongoOptions options = new MongoOptions();
		options.autoConnectRetry = this.autoConnectRetry;
		options.connectionsPerHost = this.connectionsPerHost;
		options.threadsAllowedToBlockForConnectionMultiplier = this.threadsAllowedToBlockForConnectionMultiplier;	
		options.fsync = this.fsync;

		Mongo m = new Mongo(server, options);			
		DB db = m.getDB(this.dbName);
		
		this.mongoDb = new MongoDb();
		this.mongoDb.setMongo(m);
		this.mongoDb.setDb(db);
	}
	
	public static class MongoDb implements Serializable
	{
		private Mongo mongo;
		
		private DB db;

		public Mongo getMongo() {
			return mongo;
		}

		public void setMongo(Mongo mongo) {
			this.mongo = mongo;
		}

		public DB getDb() {
			return db;
		}

		public void setDb(DB db) {
			this.db = db;
		}		
	}

	@Override
	public void destroy() throws Exception {
		this.mongoDb.getMongo().close();	
	}

}
