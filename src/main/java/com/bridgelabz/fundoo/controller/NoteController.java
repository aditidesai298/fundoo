package com.bridgelabz.fundoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@ApiOperation(value = "To create a new note for user")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Note created!"),
			@ApiResponse(code = 400, message = "Error creating note") })
	@PostMapping("create")
	public ResponseEntity<Response> createNote(@RequestBody NoteDto nDto, @RequestHeader("token") String token) {
		if (nService.createNote(nDto, token)) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Note created!", 201));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Error creating note", 400));
	}
	
	@ApiOperation(value = "To delete an existing note")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Note deleted!"),	
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
	
	@ApiOperation(value = "To update an existing note")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Note updated!"),	
			@ApiResponse(code = 300, message = "Note not found"),
			@ApiResponse(code = 400, message = "Error updating note"),
			@ApiResponse(code = 401, message = "Authorization failed")})
	@PutMapping("update")
	public ResponseEntity<Response> updateNote(@RequestBody NoteDto noteDto, @RequestParam("id") long noteId,
			@RequestHeader("token") String token) {
		if (nService.updateNote(noteDto, noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Note updated! ", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Error updating note  ", 400));
	}
	
}