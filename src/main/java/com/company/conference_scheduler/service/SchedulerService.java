package com.company.conference_scheduler.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.size;
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
	
	public List<Track> schedule() {
		List<Event> events = eventParser.getEvents();
		List<Track> tracks = createSlotsAndTracks();
		
		log.info("Ordering and scheduling events. There are {} tracks and {} events", size(tracks), size(events));
		
		events.sort((Event e1, Event e2) -> e2.getDurationInMinutes() - e1.getDurationInMinutes());
		
		for (Track track : tracks) {
			
			for (Slot slot : track.getSlots()) {
				
				if(slot.isHasEvents()) {
					LocalTime currentTime = slot.getStartTime();
					
					for (Event event : events) {
						if(event.isScheduled() == false && slot.getRemainingDurationInMinutes() >= event.getDurationInMinutes()) {
							event.setScheduled(true);
							event.setStartTime(currentTime);
							currentTime = currentTime.plusMinutes(event.getDurationInMinutes());
							event.setEndtTime(currentTime);
							slot.addEvent(event);
						}
					}
				}
			}
		}
		
		log.info("Events scheduled");
		
		return tracks;
	}
	
	public List<Track> createSlotsAndTracks() {
		log.info("Creating tracks and slots");
		List<Track> tracks = new ArrayList<>();
		for(int i = 0; i < properties.getConferenceDurationInDays(); i++) {
			Track track = new Track();
		
			Slot slot = new Slot();
			slot.setName("Morning");
			slot.setSlotDurationInMinutes(180);
			slot.setRemainingDurationInMinutes(180);
			slot.setHasEvents(true);
			slot.setStartTime(LocalTime.of(9,0));
			
			track.addSlot(slot);
			
			Slot slot2 = new Slot();
			slot2.setName("Lunch");
			slot2.setSlotDurationInMinutes(60);
			slot2.setRemainingDurationInMinutes(60);
			slot2.setHasEvents(false);
			slot2.setStartTime(LocalTime.of(12,0));
			
			track.addSlot(slot2);
			
			Slot slot3 = new Slot();
			slot3.setName("Afternoon");
			slot3.setSlotDurationInMinutes(240);
			slot3.setRemainingDurationInMinutes(240);
			slot3.setHasEvents(true);
			slot3.setStartTime(LocalTime.of(13,0));
			
			track.addSlot(slot3);
			
			tracks.add(track);
		}
		
		log.info("Created {} tracks", size(tracks));
		
		return tracks;
	}

}
