package com.company.conference_scheduler.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Track {
	private String name;
	private List<Slot> slots;
	
	public Track() {
		slots = new ArrayList<Slot>();
	}

	public void addSlots(List<Slot> slot) {
		slots.addAll(slot);
	}
	
	public void addSlot(Slot slot) {
		slots.add(slot);
	}

	public String toString() {
		String print = name + "\n";
		for (Slot slot : slots) {
			print = print + slot + "\n";
		}
		return print;
	}
}
