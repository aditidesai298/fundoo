package com.bridgelabz.fundoo.service;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.model.LoginDto;
import com.bridgelabz.fundoo.model.RegisterDto;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.IUserRepository;
import com.bridgelabz.fundoo.util.EmailSender;
import com.bridgelabz.fundoo.util.JwtGenerator;
import com.bridgelabz.fundoo.util.Util;

@Service
public class UserService implements IUserService {

	@Autowired
	private BCryptPasswordEncoder pe;
	@Autowired
	private IUserRepository urepo;
	@Autowired
	private JwtGenerator jwtToken;
	@Autowired
	private EmailSender emailServiceProvider;

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

		String emailBodyContentLink = Util.createLink("http://localhost:8081/user/verification",
				jwtToken.generateToken(newU.getId()));
		emailServiceProvider.sendMail(newU.getEmail(), "registration link", emailBodyContentLink);

		return true;

	}

	@Override
	public boolean isVerified(String token) {
		urepo.verify(jwtToken.decodeToken(token));
		return true;

	}

	@Override
	public User login(LoginDto Ldto) {
		User input_user = urepo.getUser(Ldto.getEmail());
		// valid user
		if (input_user != null) {
			// send for verification if not verified
			if (input_user.isVerified() && pe.matches(Ldto.getPassword(), input_user.getPassword())) {
				return input_user;
			}
			String email_body_link = Util.createLink("http://192.168.1.41:8081" + "/user/verification",
					jwtToken.generateToken(input_user.getId()));
			emailServiceProvider.sendMail(input_user.getEmail(), "Registration Verification link", email_body_link);
			return input_user;
		}
		// not registered
		return null;
	}
	

}
