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
    @Override
	public boolean verifyUser(Long id) {
		Session session = entityManager.unwrap(Session.class);
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery("update User set is_verified=:verified" + "where id=:id");
		query.setParameter("verified", true);
		query.setParameter("id", id);
		if (query.executeUpdate() > 0) {
			return true;
		}
		return false;
	}
	

}
