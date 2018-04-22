package com.company.conference_scheduler.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Event {
	private final long id;
	private String name;
	private int durationInMinutes;
	private boolean scheduled;
	
	@Override
	public String toString() {
		return name + " " + durationInMinutes;
	}
}
