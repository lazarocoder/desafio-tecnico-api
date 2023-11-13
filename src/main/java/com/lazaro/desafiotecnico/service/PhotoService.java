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
import com.lazaro.desafiotecnico.dto.PhotoDto;
import com.lazaro.desafiotecnico.dto.PhotoRequest;
import com.lazaro.desafiotecnico.exception.BaseException;
import com.lazaro.desafiotecnico.exception.PhotoNotFoundException;
import com.lazaro.desafiotecnico.model.Photo;
import com.lazaro.desafiotecnico.repository.PhotoRepository;

@Service
public class PhotoService {

	@Autowired
	private AuthService authService;

	@Autowired
	private AlbumService albumService;

	@Autowired
	private PhotoRepository photoRepository;

	@Transactional(readOnly = true)
	public List<PhotoDto> showAllPhotos() {
		List<Photo> photos = photoRepository.findAll();
		return photos.stream().map(this::mapFromPhotoToDto).collect(toList());
	}

	@Transactional
	public void createPhoto(PhotoRequest photoRequest) {
		Photo photo = mapFromDtoToPhoto(photoRequest);
		photoRepository.save(photo);
	}

	@Transactional
	public PhotoDto updatePhoto(Long id, PhotoRequest photoRequest) {
		Photo photo = photoRepository.findById(id).orElseThrow(() -> new PhotoNotFoundException("For id " + id));
		photo.setDescricao(photoRequest.getDescricao());
		photo.setUrl(photoRequest.getUrl());
		photoRepository.save(photo);
		return mapFromPhotoToDto(photo);
	}

	@Transactional(readOnly = true)
	public PhotoDto readSinglePhoto(Long id) {
		Photo photo = photoRepository.findById(id).orElseThrow(() -> new PhotoNotFoundException("For id " + id));
		return mapFromPhotoToDto(photo);
	}

	private PhotoDto mapFromPhotoToDto(Photo photo) {
		PhotoDto photoDto = new PhotoDto();
		BeanUtils.copyProperties(photo, photoDto);
		return photoDto;
	}

	private Photo mapFromDtoToPhoto(PhotoRequest photoRequest) {
		Photo photo = new Photo();
		photo.setDescricao(photoRequest.getDescricao());
		photo.setUrl(photoRequest.getUrl());
		AlbumDto albumDto = albumService.readSingleAlbum(photoRequest.getAlbumId());
		photo.setAlbumId(albumDto.getId());
		User loggedInUser = authService.getCurrentUser()
				.orElseThrow(() -> new IllegalArgumentException("User Not Found"));
		photo.setDtCriacao(LocalDateTime.now());
		photo.setUserName(loggedInUser.getUsername());
		return photo;
	}

	@Transactional
	public void deletePhoto(Long id) {
		User loggedInUser = authService.getCurrentUser()
				.orElseThrow(() -> new IllegalArgumentException("User Not Found"));
		PhotoDto photoDto = readSinglePhoto(id);
		if (!loggedInUser.getUsername().equals(photoDto.getUserName())) {
			throw new BaseException("Apenas o dono de um álbum poderá excluí-lo.");
		}
		photoRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public List<PhotoDto> showAllCommentsByAlbumId(Long id) {
		List<Photo> photos = photoRepository.findAllByAlbumId(id);
		return photos.stream().map(this::mapFromPhotoToDto).collect(toList());
	}
}
