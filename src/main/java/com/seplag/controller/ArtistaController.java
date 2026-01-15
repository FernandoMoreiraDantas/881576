package com.seplag.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/busca")
    public ResponseEntity<Page<Artista>> buscarPorNome(
            @RequestParam String nome,
            @PageableDefault(size = 10, sort = "nome", direction = Direction.ASC) Pageable paginacao) {
        
        Page<Artista> artistas = repository.findByNomeContainingIgnoreCase(nome, paginacao);
        return ResponseEntity.ok(artistas);
    }
}