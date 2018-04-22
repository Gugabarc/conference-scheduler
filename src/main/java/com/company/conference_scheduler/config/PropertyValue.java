package com.company.conference_scheduler.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class PropertyValue {
	
	@Value("${conference.duration.in.days:2}")
	private int conferenceDurationInDays;
	
	@Value("${max.track.duration.in.minutes:240}")
	private int maxTrackDurationInMinutes;
	
	@Value("${final.slot.name:Networking}")
	private String finalSlotName;
	
	@Value("${lightning.duration.in.minutes:5}")
	private int lightningDurationInMinutes;
	
	@Value("${line.pattern:'^(.+)\\s(\\d+)?((min)|(lightning))$'}")
	private String linePattern;
	
	private final String lightningString = "lightning";
	
	private final int eventNameGroupIndex = 1;
	private final int eventDurationGroupIndex = 2;
	private final int eventLightningGroupIndex = 3;
}
