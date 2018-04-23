package com.company.conference_scheduler.service;

import static org.apache.commons.collections.CollectionUtils.size;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.company.conference_scheduler.common.PropertyValue;
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
	@Qualifier("eventParserAlias")
	private EventParser eventParser;
	
	@Autowired
	private PropertyValue properties;
	
	public List<Track> schedule() {
		List<Event> events = eventParser.getEvents();
		List<Track> tracks = createTracks();
		LocalTime lastSlotTime = null;
		
		log.info("Ordering and scheduling events. There are {} tracks and {} events", size(tracks), size(events));
		
		events.sort((Event e1, Event e2) -> e2.getDuration().compareTo(e1.getDuration()));
		
		for (Track track : tracks) {
			
			for (Slot slot : track.getSlots()) {
				
				if(slot.isHasEvents()) {
					LocalTime currentTime = slot.getStartTime();
					
					for (Event event : events) {
						if(slot.hasRoomFor(event)) {
							event.setStartTime(currentTime);
							currentTime = currentTime.plusMinutes(event.getDuration().toMinutes());
							event.setEndtTime(currentTime);
							
							slot.addEvent(event);
							
							event.setScheduled(true);
							lastSlotTime = currentTime;
						}
					}
				}
				
				if(slot.isLast()) {
					slot.setStartTime(lastSlotTime);
				}
			}
		}
		
		log.info("Events scheduled");
		
		return tracks;
	}

	public List<Track> createTracks() {
		log.info("Creating tracks and slots");
		List<Track> tracks = new ArrayList<>();
		
		for(int t = 0; t < properties.getTracksNames().length; t++) {
			Track track = Track.builder()
								.name(properties.getTracksNames()[t])
								.build();
			
			List<Slot> slots = createSlots(track);
			track.addSlots(slots);
			
			Slot slot = Slot.builder()
							.name(properties.getFinalSlotName())
							.hasEvents(false)
							.isLast(true)
							.build();
			
			track.addSlot(slot);
			
			tracks.add(track);
		}
		
		log.info("Created {} tracks", size(tracks));
		
		return tracks;
	}

	private List<Slot> createSlots(Track track) {
		List<Slot> slots = new ArrayList<>();
		for(int s = 0; s < properties.getSlotsName().length; s++) {
			Slot slot = createSlot(s);
			
			slots.add(slot);
		}
		
		return slots;
	}

	private Slot createSlot(int arrayIndex) {
		Slot slot = Slot.builder()
						.name(properties.getSlotsName()[arrayIndex])
						.duration(Duration.ofMinutes(properties.getSlotsDurationInMinutes()[arrayIndex]))
						.remainingDurationInMinutes(properties.getSlotsDurationInMinutes()[arrayIndex])
						.hasEvents(BooleanUtils.toBoolean(properties.getSlotsHasEvents()[arrayIndex]))
						.startTime(LocalTime.of(properties.getSlotsStartHour()[arrayIndex],properties.getSlotsStartMinute()[arrayIndex]))
						.build();
		
		return slot;
	}
	
}
