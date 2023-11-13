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

import com.lazaro.desafiotecnico.dto.AlbumDto;
import com.lazaro.desafiotecnico.dto.AlbumRequest;
import com.lazaro.desafiotecnico.service.AlbumService;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

	@Autowired
	private AlbumService albumService;

	@PostMapping
	public ResponseEntity createAlbum(@RequestBody AlbumRequest albumRequest) {
		albumService.createAlbum(albumRequest);
		return new ResponseEntity(HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<AlbumDto> updateAlbum(@PathVariable Long id, @RequestBody AlbumRequest albumRequest) {
		AlbumDto albumDto = albumService.updateAlbum(id, albumRequest);
		return ResponseEntity.ok(albumDto);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAlbum(@PathVariable Long id) {
		albumService.deleteAlbum(id);
	}

	@GetMapping("public/all")
	public ResponseEntity<List<AlbumDto>> showAllAlbums() {
		return new ResponseEntity<>(albumService.showAllAlbums(), HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<AlbumDto> getSingleAlbum(@PathVariable @RequestBody Long id) {
		return new ResponseEntity<>(albumService.readSingleAlbum(id), HttpStatus.OK);
	}

	@GetMapping("/public/get/{id}")
	public ResponseEntity<AlbumDto> getPublicSingleAlbum(@PathVariable @RequestBody Long id) {
		return new ResponseEntity<>(albumService.readSingleAlbum(id), HttpStatus.OK);
	}
}
