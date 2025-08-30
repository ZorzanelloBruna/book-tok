package com.booktok.booktok.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequest {
	
	@NotBlank(message = "Campo nome é obrigatório.")
	private String nome;
	
	@Email(message = "Digite um e-mail válido")
	@NotBlank(message = "Campo e-mail é obrigatório.")
	private String email;
	
	private boolean verSpoilers = false;
	
}
