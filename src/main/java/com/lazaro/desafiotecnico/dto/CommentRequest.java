package com.lazaro.desafiotecnico.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {

	@NotBlank
	private String conteudo;

	@NotNull
	private Long postId;
}
