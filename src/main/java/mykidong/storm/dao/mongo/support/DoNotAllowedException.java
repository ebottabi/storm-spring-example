package mykidong.storm.dao.mongo.support;

public class DoNotAllowedException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = 8219581748567535546L;

    public DoNotAllowedException(){
        super();
    }

    public DoNotAllowedException(String msg){
        super(msg);
    }

    public DoNotAllowedException(String msg, Throwable e){
        super(msg, e);
    }

}
