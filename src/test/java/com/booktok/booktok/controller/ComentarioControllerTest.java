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

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.booktok.booktok.dto.request.ComentarioRequest;
import com.booktok.booktok.dto.response.ComentarioResponse;
import com.booktok.booktok.service.ComentarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ComentarioController.class)
public class ComentarioControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComentarioService service;

    private ComentarioRequest request;
    private ComentarioResponse response;

    @BeforeEach
    void setUp() {
        request = new ComentarioRequest();
        request.setUsuarioId(1L);
        request.setLivroId(1L);
        request.setMensagem("Comentário de teste");
        request.setSpoiler(false);

        response = new ComentarioResponse();
        response.setId(1L);
        response.setUsuarioId(1L);
        response.setLivroId(1L);
        response.setMensagem("Comentário de teste");
        response.setSpoiler(false);
        response.setDataComentario(LocalDateTime.now());
    }

    @Test
    void registrarComentario_DeveRetornarStatusCreated() throws Exception {
        when(service.registrarComentario(any())).thenReturn(response);

        mockMvc.perform(post("/api/comentario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.mensagem").value("Comentário de teste"));
    }

    // GET /api/comentario/livro/{livroId}?usuarioId=1
    @Test
    void listarComentariosPorLivro_DeveRetornarLista() throws Exception {
        when(service.listarComentariosPorLivro(1L, 1L)).thenReturn(List.of(response));

        mockMvc.perform(get("/api/comentario/livro/1")
                .param("usuarioId", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L));
    }
    
    @Test
    void listarTodosComentarios_DeveRetornarListaCompleta() throws Exception {
        when(service.listarTodosComentarios()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/comentario"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].mensagem").value("Comentário de teste"));
    }

    @Test
    void atualizarComentario_DeveRetornarComentarioAtualizado() throws Exception {
        when(service.atualizarComentario(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/comentario/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.mensagem").value("Comentário de teste"));
    }

    // DELETE /api/comentario/{id}?usuarioId=1
    @Test
    void removerComentario_DeveRetornarNoContent() throws Exception {
        doNothing().when(service).removerComentario(1L, 1L);

        mockMvc.perform(delete("/api/comentario/1")
                .param("usuarioId", "1"))
            .andExpect(status().isNoContent());
    }

    // Utilitário para converter objetos em JSON
    private String asJsonString(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
