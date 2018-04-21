package com.company.conference_scheduler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.company.conference_scheduler.parser.TrackParser;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@Setter
public class SchedulerService {
	
	@Autowired
	@Qualifier("fileTrackParser")
	private TrackParser trackparser;
	
	public void schedule() {
		trackparser.getTracks();
	}

}
