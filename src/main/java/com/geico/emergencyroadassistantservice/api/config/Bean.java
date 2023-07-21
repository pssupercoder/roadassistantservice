package com.geico.emergencyroadassistantservice.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.geico.emergencyroadassistantservice.api.interceptors.RequestInterceptor;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

@Component
public class Bean {

	@org.springframework.context.annotation.Bean
	public RequestInterceptor getRequestInterceptor() {
		return new RequestInterceptor();
	}

	@Autowired
	private ApplicationConfiguration applicationConfiguration;

	@org.springframework.context.annotation.Bean
	public JwtParser getJwtParser() {
		return Jwts.parser().setSigningKey(applicationConfiguration.getJwtDecreptKey().getBytes());
	}

}
