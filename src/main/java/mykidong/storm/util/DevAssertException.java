package mykidong.storm.util;

public class DevAssertException extends AbstractDevException {
	
	//private final static int ASSERT_ERROR_CODE = -100;
	public DevAssertException(){
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	} 
	
	public String getUserMessage() {
		return message;
	}
}
