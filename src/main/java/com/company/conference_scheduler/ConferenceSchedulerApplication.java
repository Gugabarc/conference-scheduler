package com.company.conference_scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.company.conference_scheduler.converter.TrackToStringConverter;
import com.company.conference_scheduler.model.Track;
import com.company.conference_scheduler.service.SchedulerService;
import com.company.conference_scheduler.util.FilePrint;

@SpringBootApplication
@EnableAutoConfiguration
@Component
@ComponentScan
public class ConferenceSchedulerApplication {
	
	@Autowired
	private SchedulerService schedulerService;
	
	@Autowired
	private TrackToStringConverter trackToStringConverter;
	
	@Autowired
	private FilePrint filePrint;
	
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(ConferenceSchedulerApplication.class, args);

		ConferenceSchedulerApplication mainObj = ctx.getBean(ConferenceSchedulerApplication.class);

		mainObj.init();
	}

	private void init() {
		List<Track> tracksScheduled = schedulerService.schedule();
		List<String> tracksAsString = trackToStringConverter.convertTracksToStringList(tracksScheduled);
		filePrint.printToFile(tracksAsString);
	}
}
