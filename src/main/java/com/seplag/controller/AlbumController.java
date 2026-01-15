package com.seplag.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seplag.dto.AlbumTipoDTO;
import com.seplag.model.Album;
import com.seplag.repository.AlbumRepository;

@RestController
@RequestMapping("/api/albuns")
public class AlbumController {

    @Autowired
    private AlbumRepository repository;

    // LISTAR COM PAGINAÇÃO
    @GetMapping
    public ResponseEntity<Page<Album>> listar(
            @PageableDefault(size = 10, sort = "titulo", direction = Direction.ASC) Pageable paginacao) {
        return ResponseEntity.ok(repository.findAll(paginacao));
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Album> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CRIAR
    @PostMapping
    @Transactional
    public ResponseEntity<Album> criar(@RequestBody Album album) {
        Album salvo = repository.save(album);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // ATUALIZAR (PUT)
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Album> atualizar(@PathVariable Long id, @RequestBody Album dados) {
        return repository.findById(id)
                .map(album -> {
                    album.setTitulo(dados.getTitulo());
                    album.setAno(dados.getAno());
                    return ResponseEntity.ok(repository.save(album));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/por-tipo-artista")
    public ResponseEntity<Page<AlbumTipoDTO>> listarPorTipo(
            @RequestParam String tipo, 
            @PageableDefault(size = 10) Pageable paginacao) {
        
        // Converte para caixa alta para evitar erros de busca
        Page<AlbumTipoDTO> resultado = repository.buscarAlbunsPorTipoArtista(tipo.toUpperCase(), paginacao);
        return ResponseEntity.ok(resultado);
    }
}
