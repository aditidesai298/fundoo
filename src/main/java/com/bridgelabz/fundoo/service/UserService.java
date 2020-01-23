package com.bridgelabz.fundoo.service;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.model.LoginDto;
import com.bridgelabz.fundoo.model.RegisterDto;
import com.bridgelabz.fundoo.repository.IUserRepository;
import com.bridgelabz.fundoo.util.JwtGenerator;

@Service
public class UserService implements IUserService {

	@Autowired
	private BCryptPasswordEncoder pe;
	@Autowired
	private IUserRepository urepo;
	@Autowired
	private JwtGenerator jwtToken;

	@Override
	public boolean register(RegisterDto UserDto) {

		User u1 = urepo.getUser(UserDto.getEmail());
		System.out.println("Email :" + u1);
		if (u1 != null) {
			return false;

		}

		User newU = new User();
		
		BeanUtils.copyProperties(UserDto, newU);
		
		newU.setCreatedDate(LocalDateTime.now());
		newU.setPassword(pe.encode(newU.getPassword()));
		newU.setVerified(false);
		
		urepo.save(newU);
		return true;

	}
	
	@Override
	public boolean isVerified(String token) {
		urepo.isVerified(jwtToken.decodeToken(token));
		return true;

	}
	
	public User login (LoginDto ldto)
	{
		User u=urepo.getUser(ldto.getEmail());
		
		return null;
		
	}

}
