package com.company.conference_scheduler.validator;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.company.conference_scheduler.common.ErrorMessage;
import com.company.conference_scheduler.common.FieldName;
import com.company.conference_scheduler.common.PropertyValue;
import com.company.conference_scheduler.model.Error;
import com.company.conference_scheduler.model.Event;

@RunWith(Theories.class)
public class EventValidatorTest {
	
	@DataPoints("invalidNames")  
	public static final String[] INVALID_LINES = {"", " ", null};
	
	@DataPoints("invalidDurationInMinutes")  
	public static final int[] INVALID_DURATION_IN_MINUTES = {110, 200, 300, 1000, 9999999};
	
	@Mock
	private FieldName fieldName;
	
	@Mock
	private ErrorMessage errorMessage;
	
	@Mock
	private PropertyValue properties;
	
	@InjectMocks
	private EventValidator validator;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(properties.getMaxTrackDurationInMinutes()).thenReturn(100);
		Mockito.when(errorMessage.getEventNameIsBlank()).thenReturn("Event's name is blank");
		Mockito.when(errorMessage.getEventExceededMaxDuration()).thenReturn("Exceeded maximum duration allowed of X minutes");
		Mockito.when(fieldName.getNameFieldName()).thenReturn("name");
		Mockito.when(fieldName.getDurationFieldName()).thenReturn("duration");
	}
	
	@Test
	public void validate_success() {
		Event event = Event.builder()
							.name("Event's name")
							.duration(Duration.ofMinutes(20))
							.build();
		
		List<Error> errors = validator.validate(event);
		
		assertThat(errors).isEmpty();
	}
	
	@Test
	@Theory
	public void validate_withInvalidName(@FromDataPoints("invalidNames") String invalidName) {
		Event event = Event.builder()
							.name(invalidName)
							.duration(Duration.ofMinutes(20))
							.build();
		
		List<Error> errors = validator.validate(event);
		
		Error error = Error.builder()
							.message(errorMessage.getEventNameIsBlank())
							.fieldName(fieldName.getNameFieldName())
							.build();
		
		assertThat(errors).contains(error);
		
	}
	
	@Test
	@Theory
	public void validate_withInvalidDuration(@FromDataPoints("invalidDurationInMinutes") int invalidDuration) {
		Event event = Event.builder()
							.name("Event's name for test")
							.duration(Duration.ofMinutes(invalidDuration))
							.build();
		
		List<Error> errors = validator.validate(event);
		
		Error error = Error.builder()
							.message(errorMessage.getEventExceededMaxDuration())
							.fieldName(fieldName.getDurationFieldName())
							.build();
		
		assertThat(errors).contains(error);
		
	}

}
