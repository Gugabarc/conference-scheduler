package com.company.conference_scheduler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.company.conference_scheduler.parser.EventParser;

@Configuration
public class AppConfig {

	@Autowired
	private ApplicationContext context;

	@Bean
	public EventParser eventParserAlias(@Value("${event.parser}") String qualifier) {
		return (EventParser) context.getBean(qualifier);
	}
}
