package com.seplag.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.seplag.config.ApiPaths;
import com.seplag.dto.AlbumTipoDTO;
import com.seplag.model.Album;
import com.seplag.service.AlbumService;

@RestController
@RequestMapping(ApiPaths.V1 + "/albuns")

public class AlbumController {

	@Autowired
	private AlbumService albumService;

	@PostMapping
	public ResponseEntity<Album> criar(@RequestBody Album album) {
		return ResponseEntity.status(HttpStatus.CREATED).body(albumService.criar(album));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Album> atualizar(@PathVariable Long id, @RequestBody Album dados) {
		return ResponseEntity.ok(albumService.atualizar(id, dados));
	}

	@GetMapping
	public ResponseEntity<Page<Album>> listar(Pageable paginacao) {
		return ResponseEntity.ok(albumService.listar(paginacao));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Album> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(albumService.buscarPorId(id));
	}

	@GetMapping("/por-tipo-artista")
	public ResponseEntity<Page<AlbumTipoDTO>> listarPorTipo(@RequestParam String tipo, Pageable paginacao) {

		return ResponseEntity.ok(albumService.listarPorTipo(tipo, paginacao));
	}

	@PostMapping(value = "/com-imagem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Album> criarAlbumComImagem(@RequestParam String titulo, @RequestParam Integer ano,
			@RequestParam List<Long> artistaIds, @RequestParam MultipartFile imagem) throws Exception {

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(albumService.criarComImagem(titulo, ano, artistaIds, imagem));
	}
	
	@GetMapping("/{id}/imagem")
	public ResponseEntity<String> obterImagem(@PathVariable Long id) throws Exception {
	    return ResponseEntity.ok(albumService.obterUrlImagem(id));
	}


}
