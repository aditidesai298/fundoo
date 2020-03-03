package com.bridgelabz.fundoo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.dto.EmailDto;
import com.bridgelabz.fundoo.dto.LoginDto;
import com.bridgelabz.fundoo.dto.RegisterDto;
import com.bridgelabz.fundoo.dto.UpdatePassDto;
import com.bridgelabz.fundoo.dto.UserDto;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.response.LoginResponse;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.service.IUserService;
import com.bridgelabz.fundoo.util.JwtGenerator;

import io.swagger.annotations.ApiOperation;

/** Controller class to manage the requests from the user
 * such as registration, login, forgot password etc
 * 
 * @author Aditi Desai
 * @created 25.01.2020
 * @version 1.0
 * */

@RestController
@RequestMapping("user")
@CrossOrigin(origins="http://localhost:4200")
public class UserController {
	@Autowired
	private IUserService uService;

	@Autowired
	private JwtGenerator tk;

	@ApiOperation(value = "To register a new user")
	
	@PostMapping("registration")
	public ResponseEntity<Response> registration(@RequestBody RegisterDto rdto) {

		
		boolean rstatus = uService.register(rdto);

		if ((rstatus) == false) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new Response("user already exist", 400));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Registration successful!", 200));

	}
	
	@ApiOperation(value = "To verify registration of user")

	@PutMapping("verification/{token}")
	public ResponseEntity<Response> verifyRegistration(@PathVariable("token") String token) {

		if (uService.isVerified(token)) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("verified sucessfully.", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new Response("not verified", 400));

	}

	@ApiOperation(value = "To login")
	
	@PostMapping("login")
	public ResponseEntity<Response> login(@RequestBody LoginDto dto) {

		User userInformation = uService.login(dto);

		if (userInformation != null) {
			String token = tk.generateToken(userInformation.getId());

			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.header("login successful! Token number:  ", dto.getEmail())
					.body(new Response("Login successful! Token number: " + token, 200, token));
		} else {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Login failed", 400, dto));
		}
	}
	
	@ApiOperation(value = "Api for forgot password")

	@PostMapping("forgotPassword")
	public ResponseEntity<Response> forgotPassword(@RequestBody EmailDto user ) {
		System.out.println("Inside forgot password controller" + user.getEmailId());
		boolean fetchedUserStatus = uService.is_User_exists(user.getEmailId());
		if (fetchedUserStatus) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("found user", 202));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response("not verified", 401));
	}
	@ApiOperation(value = "To update password")
	@PutMapping("updatePassword/{token}")
	public ResponseEntity<Response> updatePassword(@PathVariable("token") String token,
			@RequestBody() UpdatePassDto updatePassword) {
		boolean updationStatus = uService.updatePassword(updatePassword, token);
		if (updationStatus) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("updated sucessfully", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("updation failed", 400));

	}

}
