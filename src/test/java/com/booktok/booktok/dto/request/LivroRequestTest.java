package com.booktok.booktok.dto.request;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LivroRequestTest {
	@Test
    void deveInstanciarComConstrutorCompleto() {
        LivroRequest livro = new LivroRequest(
            "O Senhor dos Anéis",
            "J.R.R. Tolkien",
            "Uma jornada épica pela Terra Média.",
            1954,
            "978-85-98078-01-3"
        );

        assertEquals("O Senhor dos Anéis", livro.getTitulo());
        assertEquals("J.R.R. Tolkien", livro.getAutor());
        assertEquals("Uma jornada épica pela Terra Média.", livro.getResumo());
        assertEquals(1954, livro.getAnoPublicacao());
        assertEquals("978-85-98078-01-3", livro.getIsbn());
    }
}
