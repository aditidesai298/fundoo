package com.bridgelabz.fundoo.repository;

import com.bridgelabz.fundoo.model.User;

public interface IUserRepository {

	public User save(User newUser);

	public User getUser(String emailId);

	public boolean isVerified(Long id);

}
