package com.company.conference_scheduler.parser;

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

import com.company.conference_scheduler.common.PropertyValue;
import com.company.conference_scheduler.model.Event;

@RunWith(Theories.class)
public class FileEventParserTest {
	
	private static final String INPUT_FILE_TEST_PATH = "./src/test/resources/inputTest.txt";
	
	@DataPoints("invalidLines")  
	public static final String[] INVALID_LINES = {"", " ", "abcd", "JUnit for everybody 30", "JUnit for everybody lightnin"};
	
	@Mock
	private PropertyValue properties;
	
	@InjectMocks
	private FileEventParser fileEventParser;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(properties.getLinePattern()).thenReturn("^(.+)\\s(\\d+)?((min)|(lightning))$");
		Mockito.when(properties.getLightningString()).thenReturn("lightning");
		Mockito.when(properties.getLightningDurationInMinutes()).thenReturn(5);
		Mockito.when(properties.getEventNameGroupIndex()).thenReturn(1);
		Mockito.when(properties.getEventDurationGroupIndex()).thenReturn(2);
		Mockito.when(properties.getEventLightningGroupIndex()).thenReturn(3);
	}
	
	@Test
	public void readInput_success() {
		List<String> readInput = fileEventParser.readInput(INPUT_FILE_TEST_PATH);
		
		assertThat(readInput).hasSize(19)
								.contains("Rails for Python Developers lightning")
								.contains("Ruby on Rails Legacy App Maintenance 60min")
								.contains("Common Ruby Errors 45min");
	}
	
	@Test
	public void parseLineToEvent_lightning_success() {
		Event eventParsed = fileEventParser.parseLineToEvent("Rails for Python Developers lightning");
		
		Event eventExpected = Event.builder()
									.name("Rails for Python Developers")
									.duration(Duration.ofMinutes(properties.getLightningDurationInMinutes()))
									.build();						
		
		assertThat(eventParsed).isEqualTo(eventExpected);
	}
	
	@Test
	public void parseLineToEvent_success() {
		int durationInMinutes = 30;
		Event eventParsed = fileEventParser.parseLineToEvent("Rails for Python Developers " + durationInMinutes + "min");
		
		Event eventExpected = Event.builder()
									.name("Rails for Python Developers")
									.duration(Duration.ofMinutes(durationInMinutes))
									.build();						
		
		assertThat(eventParsed).isEqualTo(eventExpected);
	}
	
	@Test
	public void parseLineToEvent_withNullParameter() {
		Event eventParsed = fileEventParser.parseLineToEvent(null);
		
		assertThat(eventParsed).isNull();
	}
	
	@Test
	@Theory
	public void parseLineToEvent_withInvalidLine(@FromDataPoints("invalidLines") String invalidLine) {
		Event eventParsed = fileEventParser.parseLineToEvent(invalidLine);
		
		assertThat(eventParsed).isNull();
	}
}
