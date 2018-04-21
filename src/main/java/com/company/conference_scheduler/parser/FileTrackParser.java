package com.company.conference_scheduler.parser;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.company.conference_scheduler.model.Track;
import com.company.conference_scheduler.validator.TrackValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("fileTrackParser")
public class FileTrackParser implements TrackParser {
	
	@Value("default.file.full.path")
	private String filename;
	
	@Autowired
	private TrackValidator trackValidator;
	
	@Override
	public List<Track> getTracks() {
		List<String> fileLineList = readInput(filename);
		log.info(fileLineList.toString());
		System.out.println(fileLineList);
		return null;
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
