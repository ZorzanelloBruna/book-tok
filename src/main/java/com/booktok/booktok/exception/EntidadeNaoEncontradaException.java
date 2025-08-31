package com.booktok.booktok.exception;

public class EntidadeNaoEncontradaException  extends RuntimeException {
	
	public EntidadeNaoEncontradaException (String mensagem) {
		super(mensagem);
	}
	
	public EntidadeNaoEncontradaException (String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
	
	public EntidadeNaoEncontradaException (String entidade, Long id) {
		super(String.format("%s com ID %d não encontrado.", entidade, id));
	}
}
