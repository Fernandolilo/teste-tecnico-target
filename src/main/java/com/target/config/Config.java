package com.target.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.target.service.logger.ApiLoggingFilter;

@Configuration
public class Config {

	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}

	@Bean
	public FilterRegistrationBean<ApiLoggingFilter> loggingFilter(ApiLoggingFilter filter) {
		FilterRegistrationBean<ApiLoggingFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(filter);
		registrationBean.addUrlPatterns("/*"); // aplicar para todos os endpoints
		registrationBean.setOrder(1); // prioridade do filter
		return registrationBean;
	}
}
