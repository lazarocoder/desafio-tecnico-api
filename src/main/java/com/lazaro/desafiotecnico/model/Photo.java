package com.lazaro.desafiotecnico.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "tb_photo")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Photo {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String descricao;

	@Column(nullable = false)
	private String url;

	@Column(name = "dt_criacao", nullable = false)
	private LocalDateTime dtCriacao;

	@Column(name = "album_id", nullable = false)
	private Long albumId;

	@Column(name = "user_name", nullable = false)
	private String userName;
}
