package com.geico.emergencyroadassistantservice.api.utilities;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.geico.emergencyroadassistantservice.api.exceptions.GeicoException;

public class CommonUtilities {

	public static final String UTC = "UTC";

	public static Instant getCurrentTime() {
		return Instant.now();
	}
	
	public static ZonedDateTime getCurrentZonedDateTime(String zone) {
		
		ZoneId zoneId = ZoneId.of(zone);
		ZonedDateTime now = ZonedDateTime.now(zoneId );
		
		return now;
	}
	
	public static GeicoException createException(int errorCode, String errorMessage) {
		return new GeicoException(errorCode,errorMessage);
	}
}
