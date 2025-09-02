package com.booktok.booktok.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgressoLeituraRequest {
	
	@NotNull(message = "O usuário deve ser informado.")
	private Long usuarioId;

	@NotNull(message = "O livro deve ser informado.")
	private Long livroId;
	
	@NotNull(message = "Página atual deve ser informada.")
	@Min(0)
	private Integer paginaAtual;
}
