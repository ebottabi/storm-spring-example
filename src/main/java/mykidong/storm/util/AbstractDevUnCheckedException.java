package mykidong.storm.util;


public abstract class AbstractDevUnCheckedException extends AbstractDevException{

	public AbstractDevUnCheckedException(){
	}

	public AbstractDevUnCheckedException(String message){
		super(message);
	}

	public AbstractDevUnCheckedException(String message, int errorCode){
		super(message, errorCode);
	}
	
	public AbstractDevUnCheckedException(String message, Throwable ex){
		super(message, ex);
	}

	public AbstractDevUnCheckedException(String message, int errorCode, Throwable ex){
		super(message, errorCode, ex);
	}

    public String getMessage() {
        Throwable cause = getCause();
        if (cause != null) {
            return message + "; nested exception is " + cause;
        }
        return message;
    }

    public String getUserMessage() {
    	return message;
    }
    
}
