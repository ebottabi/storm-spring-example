package mykidong.storm.util;


/**
 * <pre>
 *
 * Copyright &copy 2010 by GSHS. All rights reserved.
 *
 * </pre>
 * @author kkaok1
 * @since 1.0  2011. 7. 7. 오전 11:02:49
 * <pre>
 *
 * history :
 *
 * </pre>
 * @version 1.0  2011. 7. 7. 오전 11:02:49
 */
public abstract class AbstractDevException extends RuntimeException{

    public final static int DEFUALT_ERROR_CODE = -1;
    protected int errorCode;
    protected String message;
    protected Throwable cause;

    public AbstractDevException(){
        super();
        this.errorCode = DEFUALT_ERROR_CODE;
    }

    public AbstractDevException(String message){
        super(message);
        this.message = message;
        this.errorCode = DEFUALT_ERROR_CODE;
    }

    public AbstractDevException(String message, int errorCode){
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }

    public AbstractDevException(String msg, int errorCode, Throwable ex){
        super(msg, ex);
        this.message = msg;
        this.errorCode = errorCode;
        this.cause = ex;
    }

    public AbstractDevException(String message, Throwable ex){
        super(message, ex);
        this.message = message;
        this.errorCode = DEFUALT_ERROR_CODE;
        this.cause = ex;
    }

    public int getErrorCode(){
        return this.errorCode;
    }

    public abstract String getMessage();

    public abstract String getUserMessage();

    public Throwable getCause() {
        return (this.cause == this ? null : this.cause);
    }

}
