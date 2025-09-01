package com.booktok.booktok.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booktok.booktok.dto.request.ComentarioRequest;
import com.booktok.booktok.dto.response.ComentarioResponse;
import com.booktok.booktok.exception.AcessoNegadoException;
import com.booktok.booktok.exception.EntidadeNaoEncontradaException;
import com.booktok.booktok.model.Comentario;
import com.booktok.booktok.model.Livro;
import com.booktok.booktok.model.Usuario;
import com.booktok.booktok.repository.ComentarioRepository;
import com.booktok.booktok.repository.LivroRepository;
import com.booktok.booktok.repository.UsuarioRepository;

@Service
public class ComentarioService {

	@Autowired
	ComentarioRepository comentarioRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	LivroRepository livroRepository;
	
	public ComentarioResponse registrarComentario(ComentarioRequest request) {
		Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário", request.getUsuarioId()));
		Livro livro = livroRepository.findById(request.getLivroId())
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Livro", request.getLivroId()));
		Comentario comentario = new Comentario();
		comentario.setLivro(livro);
		comentario.setUsuario(usuario);
		comentario.setMensagem(request.getMensagem());
		comentario.setDataComentario(LocalDateTime.now());
		comentario.setSpoiler(request.isSpoiler());
		
		Comentario novoComentario = comentarioRepository.save(comentario);
		return toResponse(novoComentario);
	}
	
	public List<ComentarioResponse> listarComentariosPorLivro(Long livroId, Long usuarioId) {
		// Verificar se livro existe
		if (!livroRepository.existsById(livroId)) {
	        throw new EntidadeNaoEncontradaException("Livro", livroId);
	    }

		Boolean verSpoilers = null;

		// Se usuarioId foi fornecido, validar e buscar preferências
		if (usuarioId != null) {
			Usuario usuario = usuarioRepository.findById(usuarioId)
					.orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário", usuarioId));
			verSpoilers = usuario.isVerSpoilers();
		}

		// Buscar comentários com base nas preferências
		List<Comentario> comentarios;
		if (verSpoilers != null && verSpoilers) {
			comentarios = comentarioRepository.findByLivroId(livroId);
		} else {
			comentarios = comentarioRepository.findByLivroIdAndSpoilerFalse(livroId);
		}

		return comentarios.stream().map(this::toResponse).collect(Collectors.toList());
	}
		
	public List<ComentarioResponse> listarTodosComentarios(){
		return comentarioRepository.findAll()
				.stream()
				.map(this::toResponse)
				.collect(Collectors.toList());
	}
	
	public ComentarioResponse atualizarComentario(Long id, ComentarioRequest request) {
		Comentario comentario = comentarioRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Comentário", request.getLivroId()));
		
		if(!comentario.getUsuario().getId().equals(request.getUsuarioId())) {
			throw new AcessoNegadoException("Você só pode editar seus próprios comentários");
		}
		
		comentario.setMensagem(request.getMensagem());
		comentario.setDataComentario(LocalDateTime.now());
		comentario.setSpoiler(request.isSpoiler());
		
		Comentario comentarioAtualizado = comentarioRepository.save(comentario);
		
		return toResponse(comentarioAtualizado);
	}
	
	public void removerComentario (Long id, Long usuarioId) {
		Comentario comentario = comentarioRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Comentário", id));
		
		if(!comentario.getUsuario().getId().equals(usuarioId)) {
			throw new AcessoNegadoException("Você só pode editar seus próprios comentários");
		}
		comentarioRepository.deleteById(id);
	}

	private ComentarioResponse toResponse(Comentario comentario) {
		ComentarioResponse response = new ComentarioResponse();
		response.setId(comentario.getId());
		response.setLivroId(comentario.getLivro().getId());
		response.setUsuarioId(comentario.getUsuario().getId());
		response.setMensagem(comentario.getMensagem());
		response.setDataComentario(comentario.getDataComentario());
		response.setSpoiler(comentario.isSpoiler());
		return response;
	}
}
