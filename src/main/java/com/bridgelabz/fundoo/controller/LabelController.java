package com.bridgelabz.fundoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
	@ApiOperation(value = "To create a label", response = Response.class)
	public ResponseEntity<Response> createLabel(@RequestHeader("token") String token, @RequestBody LabelDto labelDto) {
		lService.createLabel(labelDto, token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("label created", 201, labelDto));
	}
}