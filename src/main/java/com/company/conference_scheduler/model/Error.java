package com.company.conference_scheduler.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Error {
	
	private int code;
	private String message;
	private String fieldName;

}
