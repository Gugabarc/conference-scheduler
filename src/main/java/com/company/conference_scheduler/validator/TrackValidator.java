package com.company.conference_scheduler.validator;

import org.springframework.stereotype.Component;

import com.company.conference_scheduler.model.ErrorList;
import com.company.conference_scheduler.model.Track;

@Component
public class TrackValidator implements Validator<Track> {

	@Override
	public ErrorList validate(Track track) {
		// TODO Auto-generated method stub
		return null;
	}

}
