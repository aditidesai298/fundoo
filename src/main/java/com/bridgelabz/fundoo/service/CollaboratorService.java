package com.bridgelabz.fundoo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.exception.AuthorizationException;
import com.bridgelabz.fundoo.exception.CollaboratorException;
import com.bridgelabz.fundoo.exception.NoteException;
import com.bridgelabz.fundoo.model.Note;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.INoteRepository;
import com.bridgelabz.fundoo.repository.IUserRepository;
import com.bridgelabz.fundoo.util.JwtGenerator;

@Service
public class CollaboratorService implements ICollaboratorService {
	@Autowired
	private IUserRepository uRepo;
	@Autowired
	private INoteRepository nRepo;
	@Autowired
	private JwtGenerator jwt;

	private User authenticatedMainUser(String token) {
		User user = uRepo.getUser(jwt.decodeToken(token));
		if (user != null) {
			return user;
		}
		throw new AuthorizationException("Authorization failed", 401);
	}

	private Note verifiedNote(long noteId) {
		Note note = nRepo.getNote(noteId);
		if (note != null) {
			if (!note.isTrashed()) {
				return note;
			}
			throw new NoteException("Note is trashed", 300);
		}
		throw new NoteException("Note not found", 300);
	}

	private User validCollaborator(String emailId) {
		User collaborator = uRepo.getUser(emailId);
		if (collaborator != null && collaborator.isVerified()) {
			return collaborator;
		}
		throw new CollaboratorException("Collaborator is not a valid user", 404);
	}

	@Override
	public boolean addCollaborator(String token, long noteId, String emailId) {
		authenticatedMainUser(token);
		Note validNote = verifiedNote(noteId);
		User validColaborator = validCollaborator(emailId);
		validNote.getCollaboratedUsers().add(validColaborator);
		validColaborator.getCollaboratedNotes().add(validNote);
		nRepo.saveOrUpdate(validNote);
		return true;
	}

}