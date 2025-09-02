package com.booktok.booktok.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		List<ValidationError> validationErrors = ex.getBindingResult().getFieldErrors().stream().map(error -> {
			ValidationError ve = new ValidationError();
			ve.setField(error.getField());
			ve.setRejectedValue(error.getRejectedValue());
			ve.setMessage(error.getDefaultMessage());
			return ve;
		}).collect(Collectors.toList());

		ErrorResponse response = new ErrorResponse();
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setTimestamp(LocalDateTime.now().toString());
		response.setPath(request.getRequestURI());
		response.setMessage("Erro de validacao");
		response.setErrors(validationErrors);

		return ResponseEntity.badRequest().body(response);
	}
	
	@ExceptionHandler(EmailJaCadastradoException.class)
	public ResponseEntity<String> handleEmailJaCadastrado(EmailJaCadastradoException ex,
														  HttpServletRequest request) {
		ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.CONFLICT.value());
        response.setTimestamp(LocalDateTime.now().toString());
        response.setPath(request.getRequestURI());
        response.setMessage(ex.getMessage());
	    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<ErrorResponse> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex,
																	 HttpServletRequest request) {
		ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setTimestamp(LocalDateTime.now().toString());
        response.setPath(request.getRequestURI());
        response.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex,
															HttpServletRequest request) {
		ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setTimestamp(LocalDateTime.now().toString());
        response.setPath(request.getRequestURI());
        response.setMessage("ID invalido: " + ex.getMessage());
        
	    return ResponseEntity.badRequest().body(response);
	}
	@ExceptionHandler(AcessoNegadoException.class)
	public ResponseEntity<ErrorResponse> handleAcessoNegado(AcessoNegadoException ex,
													 HttpServletRequest request) {
		ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.CONFLICT.value());
        response.setTimestamp(LocalDateTime.now().toString());
        response.setPath(request.getRequestURI());
        response.setMessage(ex.getMessage());
	    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}
	
	@ExceptionHandler(IsbnJaCadastradoException.class)
	public ResponseEntity<ErrorResponse> handleIsbnJaCadastrado(IsbnJaCadastradoException ex,
														 HttpServletRequest request) {
		ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setTimestamp(LocalDateTime.now().toString());
        response.setPath(request.getRequestURI());
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	}
}
