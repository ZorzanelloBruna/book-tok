package com.booktok.booktok.exception;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.ConstraintViolationException;

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
        throw new AcessoNegadoException("Você não tem permissão");
    }

    @GetMapping("/isbn")
    public void isbn() {
        throw new IsbnJaCadastradoException("1234567890", "O diário de uma Paixão");
    }
}
