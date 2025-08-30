package com.booktok.booktok.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComentarioRequest {
	
	@NotNull(message = "Informação do livro é obrigatória")
	private Long livroId;
	
	@NotBlank(message = "Mensagem é obrigatória.")
	private String mensagem;
	
	private boolean spoiler = false;

}
