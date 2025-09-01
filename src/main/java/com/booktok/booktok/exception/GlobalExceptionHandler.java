package com.booktok.booktok.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(EmailJaCadastradoException.class)
	public ResponseEntity<String> handleEmailJaCadastrado(EmailJaCadastradoException ex) {
	    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<String> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex) {
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	            .body("ID inválido: " + ex.getMessage());
	}
	@ExceptionHandler(AcessoNegadoException.class)
	public ResponseEntity<String> handleAcessoNegado(AcessoNegadoException ex) {
	    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
	@ExceptionHandler(IsbnJaCadastradoException.class)
	public ResponseEntity<String> handleIsbnJaCadastrado(IsbnJaCadastradoException ex) {
	    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
	}
}
