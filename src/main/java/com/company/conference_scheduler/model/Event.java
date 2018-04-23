package com.company.conference_scheduler.model;

import java.time.Duration;
import java.time.LocalTime;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Event {
	private final long id;
	private String name;
	private Duration duration;
	private boolean scheduled;
	private LocalTime startTime;
	private LocalTime endtTime;
	
	@Override
	public String toString() {
		return startTime + " - " + endtTime  + " " + name;
	}
}
