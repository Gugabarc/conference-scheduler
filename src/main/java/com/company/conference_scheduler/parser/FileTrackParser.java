package com.company.conference_scheduler.parser;

import static com.company.conference_scheduler.parser.ConfigValues.LIGHTNING_DURATION_IN_MINUTES_STRING;
import static com.company.conference_scheduler.parser.ConfigValues.LIGHTNING_STRING;
import static com.company.conference_scheduler.parser.ConfigValues.LINE_PATTERN;
import static com.company.conference_scheduler.parser.ConfigValues.TRACK_DURATION_GROUP_INDEX;
import static com.company.conference_scheduler.parser.ConfigValues.TRACK_NAME_GROUP_INDEX;
import static com.company.conference_scheduler.parser.ConfigValues.ZERO_MINUTE_STRING;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.company.conference_scheduler.model.Error;
import com.company.conference_scheduler.model.Track;
import com.company.conference_scheduler.validator.TrackValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("fileTrackParser")
public class FileTrackParser implements TrackParser {
	
	@Value("${default.file.full.path}")
	private String filename;
	
	@Autowired
	private TrackValidator trackValidator;
	
	@Override
	public List<Track> getTracks() {
		List<String> fileLines = readInput(filename);
		List<Track> tracks = new ArrayList<>();
		for (String line : fileLines) {
			Track track = parseLine(line);
			List<Error> errors = trackValidator.validate(track);
			
			if(CollectionUtils.isNotEmpty(errors)) {
				throw new RuntimeException();
			}
		}
		
		return tracks;
	}
	
    private Track parseLine(String line) {
        if (isBlank(line)) {
            return null;
        }

        Matcher match = LINE_PATTERN.matcher(line);
        if (match.find() == false) {
            log.warn("Invalid input line: " + line);
            return null;
        }

        String name = match.group(TRACK_NAME_GROUP_INDEX);
        String durationInString = match.group(TRACK_DURATION_GROUP_INDEX);
        
        if (isBlank(durationInString)) {
            durationInString = ZERO_MINUTE_STRING;
        } else if(StringUtils.equals(durationInString, LIGHTNING_STRING)) {
        	durationInString = LIGHTNING_DURATION_IN_MINUTES_STRING;
        }
        
        int duration = Integer.parseInt(durationInString);

        Track track = Track.builder()
        					.name(name)
        					.durationInMinutes(duration)
        					.build();

        return track;
    }
	
	public List<String> readInput(String filename) {
		if (isBlank(filename)) {
			log.error("Filename is blank");
			throw new IllegalArgumentException("Filename is blank");
		}

		List<String> fileLineslist = new ArrayList<String>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			
			String strLine = null;
			while (isNotBlank(strLine = br.readLine())) {
				fileLineslist.add(strLine);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		}
		
		return fileLineslist;
	}

	
}
