package com.booktok.booktok.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booktok.booktok.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

}
