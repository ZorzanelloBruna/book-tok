package com.booktok.booktok.controller;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booktok.booktok.dto.request.UsuarioRequest;
import com.booktok.booktok.exception.AcessoNegadoException;
import com.booktok.booktok.exception.EmailJaCadastradoException;
import com.booktok.booktok.exception.EntidadeNaoEncontradaException;
import com.booktok.booktok.exception.IsbnJaCadastradoException;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/dummy")
public class DummyController {

	@GetMapping("/email")
    public void email() {
        throw new EmailJaCadastradoException("E-mail já cadastrado");
    }

    @GetMapping("/entidade")
    public void entidade() {
        throw new EntidadeNaoEncontradaException("Livro", 1L);
    }

    @GetMapping("/constraint")
    public void constraint() {
        throw new ConstraintViolationException("ID deve ser positivo", Set.of());
    }

    @GetMapping("/acesso")
    public void acesso() {
        throw new AcessoNegadoException("Voce nao tem permissao");
    }

    @GetMapping("/isbn")
    public void isbn() {
        throw new IsbnJaCadastradoException("1234567890", "O diário de uma Paixão");
    }
    @PostMapping("/validacao")
    public ResponseEntity<String> validar(@RequestBody @Valid UsuarioRequest request) {
        return ResponseEntity.ok("OK");
    }
}
