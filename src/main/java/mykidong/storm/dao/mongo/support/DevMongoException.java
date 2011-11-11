package mykidong.storm.dao.mongo.support;

public class DevMongoException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = 8219581748567535546L;

    public DevMongoException(){
        super();
    }

    public DevMongoException(String msg){
        super(msg);
    }

    public DevMongoException(String msg, Throwable e){
        super(msg, e);
    }

}
