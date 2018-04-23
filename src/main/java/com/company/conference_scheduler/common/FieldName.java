package com.company.conference_scheduler.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class FieldName {
	
	@Value("${fieldname.name:name}")
	private String nameFieldName;
	
	@Value("${fieldname.duration:duration}")
	private String durationFieldName;
	
}
