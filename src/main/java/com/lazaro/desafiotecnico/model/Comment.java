package com.lazaro.desafiotecnico.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "tb_comment")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Comment {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	@Column(nullable = false)
	private String conteudo;

	@Column(name = "dt_criacao", nullable = false)
	private LocalDateTime dtCriacao;
	
	@Column(name = "post_id", nullable = false)
	private Long postId;

	@Column(name = "user_name", nullable = false)
	private String userName;
}
