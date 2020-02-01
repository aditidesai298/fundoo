package com.bridgelabz.fundoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	private INoteService nService;

	@ApiOperation(value = "create a new note for valid user")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Note created!"),
			@ApiResponse(code = 400, message = "Error creating note") })
	@PostMapping("create")
	public ResponseEntity<Response> createNote(@RequestBody NoteDto nDto, @RequestHeader("token") String token) {
		if (nService.createNote(nDto, token)) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Note created!", 201));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Error creating note", 400));
	}
	
	@ApiOperation(value = "delete an existing note for valid user")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "note deleted!"),	
			@ApiResponse(code = 300, message = "Note not found"),
			@ApiResponse(code = 400, message = "Error deleting note"),
			@ApiResponse(code = 401, message = "Authorization failed")})
	@DeleteMapping("{id}/delete")
	public ResponseEntity<Response> deleteNote(@PathVariable("id") long noteId, @RequestHeader("token") String token) {
		if (nService.deleteNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Note deleted! ", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Error deleting note ", 400));

	}
}