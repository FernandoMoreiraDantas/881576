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
import com.seplag.service.ArtistaService;

@RestController
@RequestMapping("/api/artistas")
public class ArtistaController {

	@Autowired
	private ArtistaService artistaService;

	@GetMapping
	public List<Artista> listar() {
		return artistaService.listarTodos();
	}

	@PostMapping
	public Artista criar(@RequestBody Artista artista) {
		return artistaService.salvar(artista);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Artista> buscar(@PathVariable Long id) {
		Artista artista = artistaService.buscarPorId(id);
		return ResponseEntity.ok(artista);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Artista> atualizar(@PathVariable Long id, @RequestBody Artista dadosAtualizados) {

		Artista artistaAtualizado = artistaService.atualizar(id, dadosAtualizados);
		return ResponseEntity.ok(artistaAtualizado);
	}

	@GetMapping("/busca")
	public ResponseEntity<Page<Artista>> buscarPorNome(@RequestParam String nome,
			@PageableDefault(size = 10, sort = "nome", direction = Direction.ASC) Pageable paginacao) {

		Page<Artista> artistas = artistaService.buscarPorNome(nome, paginacao);
		return ResponseEntity.ok(artistas);
	}
}