package com.booktok.booktok.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booktok.booktok.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Usuario findByEmail(String email);

}
