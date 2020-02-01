package com.bridgelabz.fundoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.model.NoteDto;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.service.INoteService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("note")
public class NoteController {

	@Autowired
	private INoteService noteService;

	@ApiOperation(value = "create a new note for valid user")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Note created!"),
			@ApiResponse(code = 400, message = "Error creating note") })
	@PostMapping("create")
	public ResponseEntity<Response> createNote(@RequestBody NoteDto nDto, @RequestHeader("token") String token) {
		if (noteService.createNote(nDto, token)) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("note created", 201));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Error creating note", 400));
	}
}