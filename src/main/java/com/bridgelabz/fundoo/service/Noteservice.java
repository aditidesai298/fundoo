package com.bridgelabz.fundoo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.dto.NoteDto;
import com.bridgelabz.fundoo.dto.ReminderDto;
import com.bridgelabz.fundoo.dto.UpdateNoteDto;
import com.bridgelabz.fundoo.exception.AuthorizationException;
import com.bridgelabz.fundoo.exception.NoteException;
import com.bridgelabz.fundoo.exception.ReminderException;
import com.bridgelabz.fundoo.model.Note;
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
	
	/**
	 * Method to get the RedisCache Id where we split the token and store it
    */
	
	private long getRedisCacheId(String token) {
		
		String[] tokenSplit = token.split("\\.");
		System.out.println(tokenSplit);
		String redisToken = tokenSplit[1] + tokenSplit[2];
		System.out.println("Inside redis cache method");
		if (redisTemplate.opsForValue().get(redisToken) == null) {
			long redisId = tokenobj.decodeToken(token);
		
			redisTemplate.opsForValue().set(redisToken, redisId, 3 * 60, TimeUnit.SECONDS);
		}
		return (Long) redisTemplate.opsForValue().get(redisToken);
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
		System.out.println("Note Dto: " + noteDto);
		User getUser = authenticatedUser(token);
		Note newNote = new Note();
		BeanUtils.copyProperties(noteDto, newNote);
		newNote.setCreatedDate(LocalDateTime.now());
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
		
		/**
		 * Example for implementing Redis Cache
		 */
		
//		noteId = getRedisCacheId(token);

		authenticatedUser(token);

		isVerified(noteId);
		nrepo.deleteNote(noteId);
		return true;
	}

	@Override
	public boolean updateNote(UpdateNoteDto noteDto, String token) {
		// found authorized user
		long noteId=getRedisCacheId(token);
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
			System.out.println("Notes: " +getNotes);
			return getNotes;
		}
		
		// empty list+
		return getNotes;
	}

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
		getNote.setArchived(false);
//		fetchedNote.setUpdatedDate(LocalDateTime.now());
		nrepo.saveOrUpdate(getNote);

          
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

	
	@Override
	public boolean restoreNote(long noteId, String token) {
		// found authorized user
		authenticatedUser(token);
		// verified valid note
		Note fetchedNote = isVerified(noteId);
		if (fetchedNote.isTrashed()) {
			fetchedNote.setTrashed(false);
			fetchedNote.setUpdatedDate(LocalDateTime.now());
			nrepo.saveOrUpdate(fetchedNote);
//			elasticSearchRepository.updateNote(fetchedNote);
			return true;
		}
		return false;
	}

	@Override
	public List<Note> getTrashed(String token) {
		// note found of authenticated user
		List<Note> getTrashed = nrepo.getAllTrashed(authenticatedUser(token).getId());
		if (!getTrashed.isEmpty()) {
			return getTrashed;
		}
		// empty list
		return getTrashed;
	}
	
	@Override
	public List<Note> getArchived(String token) {
		// note found of authenticated user
		List<Note> getArchived = nrepo.getAllArchived(authenticatedUser(token).getId());
		if (!getArchived.isEmpty()) {
			return getArchived;
		}
		// empty list
		return getArchived;
	}
	@Override
	public List<Note> getAllReminderNotes(String token) {
		// note found of authenticated user
		List<Note> getReminder = nrepo.getAllReminderNotes(authenticatedUser(token).getId());
		if (!getReminder.isEmpty()) {
			return getReminder;
		}
		// empty list
		return getReminder;
	}


	@Override
	public List<Note> getPinned(String token) {

		List<Note> getPinned = nrepo.getPinned(authenticatedUser(token).getId());
//		if (!fetchedPinnedNotes.isEmpty()) {
//			return fetchedPinnedNotes;
//		}
		return getPinned;
	}

	@Override
	public boolean Note(String token, Long noteid) {
		// TODO Auto-generated method stub
		return false;
	}

}
