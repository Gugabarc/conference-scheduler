package com.company.conference_scheduler.validator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.conference_scheduler.common.ErrorMessage;
import com.company.conference_scheduler.common.FieldName;
import com.company.conference_scheduler.common.PropertyValue;
import com.company.conference_scheduler.model.Error;
import com.company.conference_scheduler.model.Event;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EventValidator implements Validator<Event> {
	
	@Autowired
	private PropertyValue properties;
	
	@Autowired
	private ErrorMessage errorMessage;
	
	@Autowired
	private FieldName fieldName;

	@Override
	public List<Error> validate(Event event) {
		List<Error> errors = new ArrayList<>();
		
		if(event == null) {
			log.error("Event object is null");
			throw new IllegalArgumentException("Event object is null");
		} 
		
		if (event.getDuration().toMinutes() > properties.getMaxTrackDurationInMinutes()) {
            log.error("Duration of event '{}' is more than the maximum duration allowed for an track, that is {}.", event.getName(), properties.getMaxTrackDurationInMinutes());
            
            errors.add(Error.builder()
            			.message(errorMessage.getEventExceededMaxDuration())
            			.fieldName(fieldName.getDurationFieldName())
            			.build());
        }
		
		if(StringUtils.isBlank(event.getName())) {
			log.error(errorMessage.getEventNameIsBlank());
			
            errors.add(Error.builder()
            			.message(errorMessage.getEventNameIsBlank())
            			.fieldName(fieldName.getNameFieldName())
            			.build());
		}
		
		return errors;
	}

}
