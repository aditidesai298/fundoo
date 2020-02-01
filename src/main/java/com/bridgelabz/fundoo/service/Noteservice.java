package com.bridgelabz.fundoo.service;

import java.time.LocalDateTime;

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

}
