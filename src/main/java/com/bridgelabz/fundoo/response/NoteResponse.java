package com.bridgelabz.fundoo.response;

import com.bridgelabz.fundoo.model.NoteDto;

public class NoteResponse {

	private NoteDto note;

	public NoteResponse(NoteDto note) {
		this.setNote(note);
	}

	public NoteDto getNote() {
		return note;
	}

	public void setNote(NoteDto note) {
		this.note = note;
	}
}