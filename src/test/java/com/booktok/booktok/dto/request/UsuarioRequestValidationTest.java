package com.booktok.booktok.dto.request;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
public class UsuarioRequestValidationTest {
	
	private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveDetectarNomeEmBranco() {
        UsuarioRequest usuario = new UsuarioRequest();
        usuario.setNome(""); 
        usuario.setEmail("joao@email.com");

        Set<ConstraintViolation<UsuarioRequest>> violations = validator.validate(usuario);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nome")));
    }

    @Test
    void deveDetectarEmailInvalido() {
        UsuarioRequest usuario = new UsuarioRequest();
        usuario.setNome("João");
        usuario.setEmail("email-invalido");

        Set<ConstraintViolation<UsuarioRequest>> violations = validator.validate(usuario);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void devePassarComDadosValidos() {
        UsuarioRequest usuario = new UsuarioRequest();
        usuario.setNome("João");
        usuario.setEmail("joao@email.com");
        usuario.setVerSpoilers(false);

        Set<ConstraintViolation<UsuarioRequest>> violations = validator.validate(usuario);

        assertTrue(violations.isEmpty());
    }
}
