package com.booktok.booktok.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class ProgressoLeitura {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuarioId;
	
	@ManyToOne
	@JoinColumn(name = "livro_id", nullable = false)
	private Livro livroId;
	
	@Column(name = "pagina_atual",nullable = false)
	private Integer paginaAtual;
	
	@Column(name = "data_registro", nullable = false)
	private LocalDateTime dataRegistro; 
	
	public ProgressoLeitura() {
        this.dataRegistro = LocalDateTime.now();
    }
    
    public ProgressoLeitura(Usuario usuario, Livro livro, Integer paginaAtual) {
        this.usuarioId = usuario;
        this.livroId = livro;
        this.paginaAtual = paginaAtual;
        this.dataRegistro = LocalDateTime.now();
    }
}
