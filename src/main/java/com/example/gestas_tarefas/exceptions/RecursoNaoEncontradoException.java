package com.example.gestas_tarefas.exceptions;

import com.example.gestas_tarefas.service.TarefaService;

public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
