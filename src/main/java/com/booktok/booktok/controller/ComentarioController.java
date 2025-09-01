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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.booktok.booktok.dto.request.ComentarioRequest;
import com.booktok.booktok.dto.response.ComentarioResponse;
import com.booktok.booktok.service.ComentarioService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/comentario")
@Validated
public class ComentarioController {

	@Autowired
	ComentarioService service;
	
	@PostMapping
	public ResponseEntity<ComentarioResponse> registrarComentario(@Valid  @RequestBody ComentarioRequest request){
		ComentarioResponse novoComentario = service.registrarComentario(request);
		return new ResponseEntity<>(novoComentario, HttpStatus.CREATED);
	}
	
	@GetMapping("livro/{livroId}")
	public ResponseEntity<List<ComentarioResponse>> listarComentariosPorLivro ( @PathVariable @NotNull  Long livroId,
																				@Valid @RequestParam(required = false) Long usuarioId) {
		List<ComentarioResponse> listaComentarios = service.listarComentariosPorLivro(livroId, usuarioId);
		return ResponseEntity.ok(listaComentarios);
	}
	
	@GetMapping
	public ResponseEntity<List<ComentarioResponse>> listarTodosComentarios() {
		List<ComentarioResponse> listaComentarios = service.listarTodosComentarios();
		return ResponseEntity.ok(listaComentarios);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ComentarioResponse> atualizarComentario(@PathVariable @NotNull Long id, 
																  @Valid @RequestBody ComentarioRequest request ) {
		ComentarioResponse comentarioAtualizado = service.atualizarComentario(id,request);
		return ResponseEntity.ok(comentarioAtualizado);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> removerComentario(@PathVariable @NotNull Long id, 
			 									  @RequestParam @NotNull Long usuarioId) {
		service.removerComentario(id, usuarioId);
		return ResponseEntity.noContent().build();
	}
}
