package mykidong.storm.util;

public class DevBaseException extends AbstractDevUnCheckedException{

    public DevBaseException(String msg){
        super(msg);
    }

    public DevBaseException(String msg, Throwable ex){
        super(msg, ex);
    }

    public DevBaseException(String msg, int errorCode){
        super(msg, errorCode);
    }

    public DevBaseException(String msg, int errorCode, Throwable ex){
        super(msg, errorCode, ex);
    }

}