package com.booktok.booktok.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comentario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "livro_id", nullable = false)
	private Livro livro;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;
	
	@Column(name = "mensagem" ,nullable = false, length = 500)
	private String mensagem;
	
	@Column(name="data_comentario",nullable = false)
	private LocalDateTime dataComentario;
	
	private boolean spoiler = false; 

}
