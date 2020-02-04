package com.bridgelabz.fundoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
		if (lService.isLabelEdited(token, labelDTO, labelId)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("label name changed", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Opps...new Label name can't be same!", 400));
	}
}