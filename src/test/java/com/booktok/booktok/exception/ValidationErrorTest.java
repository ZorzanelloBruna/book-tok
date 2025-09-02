package com.booktok.booktok.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ValidationErrorTest {

	@Test
    void deveSetarECapturarCamposCorretamente() {
        ValidationError error = new ValidationError();

        error.setField("email");
        error.setRejectedValue("email-invalido");
        error.setMessage("Digite um e-mail válido");

        assertEquals("email", error.getField());
        assertEquals("email-invalido", error.getRejectedValue());
        assertEquals("Digite um e-mail válido", error.getMessage());
    }
}
