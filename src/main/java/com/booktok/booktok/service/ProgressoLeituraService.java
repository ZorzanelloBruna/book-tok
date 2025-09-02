package com.booktok.booktok.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booktok.booktok.dto.request.ProgressoLeituraRequest;
import com.booktok.booktok.dto.response.ProgressoLeituraResponse;
import com.booktok.booktok.exception.AcessoNegadoException;
import com.booktok.booktok.exception.EntidadeNaoEncontradaException;
import com.booktok.booktok.model.Livro;
import com.booktok.booktok.model.ProgressoLeitura;
import com.booktok.booktok.model.Usuario;
import com.booktok.booktok.repository.LivroRepository;
import com.booktok.booktok.repository.ProgressoLeituraRepository;
import com.booktok.booktok.repository.UsuarioRepository;

@Service
public class ProgressoLeituraService {
	
	@Autowired
	ProgressoLeituraRepository progressoRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	LivroRepository livroRepository;

	public ProgressoLeituraResponse registrarProgresso(ProgressoLeituraRequest request) {
		Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário", request.getUsuarioId()));
		Livro livro = livroRepository.findById(request.getLivroId())
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Livro", request.getLivroId()));
		Optional<ProgressoLeitura> progressoExistente = 
                progressoRepository.findByUsuarioAndLivro(usuario, livro);
        
        ProgressoLeitura progresso;
        if(progressoExistente.isPresent()) {
        	progresso = progressoExistente.get();
        	progresso.setPaginaAtual(request.getPaginaAtual());
        	progresso.setDataRegistro(LocalDateTime.now());
        } else {
        	progresso = new ProgressoLeitura(usuario, livro, request.getPaginaAtual());
        }
        
        ProgressoLeitura novoProgresso = progressoRepository.save(progresso);
        return toResponse(novoProgresso);                
	}
	
	public List<ProgressoLeituraResponse> listarProgressosPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário", usuarioId));
        
        return progressoRepository.findByUsuario(usuario)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
	
	public List<ProgressoLeituraResponse> listarProgressosPorLivro(Long livroId) { 
        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Livro", livroId));
        
        return progressoRepository.findByLivro(livro)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
	
	public ProgressoLeituraResponse atualizarProgresso(Long id, ProgressoLeituraRequest request) {
		if (!usuarioRepository.existsById(request.getUsuarioId())) {
	        throw new EntidadeNaoEncontradaException("Usuário", request.getUsuarioId());
	    }
	
	    ProgressoLeitura progresso = progressoRepository.findById(id)
	            .orElseThrow(() -> new EntidadeNaoEncontradaException("Progresso", id));
	    
	    if (!progresso.getUsuario().getId().equals(request.getUsuarioId())) {
	        throw new AcessoNegadoException("Você só pode atualizar seus próprios progressos");
	    }
	    
	    if (!livroRepository.existsById(request.getLivroId())) {
	        throw new EntidadeNaoEncontradaException("Livro", request.getLivroId());
	    }
	    
	    if (!progresso.getLivro().getId().equals(request.getLivroId())) {
	        throw new AcessoNegadoException("Não é permitido alterar o livro do progresso");
	    }
        
        progresso.setPaginaAtual(request.getPaginaAtual());
        progresso.setDataRegistro(LocalDateTime.now());
        
        ProgressoLeitura progressoAtualizado = progressoRepository.save(progresso);
        return toResponse(progressoAtualizado);
    }
	
	public void removerProgresso(Long id) {
        if (!progressoRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Progresso", id);
        }
        progressoRepository.deleteById(id);
    }
	
	private ProgressoLeituraResponse toResponse(ProgressoLeitura progresso) {
		ProgressoLeituraResponse response = new ProgressoLeituraResponse();
		  response.setId(progresso.getId());
	      response.setUsuarioId(progresso.getUsuario().getId());
	      response.setLivroId(progresso.getLivro().getId());     
	      response.setPaginaAtual(progresso.getPaginaAtual());
	      response.setDataRegistro(progresso.getDataRegistro());
        return response;
    }
}
