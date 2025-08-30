package com.booktok.booktok.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgressoLeituraRequest {

	@NotNull(message = "O livro deve ser informado.")
	private Long livroId;
	
	@NotBlank(message = "Página atual deve ser informada.")
	private int paginaAtual;
}
