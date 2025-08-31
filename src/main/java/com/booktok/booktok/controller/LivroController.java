package com.booktok.booktok.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booktok.booktok.dto.request.LivroRequest;
import com.booktok.booktok.dto.response.LivroResponse;
import com.booktok.booktok.service.LivroService;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("api/livro")
@Validated
public class LivroController {

	@Autowired
	LivroService service;
	
	@PostMapping
	public ResponseEntity<LivroResponse> salvarLivro(@RequestBody LivroRequest request) {
		LivroResponse livro = service.salvarLivro(request);
		return new ResponseEntity<>(livro, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<LivroResponse> buscarLivroPorId(@PathVariable @NotNull Long id){
		LivroResponse livro = service.buscarLivroPorId(id);
		return ResponseEntity.ok(livro);
	}
	
	@GetMapping
	public ResponseEntity<List<LivroResponse>> listarTodosLivros(){
		List<LivroResponse> lista = service.listarLivros();
		return ResponseEntity.ok(lista);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<LivroResponse> atualizarLivro(@PathVariable @NotNull Long id,
														@RequestBody LivroRequest request) {
		LivroResponse livro = service.atualizarLivro(id, request);
		return ResponseEntity.ok(livro);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarLivro(@PathVariable @NotNull Long id) {
		service.deletarLivro(id);
		return ResponseEntity.noContent().build();
	}	
}
