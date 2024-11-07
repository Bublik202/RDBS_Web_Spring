package com.rd.epam.autotasks.scopes.config;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rd.epam.autotasks.factory.ThreadBeanFactoryPostProcessor;

@Configuration
public class ThreadScopeConfig {
	@Bean
	public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
		return new ThreadBeanFactoryPostProcessor();
	}


}