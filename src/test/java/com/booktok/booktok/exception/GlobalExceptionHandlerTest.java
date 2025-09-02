package com.booktok.booktok.exception;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.booktok.booktok.controller.DummyController;

@WebMvcTest(controllers = DummyController.class)
public class GlobalExceptionHandlerTest {

	@Autowired
    private MockMvc mockMvc;

    @Test
    void handleEmailJaCadastrado_DeveRetornarConflict() throws Exception {
        mockMvc.perform(get("/dummy/email"))
            .andExpect(status().isConflict())
            .andExpect(content().string("E-mail já cadastrado"));
    }

    @Test
    void handleEntidadeNaoEncontrada_DeveRetornarNotFound() throws Exception {
        mockMvc.perform(get("/dummy/entidade"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Livro com ID 1 nao encontrado."));

    }

    @Test
    void handleConstraintViolation_DeveRetornarBadRequest() throws Exception {
        mockMvc.perform(get("/dummy/constraint"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(startsWith("ID invalido:")));
    }

    @Test
    void handleAcessoNegado_DeveRetornarConflict() throws Exception {
        mockMvc.perform(get("/dummy/acesso"))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.message").value("Voce nao tem permissao"));
    }

    @Test
    void handleIsbnJaCadastrado_DeveRetornarForbidden() throws Exception {
        mockMvc.perform(get("/dummy/isbn"))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.message").value("INBN 1234567890 ja cadastrada para o livro O diário de uma Paixão."));
    }
    
    @Test
    void handleValidationException_DeveRetornarBadRequestComCampos() throws Exception {
        String jsonInvalido = """
            {
              "nome": "",
              "email": "email-invalido"
            }
        """;

        mockMvc.perform(post("/dummy/validacao")
                .contentType("application/json")
                .content(jsonInvalido))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("Erro de validacao"))
            .andExpect(jsonPath("$.errors").isArray())
            .andExpect(jsonPath("$.errors[*].field", containsInAnyOrder("nome", "email")))
            .andExpect(jsonPath("$.errors[*].message", containsInAnyOrder(
            	    "Campo nome é obrigatório.",
            	    "Digite um e-mail válido"
            	)));
    }
}
