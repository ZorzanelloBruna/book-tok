package com.booktok.booktok.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booktok.booktok.dto.request.LivroRequest;
import com.booktok.booktok.dto.response.LivroResponse;
import com.booktok.booktok.exception.EntidadeNaoEncontradaException;
import com.booktok.booktok.exception.IsbnJaCadastradoException;
import com.booktok.booktok.model.Livro;
import com.booktok.booktok.repository.LivroRepository;

@Service
public class LivroService {

	@Autowired
	LivroRepository repository;
	
	public LivroResponse salvarLivro(LivroRequest request) {
		if(repository.findByIsbn(request.getIsbn()) != null) {
			throw new IsbnJaCadastradoException(request.getIsbn(), request.getTitulo());
		}
		Livro livro = toEntity(request);
		Livro livroSalvo = repository.save(livro);
		return toResponse(livroSalvo);
	}
	
	public LivroResponse buscarLivroPorId(Long id) {
		Livro livro = repository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Livro", id));
		return toResponse(livro);
	}
	
	public List<LivroResponse> listarLivros() {
		return repository.findAll()
				.stream()
				.map(this::toResponse)
				.collect(Collectors.toList());
	}
	
	public LivroResponse atualizarLivro(Long id, LivroRequest request) {
		Livro livroEncontrado = repository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Livro", id));
		livroEncontrado.setTitulo(request.getTitulo());
		livroEncontrado.setAutor(request.getAutor());
		livroEncontrado.setResumo(request.getResumo());
		livroEncontrado.setAnoPublicacao(request.getAnoPublicacao());
		livroEncontrado.setIsbn(request.getIsbn());
		
		Livro livroAtualizado = repository.save(livroEncontrado);
		return toResponse(livroAtualizado);
	}
	
	public void deletarLivro(Long id) {
		if(!repository.existsById(id)) {
			new EntidadeNaoEncontradaException("Livro", id);
		}
		repository.deleteById(id);
	}
	
	private Livro toEntity(LivroRequest request) {
		Livro livro = new Livro();
		livro.setTitulo(request.getTitulo());
		livro.setAutor(request.getAutor());
		livro.setResumo(request.getResumo());
		livro.setAnoPublicacao(request.getAnoPublicacao());
		livro.setIsbn(request.getIsbn());
		return livro;
	}
	
	private LivroResponse toResponse(Livro livro) {
		return new LivroResponse(
				livro.getId(),
				livro.getTitulo(),
				livro.getAutor(),
				livro.getResumo(),
				livro.getAnoPublicacao(),
				livro.getIsbn());
	}
}
