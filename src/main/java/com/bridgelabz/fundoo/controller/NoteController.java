package com.bridgelabz.fundoo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.model.Note;
import com.bridgelabz.fundoo.model.NoteDto;
import com.bridgelabz.fundoo.model.ReminderDto;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.service.INoteService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("note")
public class NoteController {

	@Autowired
	private INoteService nService;

	@ApiOperation(value = "To create a new note for a user")

	@PostMapping("create")
	public ResponseEntity<Response> createNote(@RequestBody NoteDto nDto, @RequestHeader("token") String token) {
		if (nService.createNote(nDto, token)) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Note created!", 201));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Error creating note", 400));
	}

	@ApiOperation(value = "To delete an existing note")

	@DeleteMapping("{id}/delete")
	public ResponseEntity<Response> deleteNote(@PathVariable("id") long noteId, @RequestHeader("token") String token) {
		if (nService.deleteNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Note deleted! ", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Error deleting note ", 400));

	}

	@ApiOperation(value = "To update an existing note")

	@PutMapping("update")
	public ResponseEntity<Response> updateNote(@RequestBody NoteDto noteDto, @RequestParam("id") long noteId,
			@RequestHeader("token") String token) {
		if (nService.updateNote(noteDto, noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Note updated! ", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Error updating note  ", 400));
	}

	@ApiOperation(value = "To fetch all notes of a user")

	@GetMapping("fetch/notes")
	public ResponseEntity<Response> fetchNotes(@RequestHeader("token") String token) {
		List<Note> notes = nService.getallNotes(token);
		if (!notes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Notes are", 200, notes));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Error fetching notes", 400));
	}

	@ApiOperation(value = "To archive a note of a user")

	@PatchMapping("{id}/archive")
	public ResponseEntity<Response> archiveNote(@PathVariable("id") long noteId, @RequestHeader("token") String token) {
		if (nService.archiveNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("note archived", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Already archived", 400));
	}

	@ApiOperation(value = "To pin/unpin a note of a user")
	@PatchMapping("{id}/pin")
	public ResponseEntity<Response> pinNote(@PathVariable("id") long noteId, @RequestHeader("token") String token) {
		if (nService.pinNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("note pinned", 200));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("note unpinned", 400));
	}

	@ApiOperation(value = "To change the color of a note of a user")

	@PostMapping("{id}/colour")
	public ResponseEntity<Response> changeColour(@RequestHeader("token") String token, @PathVariable("id") long noteId,
			@RequestParam("color") String noteColour) {
		nService.changeColour(token, noteId, noteColour);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("color changed", 200));
	}

	@ApiOperation(value = "To trash the note of a user")

	@DeleteMapping("{id}/trash")
	public ResponseEntity<Response> trashNote(@PathVariable("id") long noteId, @RequestHeader("token") String token) {
		if (nService.trashNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("note trashed", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Already trashed!", 400));
	}

	@ApiOperation(value = "To set a reminder for a note by a user")
	@PutMapping("{id}/reminder/add")
	public ResponseEntity<Response> setReminder(@RequestHeader("token") String token, @PathVariable("id") long noteId,
			@RequestBody ReminderDto reminderDTO) {
		nService.addReminder(token, noteId, reminderDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Reminder created", 200));
	}

	@ApiOperation(value = "To delete reminder of a note by a user")

	@DeleteMapping("{id}/reminder/delete")
	public ResponseEntity<Response> removeReminder(@RequestHeader("token") String token,
			@PathVariable("id") long noteId) {
		nService.deleteReminder(token, noteId);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Reminder removed!", 200));
	}

}