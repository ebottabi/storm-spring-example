package mykidong.storm.dao.mongo.support;

import mykidong.storm.dao.mongo.support.MongoDbFactory.MongoDb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DB;
import com.mongodb.Mongo;


public abstract class MongodbDaoSupport{
	
	/**
	 * log4j to log.
	 */
	private static Logger log = LoggerFactory
			.getLogger(MongodbDaoSupport.class);

	private MongoDb mongoDb;	

	public void setMongoDb(MongoDb mongoDb) {
		this.mongoDb = mongoDb;
	}

	protected Mongo getMongo() {
		return this.mongoDb.getMongo();
	}

	protected DB getDb() {
		return this.mongoDb.getDb();
	}
}

