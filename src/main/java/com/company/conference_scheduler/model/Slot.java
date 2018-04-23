package com.company.conference_scheduler.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Slot {
	private long remainingDurationInMinutes;
	private Duration duration;
	private LocalTime startTime;
	private String name;
	private boolean hasEvents;
	
	@Builder.Default
	private boolean isLast = false;
	
	@Builder.Default
	private List<Event> events = new ArrayList<>(); 
	
	public boolean addEvent(Event event) {
		if (hasRoomFor(event)) {
			events.add(event);
			remainingDurationInMinutes = remainingDurationInMinutes - event.getDuration().toMinutes();
			
			return true;
		}
		
		return false;
	}

	public boolean hasRoomFor(Event event) {
		return event.isScheduled() == false && remainingDurationInMinutes >= event.getDuration().toMinutes();
	}
	
	@Override
	public String toString() {
		String print = startTime + " - " + name + "\n";
		
		for (Event event : events) {
			print = print + event + "\n";
		}
		
		return print;
	}
}
