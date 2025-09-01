package com.booktok.booktok.exception;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = DummyController.class)
public class GlobalExceptionHandlerTest {

	@Autowired
    private MockMvc mockMvc;

    // ✅ EmailJaCadastradoException
    @Test
    void handleEmailJaCadastrado_DeveRetornarConflict() throws Exception {
        mockMvc.perform(get("/dummy/email"))
            .andExpect(status().isConflict())
            .andExpect(content().string("E-mail já cadastrado"));
    }

    // ✅ EntidadeNaoEncontradaException
    @Test
    void handleEntidadeNaoEncontrada_DeveRetornarNotFound() throws Exception {
        mockMvc.perform(get("/dummy/entidade"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Livro com ID 1 não encontrado."));
    }

    // ✅ ConstraintViolationException
    @Test
    void handleConstraintViolation_DeveRetornarBadRequest() throws Exception {
        mockMvc.perform(get("/dummy/constraint"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(startsWith("ID inválido:")));
    }

    // ✅ AcessoNegadoException
    @Test
    void handleAcessoNegado_DeveRetornarConflict() throws Exception {
        mockMvc.perform(get("/dummy/acesso"))
            .andExpect(status().isConflict())
            .andExpect(content().string("Você não tem permissão"));
    }

    // ✅ IsbnJaCadastradoException
    @Test
    void handleIsbnJaCadastrado_DeveRetornarForbidden() throws Exception {
        mockMvc.perform(get("/dummy/isbn"))
            .andExpect(status().isForbidden())
            .andExpect(content().string("INBN 1234567890 ja cadastrada para o livro O diário de uma Paixão."));
    }
}
