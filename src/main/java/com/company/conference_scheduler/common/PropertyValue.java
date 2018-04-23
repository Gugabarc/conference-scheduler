package com.company.conference_scheduler.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class PropertyValue {
	
	@Value("${max.track.duration.in.minutes:240}")
	private int maxTrackDurationInMinutes;
	
	@Value("${lightning.duration.in.minutes:5}")
	private int lightningDurationInMinutes;
	
	@Value("${line.pattern:'^(.+)\\s(\\d+)?((min)|(lightning))$'}")
	private String linePattern;
	
	private final String lightningString = "lightning";
	
	private final int eventNameGroupIndex = 1;
	private final int eventDurationGroupIndex = 2;
	private final int eventLightningGroupIndex = 3;
	
	@Value("${tracks.name:['Track 1','Track 2']}")
	private String[] tracksNames;
	
	@Value("${slots.name:[null,Lunch,null]}")
	private String[] slotsName;
	
	@Value("${slots.duration.in.minutes:[180,60,240]}")
	private int[] slotsDurationInMinutes;
	
	@Value("${slots.has.events:[true,false,true]}")
	private String[] slotsHasEvents;
	
	@Value("${slots.start.hour:[9,12,13]}")
	private int[] slotsStartHour;
	
	@Value("${slots.start.minute:[0,0,0]}")
	private int[] slotsStartMinute;
	
	@Value("${final.slot.name:Networking}")
	private String finalSlotName;
	
	@Value("${output.file.path:'./output/conference-tracks.txt'}")
	private String outputFilePath;
}
