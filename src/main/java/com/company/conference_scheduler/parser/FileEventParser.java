package com.company.conference_scheduler.parser;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.company.conference_scheduler.common.PropertyValue;
import com.company.conference_scheduler.model.Error;
import com.company.conference_scheduler.model.Event;
import com.company.conference_scheduler.validator.EventValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("fileEventParser")
public class FileEventParser implements EventParser {
	
	@Value("${default.file.full.path}")
	private String filename;
	
	@Autowired
	private EventValidator eventValidator;
	
	@Autowired
	private PropertyValue properties;
	
	@Override
	public List<Event> getEvents() {
		List<String> fileLines = readInput(filename);
		List<Event> events = new ArrayList<>();
		
		for (String line : fileLines) {
			Event event = parseLineToEvent(line);
			List<Error> errors = eventValidator.validate(event);
			
			if(CollectionUtils.isNotEmpty(errors)) {
				log.error("Error in event data. Event: {}. Errors: {}", event, errors);
				throw new RuntimeException();
			}
			
			events.add(event);
		}
		
		return events;
	}
	
    protected Event parseLineToEvent(String line) {
    	log.debug("Parsing line with pattern '{}' - '{}'", properties.getLinePattern(), line);
        
    	if (isBlank(line)) {
            return null;
        }

        Pattern pattern = Pattern.compile(properties.getLinePattern());
        Matcher match = pattern.matcher(line);
        
        if (match.find() == false) {
            log.warn("Invalid input line: {}", line);
            return null;
        }

        String name = match.group(properties.getEventNameGroupIndex());
        String durationInString = match.group(properties.getEventDurationGroupIndex());
        int duration = 0;
        
        if (isNotBlank(durationInString)) {
            duration = Integer.parseInt(durationInString);
        } else if(StringUtils.equals(match.group(properties.getEventLightningGroupIndex()), properties.getLightningString())) {
        	duration = properties.getLightningDurationInMinutes();
        }
        
        Event event = Event.builder()
        					.name(name)
        					.duration(Duration.ofMinutes(duration))
        					.build();
        
        log.debug("Created event {}", event);
        
        return event;
    }
	
	public List<String> readInput(String filename) {
		if (isBlank(filename)) {
			log.error("Filename is blank");
			throw new IllegalArgumentException("Filename is blank");
		}
		
		log.info("Reading file {}", filename);

		List<String> fileLines = new ArrayList<String>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			
			String strLine = null;
			while (isNotBlank(strLine = br.readLine())) {
				fileLines.add(strLine);
			}

		} catch (Exception e) {
			log.error("Error reading line", e);
			throw new RuntimeException(e);
		}
		
		log.info("Readed {} lines", CollectionUtils.size(fileLines));
		
		return fileLines;
	}

	
}
