package com.booktok.booktok.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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

import com.booktok.booktok.dto.request.ProgressoLeituraRequest;
import com.booktok.booktok.dto.response.ProgressoLeituraResponse;
import com.booktok.booktok.exception.AcessoNegadoException;
import com.booktok.booktok.exception.EntidadeNaoEncontradaException;
import com.booktok.booktok.model.Livro;
import com.booktok.booktok.model.ProgressoLeitura;
import com.booktok.booktok.model.Usuario;
import com.booktok.booktok.repository.LivroRepository;
import com.booktok.booktok.repository.ProgressoLeituraRepository;
import com.booktok.booktok.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class ProgressoLeituraServiceTest {
	@Mock
    private ProgressoLeituraRepository progressoRepository;

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ProgressoLeituraService progressoService;

    private Livro livro;
    private Usuario usuario;
    private ProgressoLeitura progresso;
    private ProgressoLeituraRequest request;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Usuário Teste");

        livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Livro Teste");

        progresso = new ProgressoLeitura();
        progresso.setId(1L);
        progresso.setLivro(livro);
        progresso.setUsuario(usuario);
        progresso.setPaginaAtual(50);
        progresso.setDataRegistro(LocalDateTime.now());

        request = new ProgressoLeituraRequest();
        request.setUsuarioId(1L);
        request.setPaginaAtual(100);
    }

    // TESTE PARA listarProgressosPorLivro
    @Test
    void listarProgressosPorLivro_ComLivroExistente_DeveRetornarLista() {
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(progressoRepository.findByLivroId(livro)).thenReturn(List.of(progresso));

        List<ProgressoLeituraResponse> result = progressoService.listarProgressosPorLivro(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(livroRepository).findById(1L);
        verify(progressoRepository).findByLivroId(livro);
    }
    // TESTE PARA atualizarProgresso 
    @Test
    void atualizarProgresso_ComProgressoExistente_DeveAtualizarERetornar() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);
        when(progressoRepository.findById(1L)).thenReturn(Optional.of(progresso));
        when(progressoRepository.save(any(ProgressoLeitura.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProgressoLeituraResponse result = progressoService.atualizarProgresso(1L, request);

        assertNotNull(result);
        verify(usuarioRepository).existsById(1L);
        verify(progressoRepository).findById(1L);
        verify(progressoRepository).save(progresso);
        assertEquals(100, progresso.getPaginaAtual());
    }

    // TESTE PARA VALIDAÇÃO DE USUÁRIO DONO
    @Test
    void atualizarProgresso_ComUsuarioNaoDono_DeveLancarExcecaoAcessoNegado() {
        request.setUsuarioId(2L);
        when(usuarioRepository.existsById(2L)).thenReturn(true);
        when(progressoRepository.findById(1L)).thenReturn(Optional.of(progresso));

        AcessoNegadoException exception = assertThrows(
            AcessoNegadoException.class,
            () -> progressoService.atualizarProgresso(1L, request)
        );

        assertEquals("Você só pode atualizar seus próprios progressos", exception.getMessage());
        verify(usuarioRepository).existsById(2L);
        verify(progressoRepository).findById(1L);
        verify(progressoRepository, never()).save(any());
    }

    // TESTE PARA LIVRO SEM PROGRESSOS 
    @Test
    void listarProgressosPorLivro_ComLivroSemProgressos_DeveRetornarListaVazia() {
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(progressoRepository.findByLivroId(livro)).thenReturn(List.of()); // ✅ Lista vazia

        List<ProgressoLeituraResponse> result = progressoService.listarProgressosPorLivro(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(livroRepository).findById(1L);
        verify(progressoRepository).findByLivroId(livro);
    }
    //TESTE MENSAGEM EXCEPTION
    @Test
    void atualizarProgresso_ComUsuarioInexistente_DeveLancarExcecao() {
        request.setUsuarioId(999L);
        when(usuarioRepository.existsById(999L)).thenReturn(false);

        Exception exception = assertThrows(
            EntidadeNaoEncontradaException.class,
            () -> progressoService.atualizarProgresso(1L, request)
        );

        assertEquals("Usuário com ID 999 não encontrado.", exception.getMessage());
        verify(usuarioRepository).existsById(999L);
        verify(progressoRepository, never()).findById(any());
    }

    // VERIFICAR SE O REPOSITORY ESTA SENDO CHAMADO CORRETAMENTE
    @Test
    void listarProgressosPorLivro_DeveChamarRepositoryComObjetoLivroCorreto() {
        Livro livroEspecifico = new Livro();
        livroEspecifico.setId(2L);
        livroEspecifico.setTitulo("Livro Específico");
        
        when(livroRepository.findById(2L)).thenReturn(Optional.of(livroEspecifico));
        when(progressoRepository.findByLivroId(livroEspecifico)).thenReturn(List.of(progresso));

        progressoService.listarProgressosPorLivro(2L);

        verify(progressoRepository).findByLivroId(livroEspecifico);
    }
}
