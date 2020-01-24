package com.bridgelabz.fundoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.model.LoginDto;
import com.bridgelabz.fundoo.model.RegisterDto;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.response.LoginResponse;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.service.IUserService;
import com.bridgelabz.fundoo.util.JwtGenerator;

@RestController
@RequestMapping("user")

public class UserController {
	@Autowired
	private IUserService userService;

	@Autowired
	private JwtGenerator tk;

	@PostMapping("registration")
	public ResponseEntity<Response> registration(@RequestBody RegisterDto rdto) {

		System.out.println("Inside Controller...");
		boolean rstatus = userService.register(rdto);

		if ((rstatus) == false) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new Response("user already exist", 400));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Registration successful!", 200));

	}

//	
//	@PostMapping("login")
//	public ResponseEntity<Response> login(@RequestBody LoginDto ldto) {
//		
//		boolean lstatus=userService.login(ldto);
//		return null;
//		
//	}
	@GetMapping("verification/{token}")
	public ResponseEntity<Response> verifyRegistration(@PathVariable("token") String token) {

		if (userService.isVerified(token)) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("verified sucessfully.", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new Response("not verified", 400));

	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginDto info) {

		User userInformation = userService.login(info);
		System.out.println("inside login controler");
		if (userInformation != null) {
			String token = tk.generateToken(userInformation.getId());

			return ResponseEntity.status(HttpStatus.ACCEPTED).header("login successfull", info.getEmail())
					.body(new LoginResponse(token, 200, info));
		} else {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new LoginResponse("Login failed", 400, info));
		}
	}

}
