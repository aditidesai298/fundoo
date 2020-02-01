package com.bridgelabz.fundoo.repository;

import java.util.List;

import com.bridgelabz.fundoo.model.Note;

public interface INoteRepository {

	public Note saveOrUpdate(Note newNote);

	public Note getNote(long noteId);

	public boolean isDeletedNote(long noteId);

	public List<Note> getAllNotes(long userId);


}