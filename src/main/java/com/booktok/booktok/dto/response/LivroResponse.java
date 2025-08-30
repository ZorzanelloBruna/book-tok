package com.booktok.booktok.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LivroResponse {

	private Long id;
	private String titulo;
	private String autor;
	private String resumo;
	private int anoPublicacao;
	private String isbn; 
	
}
