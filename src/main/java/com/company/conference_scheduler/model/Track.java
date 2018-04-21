package com.company.conference_scheduler.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Track {
	private final long id;
	private String name;
	private int durationInMinutes;
	private boolean scheduled;
}
