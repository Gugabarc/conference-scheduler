package com.company.conference_scheduler.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Test;

import com.company.conference_scheduler.model.Event;
import com.company.conference_scheduler.model.Slot;
import com.company.conference_scheduler.model.Track;

public class TrackToStringConverterTest {
	
	private TrackToStringConverter converter = new TrackToStringConverter();
	
	private final String[] eventsName = {"Junit test for noobies", "Learning Mockito"};
	private final String[] tracksName = {"Track 1", "Track 2"};
	private final String[] slotNames = {"Morning", "Lunch"};
	
	@Test
	public void convertTracksToStringList_withoutSlotName_success() {
		Event event = Event.builder()
							.name(eventsName[0])
							.startTime(LocalTime.of(9, 00))
							.duration(Duration.ofMinutes(45))
							.endtTime(LocalTime.of(9, 45))
							.build();
		
		Event event2 = Event.builder()
						.name(eventsName[1])
						.startTime(LocalTime.of(10, 00))
						.duration(Duration.ofMinutes(30))
						.endtTime(LocalTime.of(10, 30))
						.build();
		
		Slot slot = Slot.builder()
							.events(Lists.newArrayList(event, event2))
							.startTime(LocalTime.of(9, 00))
							.build();
	
		Track track1 = Track.builder()
								.name(tracksName[0])
								.slots(Lists.newArrayList(slot))
								.build();
		
		Track track2 = Track.builder()
				.name(tracksName[1])
				.slots(Lists.newArrayList(slot))
				.build();
		
		List<String> strings = converter.convertTracksToStringList(Lists.newArrayList(track1, track2));
		
		String track1String = tracksName[0] 
									+ "\n\n"
									+ "09:00 - 09:45 " + eventsName[0] + "\n"
									+ "10:00 - 10:30 " + eventsName[1] + "\n";
		
		String track2String = tracksName[1] 
									+ "\n\n"
									+ "09:00 - 09:45 " + eventsName[0] + "\n"
									+ "10:00 - 10:30 " + eventsName[1] + "\n";
		
		assertThat(strings).containsOnly(track1String, track2String);
	}
	
	@Test
	public void convertTracksToStringList_withSlotName_success() {
		Event event = Event.builder()
							.name(eventsName[0])
							.startTime(LocalTime.of(9, 00))
							.duration(Duration.ofMinutes(45))
							.endtTime(LocalTime.of(9, 45))
							.build();
		
		Slot slot = Slot.builder()
							.name(slotNames[0])
							.events(Lists.newArrayList(event))
							.startTime(LocalTime.of(9, 00))
							.duration(Duration.ofMinutes(60))
							.build();	
		
		Track trackOne = Track.builder()
								.name(tracksName[0])
								.slots(Lists.newArrayList(slot))
								.build();
		
		List<String> strings = converter.convertTracksToStringList(Lists.newArrayList(trackOne));
		
		String trackOneString = tracksName[0] + "\n\n09:00 - 10:00 " + slotNames[0] + "\n09:00 - 09:45 " + eventsName[0] + "\n";
		
		assertThat(strings).contains(trackOneString);
	}
}
