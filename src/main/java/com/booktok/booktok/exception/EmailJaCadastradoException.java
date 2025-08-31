package com.booktok.booktok.exception;

public class EmailJaCadastradoException extends RuntimeException {

	public EmailJaCadastradoException (String mensagem) {
		super(mensagem);
	}
	
	public EmailJaCadastradoException (String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
