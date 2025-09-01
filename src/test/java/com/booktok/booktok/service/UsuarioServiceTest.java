package com.booktok.booktok.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.booktok.booktok.dto.request.UsuarioRequest;
import com.booktok.booktok.dto.response.UsuarioResponse;
import com.booktok.booktok.exception.EmailJaCadastradoException;
import com.booktok.booktok.exception.EntidadeNaoEncontradaException;
import com.booktok.booktok.model.Usuario;
import com.booktok.booktok.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

	@InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository repository;

    private Usuario usuario;
    private UsuarioRequest request;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Fulano");
        usuario.setEmail("fulano@email.com");
        usuario.setVerSpoilers(true);

        request = new UsuarioRequest();
        request.setNome("Fulano");
        request.setEmail("fulano@email.com");
        request.setVerSpoilers(true);
    }

    // Criar usuário com sucesso
    @Test
    void criarUsuario_ComEmailNovo_DeveSalvarERetornarResponse() {
        when(repository.findByEmail(request.getEmail())).thenReturn(null);
        when(repository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioResponse response = usuarioService.criarUsuario(request);

        assertNotNull(response);
        assertEquals(usuario.getId(), response.getId());
        verify(repository).findByEmail(request.getEmail());
        verify(repository).save(any(Usuario.class));
    }

    // Criar usuário com e-mail já cadastrado
    @Test
    void criarUsuario_ComEmailExistente_DeveLancarExcecao() {
        when(repository.findByEmail(request.getEmail())).thenReturn(usuario);

        assertThrows(EmailJaCadastradoException.class, () -> usuarioService.criarUsuario(request));
        verify(repository).findByEmail(request.getEmail());
        verify(repository, never()).save(any());
    }

    // Buscar usuário por ID existente
    @Test
    void buscarUsuarioPorID_ComIdExistente_DeveRetornarResponse() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));

        UsuarioResponse response = usuarioService.buscarUsuarioPorID(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(repository).findById(1L);
    }

    // Buscar usuário por ID inexistente
    @Test
    void buscarUsuarioPorID_ComIdInexistente_DeveLancarExcecao() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        EntidadeNaoEncontradaException ex = assertThrows(
            EntidadeNaoEncontradaException.class,
            () -> usuarioService.buscarUsuarioPorID(1L)
        );

        assertEquals("Usuario com ID 1 não encontrado.", ex.getMessage());
        verify(repository).findById(1L);
    }

    // Buscar todos os usuários
    @Test
    void buscarTodosUsuarios_DeveRetornarListaDeResponses() {
        when(repository.findAll()).thenReturn(List.of(usuario));

        List<UsuarioResponse> responses = usuarioService.buscarTodosUsuarios();

        assertEquals(1, responses.size());
        assertEquals("Fulano", responses.get(0).getNome());
        verify(repository).findAll();
    }

    // Atualizar usuário existente
    @Test
    void atualizarUsuario_ComIdExistente_DeveAtualizarERetornarResponse() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
        when(repository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioResponse response = usuarioService.atualizarUsuario(1L, request);

        assertNotNull(response);
        assertEquals("Fulano", response.getNome());
        verify(repository).findById(1L);
        verify(repository).save(usuario);
    }

    // Atualizar usuário inexistente
    @Test
    void atualizarUsuario_ComIdInexistente_DeveLancarExcecao() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class,
            () -> usuarioService.atualizarUsuario(1L, request));

        verify(repository).findById(1L);
        verify(repository, never()).save(any());
    }

    // Deletar usuário existente
    @Test
    void deletarUsuario_ComIdExistente_DeveDeletar() {
        when(repository.existsById(1L)).thenReturn(true);

        usuarioService.deletarUsuario(1L);

        verify(repository).existsById(1L);
        verify(repository).deleteById(1L);
    }

    // Deletar usuário inexistente
    @Test
    void deletarUsuario_ComIdInexistente_DeveLancarExcecao() {
        when(repository.existsById(1L)).thenReturn(false);

        EntidadeNaoEncontradaException exception = assertThrows(
            EntidadeNaoEncontradaException.class,
            () -> usuarioService.deletarUsuario(1L)
        );

        assertEquals("Usuario com ID 1 não encontrado.", exception.getMessage());
        verify(repository).existsById(1L);
        verify(repository, never()).deleteById(any());
    }
}
