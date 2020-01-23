package com.bridgelabz.fundoo.repository;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.model.User;

@Repository
public class UserRepository implements IUserRepository{
	
	private EntityManager entityManager;

	@Override
	public User save(User newUser) {
		// TODO Auto-generated method stub
		
	Session sess=entityManager.unwrap(Session.class);
	sess.saveOrUpdate(newUser);
	return newUser;
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public User getUser(String emailId) {
		Session session = entityManager.unwrap(Session.class);
		Query emailFetchQuery = session.createQuery("FROM User where emailId=:emailId");
		emailFetchQuery.setParameter("emailId", emailId);
		return (User) emailFetchQuery.uniqueResult();
	}
	

}
