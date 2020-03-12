package com.bridgelabz.fundoo.repository;

import java.util.List;

import com.bridgelabz.fundoo.model.Note;
import com.bridgelabz.fundoo.util.NoteData;

public interface INoteRepository {

	public Note saveOrUpdate(Note newNote);

	public Note getNote(long noteId);

	public boolean deleteNote(long noteId);

	public List<Note> getAllNotes(long userId);
	public List<Note> getTrashed(long userId);
	public List<Note> getPinned(long userId);


}