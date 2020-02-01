package com.bridgelabz.fundoo.repository;

import com.bridgelabz.fundoo.model.Note;

public interface INoteRepository {

	public Note saveOrUpdate(Note newNote);

	public Note getNote(long noteId);

	public boolean isDeletedNote(long noteId);

}