package com.example.gestas_tarefas.fixture;

import com.example.gestas_tarefas.model.StatusTarefa;
import com.example.gestas_tarefas.model.Tarefa;

import java.util.Arrays;
import java.util.List;

public class TarefaServiceFixture {

    public static Tarefa criarTarefa() {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setTitulo("Título de teste");
        tarefa.setDescricao("Descrição de teste");
        tarefa.setStatusTarefa(StatusTarefa.PENDENTE);
        return tarefa;
    }

    public static Tarefa criarTarefaAtualizada() {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setTitulo("Título atualizado");
        tarefa.setDescricao("Descrição atualizada");
        tarefa.setStatusTarefa(StatusTarefa.CONCLUIDA);
        return tarefa;
    }

    public static List<Tarefa> criarListaTarefas() {
        return Arrays.asList(criarTarefa(), criarTarefa());
    }
}

