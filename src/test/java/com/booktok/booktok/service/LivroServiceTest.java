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

import com.booktok.booktok.dto.request.LivroRequest;
import com.booktok.booktok.dto.response.LivroResponse;
import com.booktok.booktok.exception.EntidadeNaoEncontradaException;
import com.booktok.booktok.exception.IsbnJaCadastradoException;
import com.booktok.booktok.model.Livro;
import com.booktok.booktok.repository.LivroRepository;

@ExtendWith(MockitoExtension.class)
public class LivroServiceTest {

	@InjectMocks
    private LivroService livroService;

    @Mock
    private LivroRepository repository;

    private Livro livro;
    private LivroRequest request;

    @BeforeEach
    void setUp() {
        livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Dom Casmurro");
        livro.setAutor("Machado de Assis");
        livro.setResumo("Um clássico da literatura brasileira.");
        livro.setAnoPublicacao(1899);
        livro.setIsbn("1234567890");

        request = new LivroRequest();
        request.setTitulo("Dom Casmurro");
        request.setAutor("Machado de Assis");
        request.setResumo("Um clássico da literatura brasileira.");
        request.setAnoPublicacao(1899);
        request.setIsbn("1234567890");
    }

    // Salvar livro com ISBN novo
    @Test
    void salvarLivro_ComIsbnNovo_DeveSalvarERetornarResponse() {
        when(repository.findByIsbn(request.getIsbn())).thenReturn(null);
        when(repository.save(any(Livro.class))).thenReturn(livro);

        LivroResponse response = livroService.salvarLivro(request);

        assertNotNull(response);
        assertEquals("Dom Casmurro", response.getTitulo());
        verify(repository).findByIsbn(request.getIsbn());
        verify(repository).save(any(Livro.class));
    }

    // Salvar livro com ISBN já cadastrado
    @Test
    void salvarLivro_ComIsbnExistente_DeveLancarExcecao() {
        when(repository.findByIsbn(request.getIsbn())).thenReturn(livro);

        assertThrows(IsbnJaCadastradoException.class, () -> livroService.salvarLivro(request));
        verify(repository).findByIsbn(request.getIsbn());
        verify(repository, never()).save(any());
    }

    // Buscar livro por ID existente
    @Test
    void buscarLivroPorId_ComIdExistente_DeveRetornarResponse() {
        when(repository.findById(1L)).thenReturn(Optional.of(livro));

        LivroResponse response = livroService.buscarLivroPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(repository).findById(1L);
    }
    
    // Buscar livro por ID inexistente
    @Test
    void buscarLivroPorId_ComIdInexistente_DeveLancarExcecao() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        EntidadeNaoEncontradaException exception = assertThrows(
            EntidadeNaoEncontradaException.class,
            () -> livroService.buscarLivroPorId(1L)
        );

        assertEquals("Livro com ID 1 nao encontrado.", exception.getMessage());
        verify(repository).findById(1L);
    }

    // Listar todos os livros
    @Test
    void listarLivros_DeveRetornarListaDeResponses() {
        when(repository.findAll()).thenReturn(List.of(livro));

        List<LivroResponse> responses = livroService.listarLivros();

        assertEquals(1, responses.size());
        assertEquals("Dom Casmurro", responses.get(0).getTitulo());
        verify(repository).findAll();
    }

    // Atualizar livro existente
    @Test
    void atualizarLivro_ComIdExistente_DeveAtualizarERetornarResponse() {
        when(repository.findById(1L)).thenReturn(Optional.of(livro));
        when(repository.save(any(Livro.class))).thenReturn(livro);

        LivroResponse response = livroService.atualizarLivro(1L, request);

        assertNotNull(response);
        assertEquals("Dom Casmurro", response.getTitulo());
        verify(repository).findById(1L);
        verify(repository).save(livro);
    }

    // Atualizar livro inexistente
    @Test
    void atualizarLivro_ComIdInexistente_DeveLancarExcecao() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class,
            () -> livroService.atualizarLivro(1L, request));

        verify(repository).findById(1L);
        verify(repository, never()).save(any());
    }

    // Deletar livro existente
    @Test
    void deletarLivro_ComIdExistente_DeveDeletar() {
        when(repository.existsById(1L)).thenReturn(true);

        livroService.deletarLivro(1L);

        verify(repository).existsById(1L);
        verify(repository).deleteById(1L);
    }

    // Deletar livro inexistente
    @Test
    void deletarLivro_ComIdInexistente_DeveLancarExcecao() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(EntidadeNaoEncontradaException.class,
            () -> livroService.deletarLivro(1L));

        verify(repository).existsById(1L);
        verify(repository, never()).deleteById(any());
    }
}
