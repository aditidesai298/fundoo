package com.bridgelabz.fundoo.repository;

import java.util.List;

import com.bridgelabz.fundoo.model.Note;

public interface INoteRepository {

	public Note saveOrUpdate(Note newNote);

	public Note getNote(long noteId);

	public boolean isDeletedNote(long noteId);

	public List<Note> getAllNotes(long userId);

	public List<Note> getAllTrashedNotes(long userId);

	public List<Note> getAllPinnedNotes(long userId);

	public List<Note> getAllArchivedNotes(long userId);

	public List<Note> searchBy(String noteTitle);

}