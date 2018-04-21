package com.company.conference_scheduler.validator;

import static com.company.conference_scheduler.parser.ConfigValues.MAX_TRACK_DURATION_IN_MINUTES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.company.conference_scheduler.model.Error;
import com.company.conference_scheduler.model.Track;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TrackValidator implements Validator<Track> {

	@Override
	public List<Error> validate(Track track) {
		List<Error> errors = new ArrayList<>();
		
		if(track == null) {
			log.error("Track object is null");
			
            return Arrays.asList(Error.builder()
			            			.message("Track object is null")
			            			.build());
		} 
		
		if (track.getDurationInMinutes() > MAX_TRACK_DURATION_IN_MINUTES) {
            log.error("Duration of track '{}' is more than the maximum duration allowed for an TRACK, that is {}.", track.getName(), MAX_TRACK_DURATION_IN_MINUTES);
            
            errors.add(Error.builder()
            			.message("Exceeded maximum duration allowed of " + MAX_TRACK_DURATION_IN_MINUTES + "minutes")
            			.build());
        }
		
		return errors;
	}

}
