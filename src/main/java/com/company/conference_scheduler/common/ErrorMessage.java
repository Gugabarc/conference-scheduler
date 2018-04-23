package com.company.conference_scheduler.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class ErrorMessage {
	
	@Value("${event.name.is.blank}")
	private String eventNameIsBlank;
	
	@Value("${event.exceeded.max.duration}")
	private String eventExceededMaxDuration;
	
}
