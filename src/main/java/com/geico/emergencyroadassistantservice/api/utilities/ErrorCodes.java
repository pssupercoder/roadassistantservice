package com.geico.emergencyroadassistantservice.api.utilities;

public class ErrorCodes {

	public final static int ASSISTANT_NOT_FOUND= 1;
	public final static int CUSTOMER_NOT_FOUND= 2;
	public final static int ASSISTANT_GEOLOCATION_NOT_FOUND= 3;
	public final static int GEOLOCATION_NOT_FOUND= 4;
	
	public final static int INVALID_INPUT= 5;
	public final static int REGISTRATION_NOT_FOUND= 6;
	public final static int REGISTRATION_FOUND= 7;
	
	public final static String MSG_GEICO_SUPPROT ="Please contact geico support team.";
	
	
	public final static String MSG_ASSISTANT_NOT_FOUND= "Assistant not found in system.";
	public final static String MSG_CUSOTMER_NOT_FOUND= "Customer not found in system.";
	public final static String MSG_ASSISTANT_GEOLOCATION_NOT_FOUND= "Assistant geo location not found in system.";
	public final static String MSG_GEOLOCATION_NOT_FOUND= "Geolocation not found in system.";
	public final static String MSG_REGISTRATION_NOT_FOUND= "Registration not found in System.";
	public final static String MSG_REGISTRATION_FOUND= "Registration found in System.";
	
	public final static String MSG_EMPTY_GEOLOCATION_LIST= "Geolocation list is empty.";
	public final static String MSG_INVALID_CUSTOMER= "Customer data is null.";
	public final static String MSG_INVALID_ASSISTANT= "Assistant data is null.";
	public final static String MSG_INVALID_GEOLOCATION= "Geolocation data is invalid.";
	public final static String MSG_INVALID_LIMIT= "Proivded limit is invalid.";
	public final static String MSG_INVALID_CUSTOMER_ID= "Proivded cutomer id is invalid.";
	public final static String MSG_INVALID_ASSISTANT_ID= "Proivded assistant id is invalid.";
	public final static String MSG_CUSOMETR_ALREADY_EXIST= "Cusomter already exists in System.";
	public final static String MSG_ASSISTANT_ALREADY_EXIST= "Assistant already exists in System.";
}
