package com.bridgelabz.fundoo.service;

import java.util.List;

import com.bridgelabz.fundoo.model.Note;
import com.bridgelabz.fundoo.model.NoteDto;
import com.bridgelabz.fundoo.model.ReminderDto;

public interface INoteService {

	public boolean createNote(NoteDto noteDto, String token);

	public boolean deleteNote(long noteId, String token);

	public boolean updateNote(NoteDto noteDto, long noteId, String token);

	public List<Note> getallNotes(String token);

	public boolean archiveNote(long noteId, String token);

	public boolean isPinnedNote(long noteId, String token);

	public void changeColour(String token, long noteId, String noteColor);

	public boolean trashNote(long noteId, String token);

	public void setNoteReminder(String token, long noteId, ReminderDto remainderDTO);

}