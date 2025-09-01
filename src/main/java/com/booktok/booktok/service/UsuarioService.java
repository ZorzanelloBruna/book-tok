package com.booktok.booktok.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booktok.booktok.dto.request.UsuarioRequest;
import com.booktok.booktok.dto.response.UsuarioResponse;
import com.booktok.booktok.exception.EmailJaCadastradoException;
import com.booktok.booktok.exception.EntidadeNaoEncontradaException;
import com.booktok.booktok.model.Usuario;
import com.booktok.booktok.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository repository;
	
	public UsuarioResponse criarUsuario(UsuarioRequest request) {
		if(repository.findByEmail(request.getEmail()) != null) {
			throw new EmailJaCadastradoException("E-mail ja cadastrado");
		}		
		Usuario usuario = toEntity(request);
		Usuario novoUsuario = repository.save(usuario);
		return toResponse(novoUsuario);
	}
	
	public UsuarioResponse buscarUsuarioPorID(Long id) {
		Usuario usuario = repository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Usuario", id));
		return toResponse(usuario);
	}
	
	public List<UsuarioResponse> buscarTodosUsuarios(){
		return repository.findAll()
				.stream()
				.map(this::toResponse)
				.collect(Collectors.toList());
	}
	
	public UsuarioResponse atualizarUsuario(Long id, UsuarioRequest request) {
		Usuario usuarioEncontrado = repository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Usuario", id));   
		usuarioEncontrado.setNome(request.getNome());
		usuarioEncontrado.setEmail(request.getEmail());
		usuarioEncontrado.setVerSpoilers(request.isVerSpoilers());
		
		Usuario usuarioAtualizado = repository.save(usuarioEncontrado);
		return toResponse(usuarioAtualizado);
	}
	
	public void deletarUsuario(Long id) {
		if(!repository.existsById(id)) {
			throw new EntidadeNaoEncontradaException("Usuario", id);
		}
		repository.deleteById(id);		
	}
	
	private Usuario toEntity (UsuarioRequest request) {
		Usuario usuario = new Usuario();
		usuario.setNome(request.getNome());
		usuario.setEmail(request.getEmail());
		usuario.setVerSpoilers(request.isVerSpoilers());
		return usuario;		
	}
	
	private UsuarioResponse toResponse(Usuario usuario) {
		return new UsuarioResponse(
				usuario.getId(),
				usuario.getNome(),
				usuario.getEmail(),
				usuario.isVerSpoilers());		
	}

}
