package com.lazaro.desafiotecnico.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhotoDto {

	private Long id;

	private String descricao;

	private String url;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private LocalDateTime dtCriacao;

	private Long albumId;

	private String userName;
}
