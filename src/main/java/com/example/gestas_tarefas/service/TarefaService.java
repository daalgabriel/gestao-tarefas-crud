package com.example.gestas_tarefas.service;


import com.example.gestas_tarefas.exceptions.RecursoNaoEncontradoException;
import com.example.gestas_tarefas.model.Tarefa;
import com.example.gestas_tarefas.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {
    private TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;

    }

    public List<Tarefa> findAll(){

        return tarefaRepository.findAll();
    }

    public Tarefa findById(Long id){

        return tarefaRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Produto com ID "+id+" não encontrado" ));
    }

    public Tarefa salvarTarefa(Tarefa tarefa){

        return tarefaRepository.save(tarefa);
    }

    public void deletarTarefa(Long id){
        if (!tarefaRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Produto com ID "+id+" não encontrado");
        }
        tarefaRepository.deleteById(id);
    }

    public Tarefa atualizarTarefa(Long id,  Tarefa tarefaAtualizada){
        return tarefaRepository.findById(id).map(tarefa -> {
            tarefa.setTitulo(tarefaAtualizada.getTitulo());
            tarefa.setDescricao(tarefaAtualizada.getDescricao());
            tarefa.setStatusTarefa(tarefaAtualizada.getStatusTarefa());
            return tarefaRepository.save(tarefa);
        }).orElseThrow(() -> new RecursoNaoEncontradoException("Tarefa com ID " + id + " não encontrada"));
    }
}
