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
public class ComentarioResponse {
	
	private Long id;
	private Long livroId;
	private Long usuarioId;
	private String mensagem;
	private LocalDateTime dataComentario;
	private boolean spoiler;

}
