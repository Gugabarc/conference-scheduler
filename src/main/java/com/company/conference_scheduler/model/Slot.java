package com.company.conference_scheduler.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Slot {
	private List<Event> events;
	private int remainingDurationInMinutes;
	private int slotDurationInMinutes;
	private LocalTime startTime;
	private String name;
	private boolean hasEvents;
	
	public Slot() {
		events = new ArrayList<>();
	}

	public void addEvent(Event event) {
		if (hasRoomFor(event)) {
			events.add(event);
			remainingDurationInMinutes = remainingDurationInMinutes - event.getDurationInMinutes();
		}
		
		//throw new IllegalStateException("Not enough room in this slot to fit the event: '" + event.getName() + "'");
	}

	public boolean hasRoomFor(Event event) {
		return remainingDurationInMinutes >= event.getDurationInMinutes();
	}
	
	@Override
	public String toString() {
		String print = name + "\n";
		
		for (Event event : events) {
			print = print + event + "\n";
		}
		
		return print;
	}
}
