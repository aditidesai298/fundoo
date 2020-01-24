package com.bridgelabz.fundoo.service;

import com.bridgelabz.fundoo.model.LoginDto;
import com.bridgelabz.fundoo.model.RegisterDto;
import com.bridgelabz.fundoo.model.User;

public interface IUserService {

	public boolean register(RegisterDto UserDto);

	public boolean isVerified(String token);

	public User login(LoginDto lDto);

}
