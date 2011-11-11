package mykidong.storm.util;

public class DevBsonException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = 8219581748567535546L;

    public DevBsonException(){
        super();
    }

    public DevBsonException(String msg){
        super(msg);
    }

    public DevBsonException(String msg, Throwable e){
        super(msg, e);
    }

}
