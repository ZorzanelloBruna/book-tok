package com.booktok.booktok.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComentarioResponse {
	
	private Long id;
	
	private Long livroId;
	
	private Long usuarioId;
	
	private String mensagem;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDateTime dataComentario;
	
	private boolean spoiler;

}
