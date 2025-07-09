package com.exemplo.controller;

import com.exemplo.dto.UsuarioDTO;
import com.exemplo.model.Usuario;
import com.exemplo.repository.UsuarioRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Converter Entity para DTO
    private UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(usuario.getId(), usuario.getNome());
    }

    // Converter DTO para Entity
    private Usuario toEntity(UsuarioDTO dto) {
        Usuario u = new Usuario();
        u.setId(dto.getId());
        u.setNome(dto.getNome());
        return u;
    }

    // GET /usuarios
    @GetMapping
    public List<UsuarioDTO> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // GET /usuarios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> ResponseEntity.ok(toDTO(usuario)))
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /usuarios
    @PostMapping
    public UsuarioDTO criar(@RequestBody UsuarioDTO dto) {
        Usuario usuario = toEntity(dto);
        usuario.setId(null); // para garantir que é novo
        usuario = usuarioRepository.save(usuario);
        return toDTO(usuario);
    }

    // PUT /usuarios/{id}
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        return usuarioRepository.findById(id)
                .map(usuarioExistente -> {
                    usuarioExistente.setNome(dto.getNome());
                    usuarioRepository.save(usuarioExistente);
                    return ResponseEntity.ok(toDTO(usuarioExistente));
                }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /usuarios/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

