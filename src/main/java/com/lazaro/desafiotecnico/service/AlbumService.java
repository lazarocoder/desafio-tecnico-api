package com.lazaro.desafiotecnico.service;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lazaro.desafiotecnico.dto.AlbumDto;
import com.lazaro.desafiotecnico.dto.AlbumRequest;
import com.lazaro.desafiotecnico.exception.BaseException;
import com.lazaro.desafiotecnico.exception.AlbumNotFoundException;
import com.lazaro.desafiotecnico.model.Album;
import com.lazaro.desafiotecnico.model.Photo;
import com.lazaro.desafiotecnico.repository.AlbumRepository;
import com.lazaro.desafiotecnico.repository.PhotoRepository;

@Service
public class AlbumService {

	@Autowired
	private AuthService authService;

	@Autowired
	private AlbumRepository albumRepository;

	@Autowired
	private PhotoRepository photoRepository;

	@Transactional
	public List<AlbumDto> showAllAlbums() {
		List<Album> albums = albumRepository.findAll();
		return albums.stream().map(this::mapFromAlbumToDto).collect(toList());
	}

	@Transactional
	public void createAlbum(AlbumRequest albumRequest) {
		Album album = mapFromDtoToAlbum(albumRequest);
		albumRepository.save(album);
	}

	@Transactional
	public AlbumDto updateAlbum(Long id, AlbumRequest albumRequest) {
		Album album = albumRepository.findById(id).orElseThrow(() -> new AlbumNotFoundException("For id " + id));
		album.setTitulo(albumRequest.getTitulo());
		albumRepository.save(album);
		return mapFromAlbumToDto(album);
	}

	@Transactional
	public AlbumDto readSingleAlbum(Long id) {
		Album album = albumRepository.findById(id).orElseThrow(() -> new AlbumNotFoundException("For id " + id));
		return mapFromAlbumToDto(album);
	}

	private AlbumDto mapFromAlbumToDto(Album album) {
		AlbumDto albumDto = new AlbumDto();
		BeanUtils.copyProperties(album, albumDto);
		return albumDto;
	}

	private Album mapFromDtoToAlbum(AlbumRequest albumRequest) {
		Album album = new Album();
		album.setTitulo(albumRequest.getTitulo());
		User loggedInUser = authService.getCurrentUser()
				.orElseThrow(() -> new IllegalArgumentException("User Not Found"));
		album.setDtCriacao(LocalDateTime.now());
		album.setUserName(loggedInUser.getUsername());
		return album;
	}

	@Transactional
	public void deleteAlbum(Long id) {
		User loggedInUser = authService.getCurrentUser()
				.orElseThrow(() -> new IllegalArgumentException("User Not Found"));
		AlbumDto albumDto = readSingleAlbum(id);
		if (!loggedInUser.getUsername().equals(albumDto.getUserName())) {
			throw new BaseException("Apenas o dono de um álbum poderá excluí-lo.");
		}
		List<Photo> photos = photoRepository.findAllByAlbumId(id);
		for (Photo photo : photos) {
			photoRepository.delete(photo);
		}
		albumRepository.deleteById(id);
	}
}
