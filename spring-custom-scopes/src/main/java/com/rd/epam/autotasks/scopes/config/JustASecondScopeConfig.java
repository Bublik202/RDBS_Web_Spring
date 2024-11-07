
package com.rd.epam.autotasks.scopes.config;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rd.epam.autotasks.factory.JustASecondBeanFactoryPostProcessor;

@Configuration
public class JustASecondScopeConfig {
	@Bean
	public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
		return new JustASecondBeanFactoryPostProcessor();
	}
}
