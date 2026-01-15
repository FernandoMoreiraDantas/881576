package com.seplag.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seplag.model.Artista;
import com.seplag.repository.ArtistaRepository;

@RestController
@RequestMapping("/api/artistas")
public class ArtistaController {

    @Autowired
    private ArtistaRepository repository;

    @GetMapping
    public List<Artista> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Artista criar(@RequestBody Artista artista) {
        return repository.save(artista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artista> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Artista> atualizar(@PathVariable Long id, @RequestBody Artista dadosAtualizados) {
        return repository.findById(id)
                .map(artista -> {
                    // Atualiza os campos necessários
                    artista.setNome(dadosAtualizados.getNome());
                    artista.setTipo(dadosAtualizados.getTipo());
                    
                    // Salva a alteração
                    Artista artistaSalvo = repository.save(artista);
                    return ResponseEntity.ok(artistaSalvo);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!repository.existsById(id)) return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}