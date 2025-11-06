package com.example.gestas_tarefas.service;
import com.example.gestas_tarefas.exceptions.RecursoNaoEncontradoException;
import com.example.gestas_tarefas.fixture.TarefaServiceFixture;
import com.example.gestas_tarefas.model.Tarefa;
import com.example.gestas_tarefas.repository.TarefaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @InjectMocks
    private TarefaService tarefaService;


    @Test
    @DisplayName("Salvar tarefa")
    void salvarTarefaComSucesso() {
        Tarefa tarefa = new TarefaServiceFixture().criarTarefa();
        when(tarefaRepository.save(tarefa)).thenReturn(tarefa);

        Tarefa resultado = tarefaService.salvarTarefa(tarefa);
        assertNotNull(resultado);
        assertEquals(tarefa.getTitulo(), resultado.getTitulo());
        verify(tarefaRepository).save(tarefa);
    }

    @Test
    @DisplayName("Retornar a lista de tarefas")
    void listarTarefaComSucesso() {
        List<Tarefa> tarefa = Arrays.asList(TarefaServiceFixture.criarTarefa());
        when(tarefaRepository.findAll()).thenReturn(tarefa);

        List<Tarefa> resultado = tarefaService.findAll();

        assertFalse(resultado.isEmpty());
        verify(tarefaRepository).findAll();
    }

    @Test
    @DisplayName("Atualizar a tarefa Com Sucesso")
    void atualizarTarefa() {

        Long id = 1L;
        final var tarefaSalvaExistente = TarefaServiceFixture.criarTarefa();
        final var tarefaAtualizada = TarefaServiceFixture.criarTarefaAtualizada();
        when(tarefaRepository.findById(id)).thenReturn(Optional.of(tarefaSalvaExistente));
        when(tarefaRepository.save(tarefaSalvaExistente)).thenReturn(tarefaAtualizada);

        var resultado = tarefaService.atualizarTarefa(1L, tarefaAtualizada);

        assertNotNull(resultado);
        assertEquals(tarefaAtualizada.getTitulo(),  resultado.getTitulo());
        assertEquals(tarefaAtualizada.getDescricao(),  resultado.getDescricao());
        assertEquals(tarefaAtualizada.getStatusTarefa(), resultado.getStatusTarefa());

        verify(tarefaRepository, times(1)).findById(id);
        verify(tarefaRepository).save(tarefaSalvaExistente);

    }


    @Test
    @DisplayName("Buscar tarefa por ID com Sucesso")
    void buscarTarefaPorIdComSucesso() {
        Long id = 1L;
        final var tarefaExistente = TarefaServiceFixture.criarTarefa();
        when (tarefaRepository.findById(id)).thenReturn(Optional.of(tarefaExistente));

        Tarefa tarefa = tarefaService.findById(id);

        assertNotNull(tarefa);
        assertEquals(tarefaExistente.getId(), tarefa.getId());
        assertEquals(tarefaExistente.getTitulo(), tarefa.getTitulo());

        verify(tarefaRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Buscar tarefa com ID Sem Sucesso")
    void buscarTarefaPorIdSemSucesso(){
        Long id = 1L;
        when (tarefaRepository.findById(id)).thenReturn(Optional.empty());

        var excecao = assertThrows(RecursoNaoEncontradoException.class, () -> tarefaService.findById(id));

        assertEquals("Produto com ID " + id +" não encontrado", excecao.getMessage());
        verify(tarefaRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Deletar tarefa com Sucesso")
    void deletarTarefa(){
        Long id = 1L;
        when(tarefaRepository.existsById(id)).thenReturn(true);

        tarefaService.deletarTarefa(id);

        verify(tarefaRepository, times(1)).existsById(id);
        verify(tarefaRepository).deleteById(id);
    }

    @Test
    @DisplayName("Deletar Tarefa Sem Sucesso")
    void deletarTarefaSemSucesso(){
        Long id = 1L;
        when(tarefaRepository.existsById(id)).thenReturn(false);

        var excecao = assertThrows(RecursoNaoEncontradoException.class,
                () -> tarefaService.deletarTarefa(id));

        assertEquals("Produto com ID "+id+" não encontrado", excecao.getMessage());
        verify(tarefaRepository, times(1)).existsById(id);
        verify(tarefaRepository, times (0)).deleteById(id);
    }
}