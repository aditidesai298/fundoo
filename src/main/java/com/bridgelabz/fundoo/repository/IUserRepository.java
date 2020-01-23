package com.bridgelabz.fundoo.repository;

import com.bridgelabz.fundoo.model.User;

public interface IUserRepository {
	
	public User save(User newUser);
	public boolean verifyUser(Long id);

}
