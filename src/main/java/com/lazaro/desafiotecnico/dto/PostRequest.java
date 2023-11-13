package com.lazaro.desafiotecnico.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {

	@NotBlank
	private String titulo;

	@NotBlank
	private String conteudo;
}
