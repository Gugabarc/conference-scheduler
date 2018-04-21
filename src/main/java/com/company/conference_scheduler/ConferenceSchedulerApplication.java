package com.company.conference_scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.company.conference_scheduler.service.SchedulerService;

@SpringBootApplication
@EnableAutoConfiguration
@Component
@ComponentScan
public class ConferenceSchedulerApplication {
	
	@Autowired
	private SchedulerService schedulerService;
	
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(ConferenceSchedulerApplication.class, args);

		ConferenceSchedulerApplication mainObj = ctx.getBean(ConferenceSchedulerApplication.class);

		mainObj.init();
	}

	private void init() {
		schedulerService.schedule();
	}
}
