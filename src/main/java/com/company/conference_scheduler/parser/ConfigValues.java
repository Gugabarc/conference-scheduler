package com.company.conference_scheduler.parser;

import java.util.regex.Pattern;

public interface ConfigValues {
	public static final String LUNCH_SLOT_NAME = "Lunch";
    public static final String NETWORKING_TRACK_NAME = "Networking";
	public static final String LIGHTNING_STRING = "lightning";
	
	public static final String LIGHTNING_DURATION_IN_MINUTES_STRING = "5";
	public static final String ZERO_MINUTE_STRING = "0";
	
	public static final int MAX_TRACK_DURATION_IN_MINUTES = 240;
	
	public static final Pattern LINE_PATTERN = Pattern.compile("^(.+)\\s(\\d+)?\\s?((min)|(lightning))$");
	
    public static final int TRACK_NAME_GROUP_INDEX = 1;
    public static final int TRACK_DURATION_GROUP_INDEX = 2;

    public static final int MORNING_SLOT_DURATION_IN_MINUTES = 180;
    public static final int LUNCH_SLOT_DURATION_IN_MINUTED = 60;
    public static final int AFTERNOON_SLOT_DURATION_IN_MINUTES = 240;
    
}
