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

import com.booktok.booktok.dto.request.ProgressoLeituraRequest;
import com.booktok.booktok.dto.response.ProgressoLeituraResponse;
import com.booktok.booktok.service.ProgressoLeituraService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProgressoLeituraController.class)
public class ProgressoLeituraControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProgressoLeituraService service;

	private ProgressoLeituraRequest request;
	private ProgressoLeituraResponse response;

	@BeforeEach
	void setUp() {
		request = new ProgressoLeituraRequest();
		request.setUsuarioId(1L);
		request.setLivroId(1L);
		request.setPaginaAtual(50);

		response = new ProgressoLeituraResponse();
		response.setId(1L);
		response.setUsuarioId(1L);
		response.setLivroId(1L);
		response.setPaginaAtual(50);
		response.setDataRegistro(LocalDateTime.now());
	}

	@Test
	void registrarProgresso_DeveRetornarStatusCreated() throws Exception {
		when(service.registrarProgresso(any())).thenReturn(response);

		mockMvc.perform(
				post("/api/progresso-leitura").contentType(MediaType.APPLICATION_JSON).content(asJsonString(request)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.paginaAtual").value(50));
	}

	// /api/progresso-leitura/usuario/{usuarioId}
	@Test
	void listarProgressosPorUsuario_DeveRetornarLista() throws Exception {
		when(service.listarProgressosPorUsuario(1L)).thenReturn(List.of(response));

		mockMvc.perform(get("/api/progresso-leitura/usuario/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].usuarioId").value(1L));
	}

	// /api/progresso-leitura/livro/{livroId}
	@Test
	void listarProgressosPorLivro_DeveRetornarLista() throws Exception {
		when(service.listarProgressosPorLivro(1L)).thenReturn(List.of(response));

		mockMvc.perform(get("/api/progresso-leitura/livro/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].livroId").value(1L));
	}

	@Test
	void atualizarProgresso_DeveRetornarProgressoAtualizado() throws Exception {
		when(service.atualizarProgresso(eq(1L), any())).thenReturn(response);

		mockMvc.perform(
				put("/api/progresso-leitura/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(request)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.paginaAtual").value(50));
	}

	@Test
	void removerProgresso_DeveRetornarNoContent() throws Exception {
		doNothing().when(service).removerProgresso(1L);

		mockMvc.perform(delete("/api/progresso-leitura/1")).andExpect(status().isNoContent());
	}

	// Utilitário para converter objetos em JSON
	private String asJsonString(Object obj) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(obj);
	}
}
