package com.bridgelabz.fundoo.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.model.Note;

@Repository
@SuppressWarnings({ "rawtypes" })
public class NoteRepository implements INoteRepository {

	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional
	public Note saveOrUpdate(Note newNote) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(newNote);
		return newNote;
	}

	@Override
	@Transactional
	public Note getNote(long noteId) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("FROM Note WHERE noteId=:id");
		query.setParameter("id", noteId);
		return (Note) query.uniqueResult();
	}
	
	@Override
	@Transactional
	public boolean isDeletedNote(long noteId) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("DELETE FROM Note WHERE noteId=:id");
		query.setParameter("id", noteId);
		query.executeUpdate();
		return true;
	}
	@Override
	public List<Note> getAllNotes(long userId) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("FROM Note WHERE id=:id and is_trashed=false and is_archived=false");
				query.setParameter("id", userId);
				return query.getResultList();
			

	}
	
	@Transactional
	@Override
	public boolean setRestored(Long userId, Long noteId) {
	
		Session session = entityManager.unwrap(Session.class);
		Note note = findById(noteId);
		if (note.getId()==(userId)) {
			if (note.isTrashed()) {
				note.setTrashed(false);
				note.setCreatedDate(LocalDateTime.now());
				session.saveOrUpdate(note);
				return true;
			}
			return false;
		}

		return false;
	}

	private Note findById(Long noteId) {
		Session session = entityManager.unwrap(Session.class);
		Query query = (Query) session.createQuery("from Note where id=:id");
		query.setParameter("noteId", noteId);
		return (Note) query.uniqueResult();
	}

}