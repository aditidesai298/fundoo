package com.bridgelabz.fundoo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.exception.AuthorizationException;
import com.bridgelabz.fundoo.exception.NoteException;
import com.bridgelabz.fundoo.model.Note;
import com.bridgelabz.fundoo.model.NoteDto;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.INoteRepository;
import com.bridgelabz.fundoo.repository.IUserRepository;
import com.bridgelabz.fundoo.util.JwtGenerator;

@Service
public class Noteservice implements INoteService{
	
	
	@Autowired
	private IUserRepository urepo;
	@Autowired
	private INoteRepository nrepo;
	@Autowired
	private JwtGenerator tokenobj;
	
	private User authenticatedUser(String token) {
		User fetchedUser = urepo.getUser(tokenobj.decodeToken(token));
		if (fetchedUser != null) {
			return fetchedUser;
		}
		throw new AuthorizationException("Authorization failed", 401);
	}
	
	@Override
	public boolean createNote(NoteDto noteDto, String token) {
		// found authorized user
		User fetchedUser = authenticatedUser(token);
		Note newNote = new Note();
		BeanUtils.copyProperties(noteDto, newNote);
		newNote.setCreatedDate(LocalDateTime.now());
		newNote.setColor("white");
		fetchedUser.getNotes().add(newNote);
		nrepo.saveOrUpdate(newNote);
		return true;
	}
	private Note verifiedNote(long noteId) {
		Note fetchedNote = nrepo.getNote(noteId);
		if (fetchedNote != null) {
			return fetchedNote;
		}
		throw new NoteException("Not not found", 300);
	}
	
	@Override
	public boolean deleteNote(long noteId, String token) {
		// found authorized user
		authenticatedUser(token);
		// verified valid note
		verifiedNote(noteId);
		nrepo.isDeletedNote(noteId);
		return true;
	}
	
	@Override
	public boolean updateNote(NoteDto noteDto, long noteId, String token) {
		// found authorized user
		authenticatedUser(token);
		// verified valid note
		Note fetchedNote = verifiedNote(noteId);
		BeanUtils.copyProperties(noteDto, fetchedNote);
		fetchedNote.setUpdatedDate(LocalDateTime.now());
		nrepo.saveOrUpdate(fetchedNote);
		return true;
	}
	
	@Override
	public List<Note> getallNotes(String token) {
		// found authorized user
		User fetchedUser = authenticatedUser(token);
		// note found
		List<Note> fetchedNotes = nrepo.getAllNotes(fetchedUser.getId());
		if (!fetchedNotes.isEmpty()) {
			return fetchedNotes;
		}
		// empty list
		return fetchedNotes;
	}
	
	@Override
	public boolean archiveNote(long noteId, String token) {
		// found authorized user
		authenticatedUser(token);
		// verified valid note
		Note fetchedNote = verifiedNote(noteId);
		// fetched note is not archived
		if (!fetchedNote.isArchived()) {
			fetchedNote.setArchived(true);
			fetchedNote.setUpdatedDate(LocalDateTime.now());
			nrepo.saveOrUpdate(fetchedNote);
			return true;
		}
		// if archived already
		return false;
	}
	
	@Override
	public boolean isPinnedNote(long noteId, String token) {
		// found authorized user
		authenticatedUser(token);
		// verified valid note
		Note fetchedNote = verifiedNote(noteId);
		if (!fetchedNote.isPinned()) {
			fetchedNote.setPinned(true);
			fetchedNote.setUpdatedDate(LocalDateTime.now());
			nrepo.saveOrUpdate(fetchedNote);
			return true;
		}
		// if pinned already
		fetchedNote.setPinned(false);
		fetchedNote.setUpdatedDate(LocalDateTime.now());
		nrepo.saveOrUpdate(fetchedNote);
		return false;
	}
	
	@Override
	public void changeColour(String token, long noteId, String noteColour) {
		// authenticate user
		authenticatedUser(token);
		// validate note
		Note fetchedNote = verifiedNote(noteId);
		fetchedNote.setColor(noteColour);
		fetchedNote.setUpdatedDate(LocalDateTime.now());
		nrepo.saveOrUpdate(fetchedNote);
	}

}
