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

import com.booktok.booktok.dto.request.ProgressoLeituraRequest;
import com.booktok.booktok.dto.response.ProgressoLeituraResponse;
import com.booktok.booktok.service.ProgressoLeituraService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("api/progresso-leitura")
@Validated
public class ProgressoLeituraController {

	@Autowired
	ProgressoLeituraService service;
	
	@PostMapping
	public ResponseEntity<ProgressoLeituraResponse> registrarProgresso(@Valid @RequestBody ProgressoLeituraRequest request) {
		ProgressoLeituraResponse progresso = service.registrarProgresso(request);
		return new ResponseEntity<>(progresso, HttpStatus.CREATED);		
	}
	
	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<List<ProgressoLeituraResponse>> listarProgressosPorUsuario(@PathVariable @NotNull Long usuarioId){
		List<ProgressoLeituraResponse> listaPorUsuario = service.listarProgressosPorUsuario(usuarioId);
		return ResponseEntity.ok(listaPorUsuario);		
	}
	
	@GetMapping("/livro/{livroId}")
	public ResponseEntity<List<ProgressoLeituraResponse>> listarProgressosPorLivro(@PathVariable @NotNull Long livroId) {
		List<ProgressoLeituraResponse> listaPorLivro = service.listarProgressosPorLivro(livroId);
		return ResponseEntity.ok(listaPorLivro);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProgressoLeituraResponse> atualizarProgresso(@PathVariable @NotNull Long id,
																	   @Valid @RequestBody ProgressoLeituraRequest request) {
		ProgressoLeituraResponse progressoAtualizado = service.atualizarProgresso(id, request);
		return ResponseEntity.ok(progressoAtualizado);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> removerProgresso(@PathVariable @NotNull Long id){
		service.removerProgresso(id);
		return ResponseEntity.noContent().build();
	}	
}
