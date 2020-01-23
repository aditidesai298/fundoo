package com.bridgelabz.fundoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.model.LoginDto;
import com.bridgelabz.fundoo.model.RegisterDto;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.service.IUserService;

@RestController
@RequestMapping("user")
//@CrossOrigin(origins = "http://localhost:8081")
public class UserController {
	@Autowired
	private IUserService userService;

	@PostMapping("registration")
	public ResponseEntity<Response> registration(@RequestBody RegisterDto rdto) {

		System.out.println("Inside Controller...");
		boolean rstatus = userService.register(rdto);

		if ((rstatus) == false) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
					.body(new Response("user already exist", 400));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Registration successful!", 200));

	}
	
	@PostMapping("login")
	public ResponseEntity<Response> login(@RequestBody LoginDto ldto) {
		
		boolean lstatus=userService.login(ldto);
		return null;
		
	}


}
