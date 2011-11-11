package mykidong.storm.util;

public class DevDaoException extends AbstractDevUnCheckedException{

    public DevDaoException(String msg){
        super(msg);
    }

    public DevDaoException(String msg, Throwable ex){
        super(msg, ex);
    }

    public DevDaoException(String msg, int errorCode){
        super(msg, errorCode);
    }

    public DevDaoException(String msg, int errorCode, Throwable ex){
        super(msg, errorCode, ex);
    }

}