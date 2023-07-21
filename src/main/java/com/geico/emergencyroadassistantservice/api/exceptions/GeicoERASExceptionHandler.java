package com.geico.emergencyroadassistantservice.api.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.geico.emergencyroadassistantservice.api.utilities.ErrorCodes;

@ControllerAdvice
public class GeicoERASExceptionHandler extends ResponseEntityExceptionHandler {

	
	@ExceptionHandler(value = { GeicoException.class })
	protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {
		
		String errorMessageBody = ErrorCodes.MSG_GEICO_SUPPROT;
	    HttpStatus errorHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		
		 if(ex instanceof GeicoException) {
			
			GeicoException exception=(GeicoException)ex;
			
			if(exception.getErrorCode()==ErrorCodes.ASSISTANT_NOT_FOUND
					|| exception.getErrorCode()==ErrorCodes.CUSTOMER_NOT_FOUND
					|| exception.getErrorCode()==ErrorCodes.ASSISTANT_GEOLOCATION_NOT_FOUND
					|| exception.getErrorCode()==ErrorCodes.GEOLOCATION_NOT_FOUND) {
				
				errorMessageBody=exception.getErrorMessage();
				errorHttpStatus=HttpStatus.BAD_REQUEST;
				
			}else if(exception.getErrorCode()==ErrorCodes.INVALID_INPUT) {
				
				errorMessageBody=exception.getErrorMessage();
				errorHttpStatus=HttpStatus.BAD_REQUEST;
			}
			
			return handleExceptionInternal(exception, errorMessageBody, new HttpHeaders(), errorHttpStatus, request);
		}
		
		return handleExceptionInternal(ex, errorMessageBody, new HttpHeaders(), errorHttpStatus, request);
	}
}