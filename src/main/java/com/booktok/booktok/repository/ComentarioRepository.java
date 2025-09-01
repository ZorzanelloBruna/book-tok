package com.booktok.booktok.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.booktok.booktok.model.Comentario;
import com.booktok.booktok.model.Livro;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

	// Buscar comentários por livro (sem filtro de spoiler)
    List<Comentario> findByLivroId(Long livroId);
    
    // Buscar comentários por livro excluindo spoilers
    @Query("SELECT c FROM Comentario c WHERE c.livro.id = :livroId AND c.spoiler = false")
    List<Comentario> findByLivroIdAndSpoilerFalse(@Param("livroId") Long livroId);
    
    List<Comentario> findByLivroId(Livro livro);
}
