package com.lazaro.desafiotecnico.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumRequest {

	@NotBlank
	private String titulo;
}
