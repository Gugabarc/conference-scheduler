package com.company.conference_scheduler.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.size;

import org.apache.commons.lang3.BooleanUtils;
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
		LocalTime lastSlotTime = null;
		
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
							
							lastSlotTime = currentTime;
						}
					}
				}
				
				if(slot.isLast()) {
					slot.setStartTime(lastSlotTime);
				}
			}
			System.out.println(track);
		}
		
		log.info("Events scheduled");
		
		return tracks;
	}
	
	public List<Track> createSlotsAndTracks() {
		log.info("Creating tracks and slots");
		List<Track> tracks = new ArrayList<>();
		
		for(int t = 0; t < properties.getTracksNames().length; t++) {
			Track track = new Track();
			track.setName(properties.getTracksNames()[t]);
			
			for(int s = 0; s < properties.getSlotsName().length; s++) {
				Slot slot = new Slot();
				slot.setName(properties.getSlotsName()[s]);
				slot.setSlotDurationInMinutes(properties.getSlotsDurationInMinutes()[s]);
				slot.setRemainingDurationInMinutes(properties.getSlotsDurationInMinutes()[s]);
				slot.setHasEvents(BooleanUtils.toBoolean(properties.getSlotsHasEvents()[s]));
				slot.setStartTime(LocalTime.of(properties.getSlotsStartHour()[s],properties.getSlotsStartMinute()[s]));
				
				track.addSlot(slot);
			}
			
			Slot slot = new Slot();
			slot.setName(properties.getFinalSlotName());
			slot.setHasEvents(false);
			slot.setLast(true);
			
			track.addSlot(slot);
			
			tracks.add(track);
		}
		
		log.info("Created {} tracks", size(tracks));
		
		return tracks;
	}
	
}
