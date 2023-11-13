package com.lazaro.desafiotecnico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lazaro.desafiotecnico.dto.CommentDto;
import com.lazaro.desafiotecnico.dto.CommentRequest;
import com.lazaro.desafiotecnico.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

	@Autowired
	private CommentService postService;

	@PostMapping
	public ResponseEntity createComment(@RequestBody CommentRequest postRequest) {
		postService.createComment(postRequest);
		return new ResponseEntity(HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @RequestBody CommentRequest postRequest) {
		CommentDto postDto = postService.updateComment(id, postRequest);
		return ResponseEntity.ok(postDto);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteComment(@PathVariable Long id) {
		postService.deleteComment(id);
	}

	@GetMapping("public/all")
	public ResponseEntity<List<CommentDto>> showAllComments() {
		return new ResponseEntity<>(postService.showAllComments(), HttpStatus.OK);
	}

	@GetMapping("public/all/{id}")
	public ResponseEntity<List<CommentDto>> showAllCommentsByPostId(@PathVariable Long id) {
		return new ResponseEntity<>(postService.showAllCommentsByPostId(id), HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<CommentDto> getSingleComment(@PathVariable @RequestBody Long id) {
		return new ResponseEntity<>(postService.readSingleComment(id), HttpStatus.OK);
	}

	@GetMapping("/public/get/{id}")
	public ResponseEntity<CommentDto> getPublicSingleComment(@PathVariable @RequestBody Long id) {
		return new ResponseEntity<>(postService.readSingleComment(id), HttpStatus.OK);
	}
}
