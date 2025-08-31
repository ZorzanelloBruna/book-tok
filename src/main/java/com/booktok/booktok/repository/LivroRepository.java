package com.booktok.booktok.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booktok.booktok.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {

	Livro findByIsbn(String Isbn);
}
