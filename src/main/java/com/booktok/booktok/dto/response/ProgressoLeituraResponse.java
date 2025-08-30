package com.booktok.booktok.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProgressoLeituraResponse {
	
	private Long usuarioId;
	private Long livroId;
	private int paginaAtual;
	private LocalDateTime dataRegistro; 

}
