package com.bridgelabz.fundoo.service;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.model.LoginDto;
import com.bridgelabz.fundoo.model.RegisterDto;
import com.bridgelabz.fundoo.model.UpdatePassDto;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.IUserRepository;
import com.bridgelabz.fundoo.util.EmailSender;
import com.bridgelabz.fundoo.util.JwtGenerator;
import com.bridgelabz.fundoo.util.Util;

/**
 * The class that has all the service methods that provide services to the
 * controller such as registering the user, login service methods, etc. It
 * contains the implementation of such methods.
 * 
 * @author Aditi Desai
 * @created 26.1.20
 * @version 1.0
 */
@Service
public class UserService implements IUserService {

	@Autowired
	private BCryptPasswordEncoder pe;
	@Autowired
	private IUserRepository urepo;
	@Autowired
	private JwtGenerator tokenobj;
	@Autowired
	private EmailSender emailobj;
	@Autowired
	private Environment environment;

	@Override
	public boolean register(RegisterDto UserDto) {

		User u1 = urepo.getUser(UserDto.getEmail());
		;
		if (u1 != null) {
			return false;

		}

		User newU = new User();

		BeanUtils.copyProperties(UserDto, newU);

		newU.setCreatedDate(LocalDateTime.now());
		newU.setPassword(pe.encode(newU.getPassword()));
		newU.setVerified(false);

		urepo.save(newU);

		String emailBodyContentLink = Util.createLink("http://localhost:8082/user/verification",
				tokenobj.generateToken(newU.getId()));
		emailobj.sendMail(newU.getEmail(), "registration link", emailBodyContentLink);

		return true;

	}

	@Override
	public boolean isVerified(String token) {
		urepo.verify(tokenobj.decodeToken(token));
		return true;

	}

	@Override
	public User login(LoginDto Ldto) {
		User inputUser = urepo.getUser(Ldto.getEmail());
		// valid user
		if (inputUser != null) {
			// send for verification if not verified
			if (inputUser.isVerified() && pe.matches(Ldto.getPassword(), inputUser.getPassword())) {
				return inputUser;
			}
			String emailBodyLink = Util.createLink("http://localhost:8082" + "/user/verification",
					tokenobj.generateToken(inputUser.getId()));
			emailobj.sendMail(inputUser.getEmail(), "Registration Verification link", emailBodyLink);
			return inputUser;
		}
		// not registered
		return null;
	}

	@Override
	public boolean is_User_exists(String email) {
		User user = urepo.getUser(email);
		if (!user.isVerified()) {
			return false;
		}

		String mail = Util.createLink("http://localhost:8082" + "/user/forgotpassword",
				tokenobj.generateToken(user.getId()));
		emailobj.sendMail(user.getEmail(), "verification", mail);
		return true;

	}

	@Override
	public boolean updatePassword(UpdatePassDto updatePasswordInformation, String token) {
		if (updatePasswordInformation.getPassword().equals(updatePasswordInformation.getConfirmPassword())) {
			updatePasswordInformation.setConfirmPassword(pe.encode(updatePasswordInformation.getConfirmPassword()));
			urepo.updatePassword(updatePasswordInformation, tokenobj.decodeToken(token));
			// sends mail after updating password
			emailobj.sendMail(updatePasswordInformation.getEmailId(), "Password updated sucessfully!",
					postUpdatePassMail(updatePasswordInformation));
			return true;
		}
		return false;

	}

	private String postUpdatePassMail(UpdatePassDto updatePasswordInformation) {
		String passwordUpdateBodyContent = "Login Details \n" + "UserId : " + updatePasswordInformation.getEmailId()
				+ "\nPassword : " + updatePasswordInformation.getPassword();
		String loginString = "\nClick on the link to login\n";
		String loginLink = "http://localhost:" + environment.getProperty("server.port") + "/user/login";
		return passwordUpdateBodyContent + loginString + loginLink;
	}

}
