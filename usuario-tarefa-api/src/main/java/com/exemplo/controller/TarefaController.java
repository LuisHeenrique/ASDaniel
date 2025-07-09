package com.exemplo.controller;

import com.exemplo.dto.TarefaDTO;
import com.exemplo.model.Tarefa;
import com.exemplo.model.Usuario;
import com.exemplo.repository.TarefaRepository;
import com.exemplo.repository.UsuarioRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaRepository tarefaRepository;
    private final UsuarioRepository usuarioRepository;

    public TarefaController(TarefaRepository tarefaRepository, UsuarioRepository usuarioRepository) {
        this.tarefaRepository = tarefaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Converter Entity para DTO
    private TarefaDTO toDTO(Tarefa tarefa) {
        return new TarefaDTO(
                tarefa.getId(),
                tarefa.getDescricao(),
                tarefa.getConcluida(),
                tarefa.getUsuario() != null ? tarefa.getUsuario().getId() : null
        );
    }

    // Converter DTO para Entity
    private Tarefa toEntity(TarefaDTO dto) {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(dto.getId());
        tarefa.setDescricao(dto.getDescricao());
        tarefa.setConcluida(dto.getConcluida());
        if (dto.getUsuarioId() != null) {
            Usuario usuario = new Usuario();
            usuario.setId(dto.getUsuarioId());
            tarefa.setUsuario(usuario);
        }
        return tarefa;
    }

    // GET /tarefas
    @GetMapping
    public List<TarefaDTO> listar() {
        return tarefaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // GET /tarefas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<TarefaDTO> buscarPorId(@PathVariable Long id) {
        return tarefaRepository.findById(id)
                .map(tarefa -> ResponseEntity.ok(toDTO(tarefa)))
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /tarefas
    @PostMapping
    public ResponseEntity<TarefaDTO> criar(@RequestBody TarefaDTO dto) {
        Tarefa tarefa = toEntity(dto);
        // Buscando usuário real para relacionamento válido
        if (tarefa.getUsuario() != null) {
            Long usuarioId = tarefa.getUsuario().getId();
            Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
            if (usuario == null) {
                return ResponseEntity.badRequest().build();
            }
            tarefa.setUsuario(usuario);
        }
        tarefa.setId(null);
        tarefa = tarefaRepository.save(tarefa);
        return ResponseEntity.ok(toDTO(tarefa));
    }

    // PUT /tarefas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<TarefaDTO> atualizar(@PathVariable Long id, @RequestBody TarefaDTO dto) {
        return tarefaRepository.findById(id)
                .map(tarefaExistente -> {
                    tarefaExistente.setDescricao(dto.getDescricao());
                    tarefaExistente.setConcluida(dto.getConcluida());
                    if (dto.getUsuarioId() != null) {
                        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId()).orElse(null);
                        tarefaExistente.setUsuario(usuario);
                    }
                    tarefaRepository.save(tarefaExistente);
                    return ResponseEntity.ok(toDTO(tarefaExistente));
                }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /tarefas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!tarefaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tarefaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
