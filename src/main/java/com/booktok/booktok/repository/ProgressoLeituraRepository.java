package com.booktok.booktok.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booktok.booktok.model.Livro;
import com.booktok.booktok.model.ProgressoLeitura;
import com.booktok.booktok.model.Usuario;

public interface ProgressoLeituraRepository extends JpaRepository<ProgressoLeitura, Long>{

	List<ProgressoLeitura> findByUsuarioId(Usuario usuario);
	
	List<ProgressoLeitura> findByLivroId(Livro livro);
	
	Optional<ProgressoLeitura> findByUsuarioIdAndLivroId(Usuario usuario, Livro livro);
	
	boolean existsByUsuarioIdAndLivroId(Usuario usuario, Livro livro);
}
