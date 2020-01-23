package com.bridgelabz.fundoo.repository;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.fundoo.model.User;

@Repository
public class UserRepository implements IUserRepository{
	@Autowired
	private EntityManager entityManager;

	@Override
	public User save(User newUser) {
	System.out.println("Data :"+newUser.getEmail());
	Session sess=entityManager.unwrap(Session.class);
	sess.saveOrUpdate(newUser);
	return newUser;
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional
	public User getUser(String email) {
		Session sess = entityManager.unwrap(Session.class);
		Query emailFetchQuery = sess.createQuery("FROM User where email=:email");
		System.out.println("After Query");
		emailFetchQuery.setParameter("email", email);
		return (User) emailFetchQuery.uniqueResult();
	}

	@Override
	@Transactional
	public boolean isVerified(Long id) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("update User set is_verified=:verified" + " where id=:id");
		query.setParameter("verified", true);
		query.setParameter("id", id);
		query.executeUpdate();
		return true;
	}
	}
	


