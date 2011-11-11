package mykidong.storm.dao.mongo.support;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

public class DevMongoFactory implements FactoryBean<Mongo>, InitializingBean, DisposableBean {

    private Mongo mongo;

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

    public void setHost(String host) {
        this.host = host;
    }

    public void setAutoConnectRetry(boolean autoConnectRetry) {
        this.autoConnectRetry = autoConnectRetry;
    }

    public void setConnectionsPerHost(int connectionsPerHost) {
        this.connectionsPerHost = connectionsPerHost;
    }

    public Mongo getObject() throws Exception {
        return mongo;
    }

    public Class<?> getObjectType() {
        return Mongo.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public void afterPropertiesSet() throws Exception {
        ServerAddress server = new ServerAddress(host);

        MongoOptions options = new MongoOptions();
        options.autoConnectRetry = this.autoConnectRetry;
        options.connectionsPerHost = this.connectionsPerHost;
        options.threadsAllowedToBlockForConnectionMultiplier = this.threadsAllowedToBlockForConnectionMultiplier;
        options.fsync = this.fsync;

        this.mongo = new Mongo(server, options);
    }

    public void destroy() throws Exception {
        this.mongo.close();
    }
}
