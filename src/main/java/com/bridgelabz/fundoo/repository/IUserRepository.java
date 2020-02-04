package com.bridgelabz.fundoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundoo.model.UpdatePassDto;
import com.bridgelabz.fundoo.model.User;

public interface IUserRepository {

	public User save(User newUser);

	public User getUser(String emailId);

	public boolean verify(Long id);

	public boolean updatePassword(UpdatePassDto updatePasswordinformation, long id);

	public User getUser(Long id);
	
	

}
