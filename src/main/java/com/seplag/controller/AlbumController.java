package com.seplag.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.seplag.dto.AlbumTipoDTO;
import com.seplag.model.Album;
import com.seplag.model.AlbumImagem;
import com.seplag.model.Artista;
import com.seplag.repository.AlbumImagemRepository;
import com.seplag.repository.AlbumRepository;
import com.seplag.repository.ArtistaRepository;
import com.seplag.service.MinioService;

@RestController
@RequestMapping("/api/albuns")
public class AlbumController {

    @Autowired
    private AlbumRepository repository;
    
    @Autowired
    private ArtistaRepository artistaRepository;
    
    @Autowired
    private AlbumImagemRepository albumImagemRepository;
    
    @Autowired
    private MinioService minioService;

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
    
    @PostMapping(value = "/com-imagem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> criarAlbumComImagem(
            @RequestParam String titulo,
            @RequestParam Integer ano,
            @RequestParam List<Long> artistaIds,
            @RequestParam MultipartFile imagem) throws Exception {

        List<Artista> artistas = artistaRepository.findAllById(artistaIds);

        Album album = new Album();
        album.setTitulo(titulo);
        album.setAno(ano);
        Album albumSalvo = repository.save(album);

        for (Artista artista : artistas) {
            artista.getAlbuns().add(albumSalvo);
        }

        String objectKey = minioService.upload(imagem);

        AlbumImagem img = new AlbumImagem();
        img.setAlbum(albumSalvo);
        img.setObjectKey(objectKey);
        albumImagemRepository.save(img);

        return ResponseEntity.status(HttpStatus.CREATED).body(albumSalvo);
    }



}
