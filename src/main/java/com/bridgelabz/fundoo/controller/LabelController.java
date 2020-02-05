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

import com.bridgelabz.fundoo.model.Label;
import com.bridgelabz.fundoo.model.LabelDto;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.service.ILabelService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/label")
public class LabelController {

	@Autowired
	private ILabelService lService;

	@PostMapping("create")
	@ApiOperation(value = "To create a label")
	public ResponseEntity<Response> createLabel(@RequestHeader("token") String token, @RequestBody LabelDto labelDto) {
		lService.createLabel(token, labelDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Label created!", 201, labelDto));
	}

	@PostMapping("/create/{noteId}")
	@ApiOperation(value = "To create label and it's note")
	public ResponseEntity<Response> createandMapLabel(@RequestHeader("token") String token,
			@RequestBody LabelDto labelDTO, @PathVariable("noteId") long noteId) {
		lService.createLabelAndMap(token, noteId, labelDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("label created and mapped", 201, labelDTO));
	}

	@PutMapping("/edit")
	@ApiOperation(value = "To edit name of label")
	public ResponseEntity<Response> editLabelName(@RequestHeader("token") String token, @RequestBody LabelDto labelDTO,
			@RequestParam("labelId") long labelId) {
		if (lService.editLabel(token, labelDTO, labelId)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("label name changed", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("New label cannot be the same", 400));
	}

	@DeleteMapping("/{labelId}/delete")
	@ApiOperation(value = "To delete a label")
	public ResponseEntity<Response> deleteLabel(@RequestHeader("token") String token,
			@PathVariable("labelId") long labelId) {
		if (lService.deleteLabel(token, labelId)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("label deleted sucessfully", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Cannot delete label", 400));
	}

	@GetMapping("/fetch/labels")
	@ApiOperation(value = "To get all the labels from user")
	public ResponseEntity<Response> getAllLabels(@RequestHeader("token") String token) {
		List<Label> foundLabelList = lService.foundLabelsList(token);
		if (!foundLabelList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("found labels", 200, foundLabelList));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Opps...No labels founds", 400));
	}
	
	@PostMapping("/addlabels")
	@ApiOperation(value = "To add a note to existing label")
	public ResponseEntity<Response> addLabelsToNote(@RequestHeader("token") String token,
			@RequestParam("noteId") long noteId, @RequestParam("labelId") long labelId) {
		lService.addNoteLabel(token, noteId, labelId);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("note added to the label", 200));
	}
	
	@PatchMapping("/remove")
	@ApiOperation(value = "To remove a note from a label", response = Response.class)
	public ResponseEntity<Response> removeLabelsToNote(@RequestHeader("token") String token,
			@RequestParam("noteId") long noteId, @RequestParam("labelId") long labelId) {
		lService.remoNoteLabel(token, noteId, labelId);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("note removed from the label", 200));
	}
}