package com.company.conference_scheduler.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.company.conference_scheduler.model.Track;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TrackToStringConverter {
	
	public List<String> convertTracksToStringList(List<Track> tracks){
		log.info("Converting tracks to a list of strings");
		
		List<String> tracksAsString = new ArrayList<>();
		
		for (Track track : tracks) {
			tracksAsString.add(track.toString());
		}
		
		log.info("Converted");
		
		return tracksAsString;
	}

}
