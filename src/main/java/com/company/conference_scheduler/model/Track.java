package com.company.conference_scheduler.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Track {
	private String name;
	
	@Builder.Default
	private List<Slot> slots = new ArrayList<Slot>();
	
	public void addSlots(List<Slot> slot) {
		slots.addAll(slot);
	}
	
	public void addSlot(Slot slot) {
		slots.add(slot);
	}

	public String toString() {
		String print = name + "\n";
		for (Slot slot : slots) {
			print += slot;
		}
		print += "\n";
		return print;
	}
}
