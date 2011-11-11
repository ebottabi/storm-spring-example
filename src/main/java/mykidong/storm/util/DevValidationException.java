package mykidong.storm.util;

public class DevValidationException extends AbstractDevUnCheckedException{
	
	public DevValidationException(String msg){
		super(msg);
	}

	public DevValidationException(String msg, Throwable ex){
		super(msg, ex);
	}

	public DevValidationException(String msg, int errorCode){
		super(msg, errorCode);
	}
	
	public DevValidationException(String msg, int errorCode, Throwable ex){
		super(msg, errorCode, ex);
	}
	
}