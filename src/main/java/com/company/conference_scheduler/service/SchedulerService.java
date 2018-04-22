package com.company.conference_scheduler.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.company.conference_scheduler.config.PropertyValue;
import com.company.conference_scheduler.model.Event;
import com.company.conference_scheduler.model.Slot;
import com.company.conference_scheduler.model.Track;
import com.company.conference_scheduler.parser.EventParser;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@Setter
@Slf4j
public class SchedulerService {
	
	@Autowired
	@Qualifier("fileEventParser")
	private EventParser eventParser;
	
	@Autowired
	private PropertyValue properties;
	
	public void schedule() {
		List<Event> events = eventParser.getEvents();
		List<Track> tracks = setupSlotsAndTracks();
		
		for (Track track : tracks) {
			for (Slot slot : track.getSlots()) {
				if(slot.isHasEvents()) {
					for (Event event : events) {
						if(event.isScheduled() == false && slot.getRemainingDurationInMinutes() > 0) {
							slot.addEvent(event);
							event.setScheduled(true);
						}
					}
				}
			}
		}
		
		for (Track track : tracks) {
			System.out.println(track);
		}
	}
	
	public List<Track> setupSlotsAndTracks() {
		List<Track> tracks = new ArrayList<>();
		for(int i = 0; i < properties.getConferenceDurationInDays(); i++) {
			Track track = new Track();
		
			Slot slot = new Slot();
			slot.setName("Morning");
			slot.setSlotDurationInMinutes(180);
			slot.setRemainingDurationInMinutes(180);
			slot.setHasEvents(true);
			
			track.addSlot(slot);
			
			Slot slot2 = new Slot();
			slot2.setName("Lunch");
			slot2.setSlotDurationInMinutes(60);
			slot2.setRemainingDurationInMinutes(60);
			slot2.setHasEvents(false);
			
			track.addSlot(slot2);
			
			Slot slot3 = new Slot();
			slot3.setName("Afternoon");
			slot3.setSlotDurationInMinutes(240);
			slot3.setRemainingDurationInMinutes(240);
			slot3.setHasEvents(true);
			
			track.addSlot(slot3);
			
			tracks.add(track);
		}
		
		return tracks;
	}

}
