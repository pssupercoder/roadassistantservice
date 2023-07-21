package com.geico.emergencyroadassistantservice.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.geico.emergencyroadassistantservice.api.interceptors.RequestInterceptor;

@Configuration
public class Interceptor  implements WebMvcConfigurer{

	@Autowired
	private RequestInterceptor requestInterceptor;
	
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requestInterceptor).excludePathPatterns("/ping");
	}
}
