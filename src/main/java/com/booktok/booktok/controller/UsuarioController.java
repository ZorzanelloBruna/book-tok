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

import com.booktok.booktok.dto.request.UsuarioRequest;
import com.booktok.booktok.dto.response.UsuarioResponse;
import com.booktok.booktok.service.UsuarioService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("api/usuario")
@Validated
public class UsuarioController {
	
	@Autowired
	UsuarioService service;
	
	@PostMapping
	public ResponseEntity<UsuarioResponse> salvarUsuario(@RequestBody @Valid UsuarioRequest request) {
		UsuarioResponse novoUsuario = service.criarUsuario(request);
		return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioResponse> buscarUsuarioPorId (@PathVariable @NotNull Long id){
		UsuarioResponse usuarioEncontrado = service.buscarUsuarioPorID(id);
		return ResponseEntity.ok(usuarioEncontrado);
	}
	
	@GetMapping
	public ResponseEntity<List<UsuarioResponse>> buscarUsuarios(){
		List<UsuarioResponse> lista = service.buscarTodosUsuarios();
		return ResponseEntity.ok(lista);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioResponse> atualizarUsuario(@PathVariable @NotNull Long id,
															@RequestBody @Valid UsuarioRequest request) {
		UsuarioResponse usuario = service.atualizarUsuario(id, request);
		return ResponseEntity.ok(usuario);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarUsuario(@PathVariable @NotNull Long id) {
		service.deletarUsuario(id);
		return ResponseEntity.noContent().build();		
	}
}
