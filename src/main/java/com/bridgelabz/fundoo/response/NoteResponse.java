package com.bridgelabz.fundoo.response;



import com.bridgelabz.fundoo.model.NoteDto;

import lombok.Data;

@Data
public class NoteResponse {

	private NoteDto note;

	public NoteResponse(NoteDto note) {
		this.note = note;
	}
}