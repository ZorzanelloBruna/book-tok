package com.booktok.booktok.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.booktok.booktok.dto.request.UsuarioRequest;
import com.booktok.booktok.dto.response.UsuarioResponse;
import com.booktok.booktok.service.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService service;

    private UsuarioRequest request;
    private UsuarioResponse response;

    @BeforeEach
    void setUp() {
        request = new UsuarioRequest();
        request.setNome("Fulano");
        request.setEmail("fulano@email.com");
        request.setVerSpoilers(true);

        response = new UsuarioResponse(1L, "Fulano", "fulano@email.com", true);
    }

    @Test
    void salvarUsuario_DeveRetornarStatusCreated() throws Exception {
        when(service.criarUsuario(any())).thenReturn(response);

        mockMvc.perform(post("/api/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.nome").value("Fulano"));
    }

    @Test
    void buscarUsuarioPorId_DeveRetornarUsuario() throws Exception {
        when(service.buscarUsuarioPorID(1L)).thenReturn(response);

        mockMvc.perform(get("/api/usuario/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.email").value("fulano@email.com"));
    }

    @Test
    void buscarUsuarios_DeveRetornarLista() throws Exception {
        when(service.buscarTodosUsuarios()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/usuario"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void atualizarUsuario_DeveRetornarUsuarioAtualizado() throws Exception {
        when(service.atualizarUsuario(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/usuario/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome").value("Fulano"));
    }

    @Test
    void deletarUsuario_DeveRetornarNoContent() throws Exception {
        doNothing().when(service).deletarUsuario(1L);

        mockMvc.perform(delete("/api/usuario/1"))
            .andExpect(status().isNoContent());
    }

    // Utilitário para converter objetos em JSON
    private String asJsonString(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
