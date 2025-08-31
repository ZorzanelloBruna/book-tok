package com.booktok.booktok.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booktok.booktok.model.Livro;
import com.booktok.booktok.model.ProgressoLeitura;
import com.booktok.booktok.model.Usuario;

public interface ProgressoLeituraRepository extends JpaRepository<ProgressoLeitura, Long>{

	List<ProgressoLeitura> findByUsuarioId(Usuario usuarioId);
	
	List<ProgressoLeitura> findByLivroId(Livro livroId);
	
	Optional<ProgressoLeitura> findByUsuarioIdAndLivroId(Usuario usuarioId, Livro livroId);
	
	boolean existsByUsuarioIdAndLivroId(Usuario usuarioId, Livro livroId);
}
