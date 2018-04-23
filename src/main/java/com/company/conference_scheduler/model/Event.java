package com.company.conference_scheduler.model;

import java.time.Duration;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Event {
	private final long id;
	private String name;
	private Duration duration;
	private boolean scheduled;
	private LocalTime startTime;
	private LocalTime endtTime;
	private Duration d;
	
	@Override
	public String toString() {
		return startTime + " - " + endtTime  + " " + name;
	}
}
