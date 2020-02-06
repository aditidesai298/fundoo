package com.bridgelabz.fundoo.repository;

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
	public Note getNote(long nId) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("FROM Note WHERE n_id=:id");
		query.setParameter("id", nId);
		return (Note) query.uniqueResult();
	}
	
	@Override
	@Transactional
	public boolean deleteNote(long nId) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("DELETE FROM Note WHERE n_id=:id");
		query.setParameter("id", nId);
		query.executeUpdate();
		return true;
	}
	@Override
	@Transactional
	public List<Note> getAllNotes(long uId) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("FROM Note WHERE id=:id and is_trashed=false and is_archived=false");
				query.setParameter("id", uId);
				return query.getResultList();
			

	}
	@Override
	public List<Note> getTrashed(long uId) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("FROM Note WHERE id=:id and is_trashed=true");
				query.setParameter("id", uId);
		return query.getResultList();
	}
	
	@Override
	public List<Note> getPinned(long uId) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("FROM Note WHERE id=:id and is_pinned=true");
				query.setParameter("id", uId);
		return query.getResultList();
	}
	
	
}