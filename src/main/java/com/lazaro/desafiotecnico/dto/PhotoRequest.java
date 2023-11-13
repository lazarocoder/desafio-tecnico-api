package com.lazaro.desafiotecnico.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhotoRequest {

	@NotBlank
	private String descricao;

	@NotBlank
	private String url;

	@NotNull
	private Long albumId;
}
