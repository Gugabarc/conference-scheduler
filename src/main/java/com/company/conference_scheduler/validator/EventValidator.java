package com.company.conference_scheduler.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.conference_scheduler.config.PropertyValue;
import com.company.conference_scheduler.model.Error;
import com.company.conference_scheduler.model.Event;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EventValidator implements Validator<Event> {
	
	@Autowired
	private PropertyValue properties;

	@Override
	public List<Error> validate(Event event) {
		List<Error> errors = new ArrayList<>();
		
		if(event == null) {
			log.error("Event object is null");
			
            return Arrays.asList(Error.builder()
			            			.message("Event object is null")
			            			.build());
		} 
		
		if (event.getDurationInMinutes() > properties.getMaxTrackDurationInMinutes()) {
            log.error("Duration of event '{}' is more than the maximum duration allowed for an track, that is {}.", event.getName(), properties.getMaxTrackDurationInMinutes());
            
            errors.add(Error.builder()
            			.message("Exceeded maximum duration allowed of " + properties.getMaxTrackDurationInMinutes() + " minutes")
            			.build());
        }
		
		if(StringUtils.isBlank(event.getName())) {
			log.error("Event's name is blank");
			
            errors.add(Error.builder()
            			.message("Event's name is blank")
            			.build());
		}
		
		return errors;
	}

}
