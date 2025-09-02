package com.booktok.booktok.dto.request;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioRequestTest {

	@Test
    void deveSetarECapturarCamposCorretamente() {
        UsuarioRequest usuario = new UsuarioRequest();
        usuario.setNome("João");
        usuario.setEmail("joao@email.com");
        usuario.setVerSpoilers(true);

        assertEquals("João", usuario.getNome());
        assertEquals("joao@email.com", usuario.getEmail());
        assertTrue(usuario.isVerSpoilers());
    }
}
