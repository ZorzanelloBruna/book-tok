package com.booktok.booktok.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComentarioRequest {
	
	@NotNull(message = "Informação do livro é obrigatória")
	private Long livroId;
	
	@NotNull(message = "Informação do usuário é obrigatória")
	private Long usuarioId;
	
	@NotBlank(message = "Mensagem é obrigatória.")
	@Size(max= 500 , message = "Campo mensagem deve ter no maximo 500 caracteres.")
	private String mensagem;
	
	private boolean spoiler = false;

}
