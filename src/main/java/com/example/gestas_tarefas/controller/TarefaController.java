package com.example.gestas_tarefas.controller;

import com.example.gestas_tarefas.exceptions.RecursoNaoEncontradoException;
import com.example.gestas_tarefas.model.Tarefa;
import com.example.gestas_tarefas.service.TarefaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {

        this.tarefaService = tarefaService;
    }

    @GetMapping
    public List<Tarefa> listarTarefa() {

        return tarefaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarTarefa(@PathVariable Long id) {
            Tarefa tarefa = tarefaService.findById(id);
            return ResponseEntity.ok(tarefa);
    }

    @PostMapping
    public Tarefa criarTarefa(@RequestBody Tarefa tarefa) {

        return tarefaService.salvarTarefa(tarefa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefa) {
        try {
            return ResponseEntity.ok(tarefaService.atualizarTarefa(id,tarefa));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        tarefaService.deletarTarefa(id);
        return ResponseEntity.noContent().build();
    }
}

