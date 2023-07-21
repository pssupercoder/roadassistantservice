package com.geico.emergencyroadassistantservice.api.exceptions;

public class GeicoException extends Exception{
	
	private static final long serialVersionUID = 1L;
	private int errorCode;
	private String errorMessage;
	
	public GeicoException(int errorCode, String errorMessage){
		this.errorCode=errorCode;
		this.errorMessage=errorMessage;
	}
	
	public GeicoException(int errorCode, String errorMessage, Throwable throwable){
	    super(throwable);
		this.errorCode=errorCode;
		this.errorMessage=errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

}
