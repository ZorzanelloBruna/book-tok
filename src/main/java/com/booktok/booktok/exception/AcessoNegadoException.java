package com.booktok.booktok.exception;

public class AcessoNegadoException extends RuntimeException {

	public AcessoNegadoException (String mensagem) {
		super(mensagem);
	}
	
	public AcessoNegadoException (String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
