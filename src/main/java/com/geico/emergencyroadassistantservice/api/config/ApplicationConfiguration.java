package com.geico.emergencyroadassistantservice.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.Nonnull;

@Configuration
@RefreshScope
public class ApplicationConfiguration {

	@Nonnull
	@Value("${jwt.decryptKey}")
	private String jwtDecreptKey;
	
	
	public String getJwtDecreptKey() {
		return jwtDecreptKey;
	}

	public void setJwtDecreptKey(String jwtDecreptKey) {
		this.jwtDecreptKey = jwtDecreptKey;
	}

	
	
	
}
