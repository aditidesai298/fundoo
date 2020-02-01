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
		User input_user = urepo.getUser(Ldto.getEmail());
		// valid user
		if (input_user != null) {
			// send for verification if not verified
			if (input_user.isVerified() && pe.matches(Ldto.getPassword(), input_user.getPassword())) {
				return input_user;
			}
			String email_body_link = Util.createLink("http://localhost:8081" + "/user/verification",
					tokenobj.generateToken(input_user.getId()));
			emailobj.sendMail(input_user.getEmail(), "Registration Verification link", email_body_link);
			return input_user;
		}
		// not registered
		return null;
	}

	@Override
	public boolean is_User_exists(String email) {
		User user=urepo.getUser(email);
		if (!user.isVerified()) {
			return false;
		}
		
		String mail=Util.createLink("http://localhost:8081"+"/user/forgotpassword",tokenobj.generateToken(user.getId()));
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
					post_updatePass_mail(updatePasswordInformation));
			return true;
		}
		return false;

	}

	private String post_updatePass_mail(UpdatePassDto updatePasswordInformation) {
		String passwordUpdateBodyContent = "Login Details \n" + "UserId : " + updatePasswordInformation.getEmailId()
				+ "\nPassword : " + updatePasswordInformation.getPassword();
		String loginString = "\nClick on the link to login\n";
		String loginLink = "http://localhost:" + environment.getProperty("server.port") + "/user/login";
		return passwordUpdateBodyContent + loginString + loginLink;
	}

}
