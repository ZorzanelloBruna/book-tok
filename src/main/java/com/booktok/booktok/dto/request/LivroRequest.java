package com.booktok.booktok.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LivroRequest {

	@NotBlank(message = "Campo título é obrigatorio.")
	private String titulo;
	
	@NotBlank(message = "Campo autor é obrigatório.")
	private String autor;
	
	private String resumo;
	
	@NotNull(message = "Campo ano de Publicação é obrigatório")
	private int anoPublicacao;
	
	@NotBlank(message = "Campo ISBN é obrigatório.")
	private String isbn; 
	
}
