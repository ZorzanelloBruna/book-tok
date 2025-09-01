package com.booktok.booktok.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.booktok.booktok.dto.request.ComentarioRequest;
import com.booktok.booktok.dto.response.ComentarioResponse;
import com.booktok.booktok.exception.AcessoNegadoException;
import com.booktok.booktok.exception.EntidadeNaoEncontradaException;
import com.booktok.booktok.model.Comentario;
import com.booktok.booktok.model.Livro;
import com.booktok.booktok.model.Usuario;
import com.booktok.booktok.repository.ComentarioRepository;
import com.booktok.booktok.repository.LivroRepository;
import com.booktok.booktok.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class ComentarioServiceTest {

	@InjectMocks
    private ComentarioService comentarioService;

    @Mock
    private ComentarioRepository comentarioRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private LivroRepository livroRepository;

    private Usuario usuario;
    private Livro livro;
    private Comentario comentario;
    private ComentarioRequest request;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setVerSpoilers(true);

        livro = new Livro();
        livro.setId(1L);

        comentario = new Comentario();
        comentario.setId(1L);
        comentario.setUsuario(usuario);
        comentario.setLivro(livro);
        comentario.setMensagem("Comentário de teste");
        comentario.setSpoiler(false);
        comentario.setDataComentario(LocalDateTime.now());

        request = new ComentarioRequest();
        request.setUsuarioId(1L);
        request.setLivroId(1L);
        request.setMensagem("Comentário de teste");
        request.setSpoiler(false);
    }

    // registrarComentario
    @Test
    void registrarComentario_DeveSalvarERetornarResponse() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(comentarioRepository.save(any())).thenReturn(comentario);

        ComentarioResponse response = comentarioService.registrarComentario(request);

        assertNotNull(response);
        assertEquals("Comentário de teste", response.getMensagem());
        verify(comentarioRepository).save(any());
    }

    // registrarComentario - usuário inexistente
    @Test
    void registrarComentario_ComUsuarioInexistente_DeveLancarExcecao() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class,
            () -> comentarioService.registrarComentario(request));
    }

    // listarComentariosPorLivro - com spoilers permitidos
    @Test
    void listarComentariosPorLivro_ComSpoilersPermitidos_DeveRetornarTodos() {
        when(livroRepository.existsById(1L)).thenReturn(true);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(comentarioRepository.findByLivroId(1L)).thenReturn(List.of(comentario));

        List<ComentarioResponse> responses = comentarioService.listarComentariosPorLivro(1L, 1L);

        assertEquals(1, responses.size());
        verify(comentarioRepository).findByLivroId(1L);
    }

    // listarComentariosPorLivro - com spoilers ocultos
    @Test
    void listarComentariosPorLivro_ComSpoilersOcultos_DeveFiltrarSpoilers() {
        usuario.setVerSpoilers(false);
        when(livroRepository.existsById(1L)).thenReturn(true);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(comentarioRepository.findByLivroIdAndSpoilerFalse(1L)).thenReturn(List.of(comentario));

        List<ComentarioResponse> responses = comentarioService.listarComentariosPorLivro(1L, 1L);

        assertEquals(1, responses.size());
        verify(comentarioRepository).findByLivroIdAndSpoilerFalse(1L);
    }

    // listarComentariosPorLivro - livro inexistente
    @Test
    void listarComentariosPorLivro_ComLivroInexistente_DeveLancarExcecao() {
        when(livroRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntidadeNaoEncontradaException.class,
            () -> comentarioService.listarComentariosPorLivro(1L, null));
    }

    // listarTodosComentarios
    @Test
    void listarTodosComentarios_DeveRetornarListaCompleta() {
        when(comentarioRepository.findAll()).thenReturn(List.of(comentario));

        List<ComentarioResponse> responses = comentarioService.listarTodosComentarios();

        assertEquals(1, responses.size());
        verify(comentarioRepository).findAll();
    }

    // atualizarComentario - com permissão
    @Test
    void atualizarComentario_ComPermissao_DeveAtualizar() {
        when(comentarioRepository.findById(1L)).thenReturn(Optional.of(comentario));
        when(comentarioRepository.save(any())).thenReturn(comentario);

        ComentarioResponse response = comentarioService.atualizarComentario(1L, request);

        assertEquals("Comentário de teste", response.getMensagem());
        verify(comentarioRepository).save(any());
    }

    // atualizarComentario - sem permissão
    @Test
    void atualizarComentario_SemPermissao_DeveLancarExcecao() {
        Usuario outroUsuario = new Usuario();
        outroUsuario.setId(2L);
        comentario.setUsuario(outroUsuario);

        when(comentarioRepository.findById(1L)).thenReturn(Optional.of(comentario));

        assertThrows(AcessoNegadoException.class,
            () -> comentarioService.atualizarComentario(1L, request));
    }

    // removerComentario - com permissão
    @Test
    void removerComentario_ComPermissao_DeveRemover() {
        when(comentarioRepository.findById(1L)).thenReturn(Optional.of(comentario));

        comentarioService.removerComentario(1L, 1L);

        verify(comentarioRepository).deleteById(1L);
    }

    // removerComentario - sem permissão
    @Test
    void removerComentario_SemPermissao_DeveLancarExcecao() {
        Usuario outroUsuario = new Usuario();
        outroUsuario.setId(2L);
        comentario.setUsuario(outroUsuario);

        when(comentarioRepository.findById(1L)).thenReturn(Optional.of(comentario));

        assertThrows(AcessoNegadoException.class,
            () -> comentarioService.removerComentario(1L, 1L));
    }
}
