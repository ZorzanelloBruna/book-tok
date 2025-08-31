package com.booktok.booktok.exception;

public class IsbnJaCadastradoException extends RuntimeException {

	public IsbnJaCadastradoException (String mensagem) {
		super(mensagem);
	}
	
	public IsbnJaCadastradoException (String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
	
	public IsbnJaCadastradoException (String isbn, String titulo) {
		super(String.format("INBN %s ja cadastrada para o livro %s.", isbn, titulo));
	}
}
