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

import com.lazaro.desafiotecnico.dto.PhotoDto;
import com.lazaro.desafiotecnico.dto.PhotoRequest;
import com.lazaro.desafiotecnico.service.PhotoService;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

	@Autowired
	private PhotoService photoService;

	@PostMapping
	public ResponseEntity createPhoto(@RequestBody PhotoRequest photoRequest) {
		photoService.createPhoto(photoRequest);
		return new ResponseEntity(HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PhotoDto> updatePhoto(@PathVariable Long id, @RequestBody PhotoRequest photoRequest) {
		PhotoDto photoDto = photoService.updatePhoto(id, photoRequest);
		return ResponseEntity.ok(photoDto);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePhoto(@PathVariable Long id) {
		photoService.deletePhoto(id);
	}

	@GetMapping("public/all")
	public ResponseEntity<List<PhotoDto>> showAllPhotos() {
		return new ResponseEntity<>(photoService.showAllPhotos(), HttpStatus.OK);
	}

	@GetMapping("public/all/{id}")
	public ResponseEntity<List<PhotoDto>> showAllCommentsByAlbumId(@PathVariable Long id) {
		return new ResponseEntity<>(photoService.showAllCommentsByAlbumId(id), HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<PhotoDto> getSinglePhoto(@PathVariable @RequestBody Long id) {
		return new ResponseEntity<>(photoService.readSinglePhoto(id), HttpStatus.OK);
	}

	@GetMapping("/public/get/{id}")
	public ResponseEntity<PhotoDto> getPublicSinglePhoto(@PathVariable @RequestBody Long id) {
		return new ResponseEntity<>(photoService.readSinglePhoto(id), HttpStatus.OK);
	}
}
