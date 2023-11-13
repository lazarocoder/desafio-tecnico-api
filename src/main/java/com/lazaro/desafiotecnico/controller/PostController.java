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

import com.lazaro.desafiotecnico.dto.PostDto;
import com.lazaro.desafiotecnico.dto.PostRequest;
import com.lazaro.desafiotecnico.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;

	@PostMapping
	public ResponseEntity createPost(@RequestBody PostRequest postRequest) {
		postService.createPost(postRequest);
		return new ResponseEntity(HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @RequestBody PostRequest postRequest) {
		PostDto postDto = postService.updatePost(id, postRequest);
		return ResponseEntity.ok(postDto);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePost(@PathVariable Long id) {
		postService.deletePost(id);
	}

	@GetMapping("public/all")
	public ResponseEntity<List<PostDto>> showAllPosts() {
		return new ResponseEntity<>(postService.showAllPosts(), HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<PostDto> getSinglePost(@PathVariable @RequestBody Long id) {
		return new ResponseEntity<>(postService.readSinglePost(id), HttpStatus.OK);
	}

	@GetMapping("/public/get/{id}")
	public ResponseEntity<PostDto> getPublicSinglePost(@PathVariable @RequestBody Long id) {
		return new ResponseEntity<>(postService.readSinglePost(id), HttpStatus.OK);
	}
}
