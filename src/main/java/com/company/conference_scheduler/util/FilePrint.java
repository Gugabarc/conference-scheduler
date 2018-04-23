package com.company.conference_scheduler.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.conference_scheduler.common.PropertyValue;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@Setter
@Slf4j
public class FilePrint {
	
	@Autowired
	private PropertyValue properties;
	
	public void printToFile(List<String> strings) {
		log.info("Printing to file. Path: {}", properties.getOutputFilePath());
		Path path = Paths.get(properties.getOutputFilePath());
		
		try {
			Files.write(path, strings);
		} catch (IOException e) {
			log.error("Error printing to file", e);
		}
	}
	
}
