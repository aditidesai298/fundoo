package com.bridgelabz.fundoo.model;

import java.time.LocalDateTime;


public class ReminderDto {

	private LocalDateTime remainder;


	public LocalDateTime getRemainder() {
		return remainder;
	}

	public void setRemainder(LocalDateTime remainder) {
		this.remainder = remainder;
	}


	@Override
	public String toString() {
		return "RemainderDTO [remainder=" + remainder + "]";
	}

}