package com.bridgelabz.fundoo.service;

import com.bridgelabz.fundoo.model.NoteDto;

public interface INoteService {

	public boolean createNote(NoteDto noteDto, String token);

	public boolean deleteNote(long noteId, String token);

	public boolean updateNote(NoteDto noteDto, long noteId, String token);

}