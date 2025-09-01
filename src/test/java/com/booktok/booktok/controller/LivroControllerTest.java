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

import com.booktok.booktok.dto.request.LivroRequest;
import com.booktok.booktok.dto.response.LivroResponse;
import com.booktok.booktok.service.LivroService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(LivroController.class)
public class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LivroService service;

    private LivroRequest request;
    private LivroResponse response;

    @BeforeEach
    void setUp() {
        request = new LivroRequest();
        request.setTitulo("Dom Casmurro");
        request.setAutor("Machado de Assis");
        request.setResumo("Romance clássico");
        request.setAnoPublicacao(1899);
        request.setIsbn("1234567890");

        response = new LivroResponse(1L, "Dom Casmurro", "Machado de Assis", "Romance clássico", 1899, "1234567890");
    }

    @Test
    void salvarLivro_DeveRetornarStatusCreated() throws Exception {
        when(service.salvarLivro(any())).thenReturn(response);

        mockMvc.perform(post("/api/livro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.titulo").value("Dom Casmurro"));
    }

    @Test
    void buscarLivroPorId_DeveRetornarLivro() throws Exception {
        when(service.buscarLivroPorId(1L)).thenReturn(response);

        mockMvc.perform(get("/api/livro/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.autor").value("Machado de Assis"));
    }

    @Test
    void listarTodosLivros_DeveRetornarLista() throws Exception {
        when(service.listarLivros()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/livro"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].titulo").value("Dom Casmurro"));
    }

    @Test
    void atualizarLivro_DeveRetornarLivroAtualizado() throws Exception {
        when(service.atualizarLivro(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/livro/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.titulo").value("Dom Casmurro"));
    }

    @Test
    void deletarLivro_DeveRetornarNoContent() throws Exception {
        doNothing().when(service).deletarLivro(1L);

        mockMvc.perform(delete("/api/livro/1"))
            .andExpect(status().isNoContent());
    }

    // Utilitário para converter objetos em JSON
    private String asJsonString(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
