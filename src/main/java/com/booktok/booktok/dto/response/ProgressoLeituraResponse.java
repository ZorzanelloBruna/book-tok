package com.booktok.booktok.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgressoLeituraResponse {
	
	private Long id;
	private Long usuarioId;
	private Long livroId;
	private int paginaAtual;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDateTime dataRegistro; 

}
