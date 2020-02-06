package com.bridgelabz.fundoo.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.exception.AuthorizationException;
import com.bridgelabz.fundoo.exception.NoteException;
import com.bridgelabz.fundoo.exception.ReminderException;
import com.bridgelabz.fundoo.model.Note;
import com.bridgelabz.fundoo.model.NoteDto;
import com.bridgelabz.fundoo.model.ReminderDto;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.INoteRepository;
import com.bridgelabz.fundoo.repository.IUserRepository;
import com.bridgelabz.fundoo.util.JwtGenerator;

/**
 * The class that contains the service methods for the NoteController to use
 * such as create note, delete note, update note, archive notes etc.
 * 
 * @author Aditi Desai
 * @created 28.1.20
 * @version 1.0
 */
@Service
public class Noteservice implements INoteService {

	@Autowired
	private IUserRepository urepo;
	@Autowired
	private INoteRepository nrepo;
	@Autowired
	private JwtGenerator tokenobj;

	@Autowired
	RedisTemplate<String, Object> redisTemplate;
	
	
	private long getRedisCacheId(String token) {
		
		String[] splitedToken = token.split("\\.");
		System.out.println(splitedToken);
		String redisTokenKey = splitedToken[1] + splitedToken[2];
		System.out.println("Inside redis cache method");
		if (redisTemplate.opsForValue().get(redisTokenKey) == null) {
			long idForRedis = tokenobj.decodeToken(token);
		
			redisTemplate.opsForValue().set(redisTokenKey, idForRedis, 3 * 60, TimeUnit.SECONDS);
		}
		return (Long) redisTemplate.opsForValue().get(redisTokenKey);
	}

	private User authenticatedUser(String token) {
		User getUser = urepo.getUser(tokenobj.decodeToken(token));
		if (getUser != null) {
			return getUser;
		}
		throw new AuthorizationException("Authorization failed", 401);
	}

	@Override
	public boolean createNote(NoteDto noteDto, String token) {
		// found authorized user
		
		User getUser = authenticatedUser(token);
		Note newNote = new Note();
		BeanUtils.copyProperties(noteDto, newNote);

		newNote.setColor("white");
		getUser.getNotes().add(newNote);
		nrepo.saveOrUpdate(newNote);
		return true;
	}

	private Note isVerified(long noteId) {
		
		Note getNote = nrepo.getNote(noteId);
		if (getNote != null) {
			return getNote;
		}
		throw new NoteException("Not not found", 300);
	}

	@Override
	public boolean deleteNote(long noteId, String token) {
		
		long noteIdId = getRedisCacheId(token);
		// found authorized user
		//authenticatedUser(token);
		// verified valid note
		//isVerified(noteId);
		nrepo.deleteNote(noteId);
		return true;
	}

	@Override
	public boolean updateNote(NoteDto noteDto, long noteId, String token) {
		// found authorized user
		authenticatedUser(token);
		// verified valid note
		Note getNote = isVerified(noteId);
		BeanUtils.copyProperties(noteDto, getNote);

		nrepo.saveOrUpdate(getNote);
		return true;
	}

	@Override
	@Transactional
	public List<Note> getallNotes(String token) {
		// found authorized user
		User getUser = authenticatedUser(token);
		// note found
		List<Note> getNotes = nrepo.getAllNotes(getUser.getId());
		if (!getNotes.isEmpty()) {
			return getNotes;
		}
		// empty list
		return getNotes;
	}

	@Override
	public boolean archiveNote(long noteId, String token) {
		// found authorized user
		authenticatedUser(token);
		// verified valid note
		Note getNote = isVerified(noteId);
		// fetched note is not archived
		if (!getNote.isArchived()) {
			getNote.setArchived(true);

			nrepo.saveOrUpdate(getNote);
			return true;
		}
		// if archived already
		return false;
	}

	@Override
	public boolean pinNote(long noteId, String token) {
		// found authorized user
		authenticatedUser(token);
		// verified valid note
		Note getNote = isVerified(noteId);
		if (!getNote.isPinned()) {
			getNote.setPinned(true);

			nrepo.saveOrUpdate(getNote);
			return true;
		}
		// if already pinned
		getNote.setPinned(false);

		nrepo.saveOrUpdate(getNote);
		return false;
	}

	@Override
	public void changeColour(String token, long noteId, String noteColour) {
		// authenticate user
		authenticatedUser(token);
		// validate note
		Note getNote = isVerified(noteId);
		getNote.setColor(noteColour);

		nrepo.saveOrUpdate(getNote);
	}

	@Override
	public boolean trashNote(long noteId, String token) {
		// found authorized user
		authenticatedUser(token);
		// verified valid note
		Note getNote = isVerified(noteId);
		if (!getNote.isTrashed()) {
			getNote.setTrashed(true);

			nrepo.saveOrUpdate(getNote);
			return true;
		}
		// if already trashed
		return false;
	}

	@Override
	public void addReminder(String token, long noteId, ReminderDto remainderDTO) {
		// authenticate user
		authenticatedUser(token);
		// validate note
		Note getNote = isVerified(noteId);
		if (getNote.getRemainderDate() == null) {

			getNote.setRemainderDate(remainderDTO.getRemainder());
			nrepo.saveOrUpdate(getNote);
			return;
		}
		throw new ReminderException("Reminder already set!", 502);
	}

	@Override
	public void deleteReminder(String token, long noteId) {
		// authenticate user
		authenticatedUser(token);
		// validate note
		Note getNote = isVerified(noteId);
		if (getNote.getRemainderDate() != null) {
			getNote.setRemainderDate(null);

			nrepo.saveOrUpdate(getNote);
			return;
		}
		throw new ReminderException("Reminder already removed!", 502);
	}

	@Transactional
	@Override
	public boolean restored(String token, Long noteId) {

		authenticatedUser(token);

		Note getNote = isVerified(noteId);
		if (getNote.isTrashed()) {
			getNote.setTrashed(false);

			nrepo.saveOrUpdate(getNote);
			return true;

		}
		return false;

	}

	@Override
	public List<Note> getTrashed(String token) {
		// note found of authenticated user
		List<Note> getTrashed = nrepo.getTrashed(authenticatedUser(token).getId());
		if (!getTrashed.isEmpty()) {
			return getTrashed;
		}
		// empty list
		return getTrashed;
	}

	@Override
	public List<Note> getPinned(String token) {

		List<Note> getPinned = nrepo.getPinned(authenticatedUser(token).getId());
//		if (!fetchedPinnedNotes.isEmpty()) {
//			return fetchedPinnedNotes;
//		}
		return getPinned;
	}

}
