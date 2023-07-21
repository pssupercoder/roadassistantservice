package com.geico.emergencyroadassistantservice.api.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.geico.emergencyroadassistantservice.api.validators.JwtTokenValidator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestInterceptor implements HandlerInterceptor{

	private static final Logger LOG = LoggerFactory.getLogger(RequestInterceptor.class);
	
	
	 @Autowired public JwtTokenValidator jwtTokenValidator;
	 
	
	
	
	public RequestInterceptor() {
		
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String jwtToken = request.getHeader("Authorization");
		
		boolean valiadate = jwtTokenValidator.valiadate(jwtToken);
		if(!valiadate) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		return valiadate;
	}

	
	
}
