package com.seplag.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seplag.model.Artista;
import com.seplag.repository.ArtistaRepository;

@Service
public class ArtistaService {

	@Autowired
	private ArtistaRepository artistaRepository;

	public List<Artista> listarTodos() {
		return artistaRepository.findAll();
	}

	public Artista buscarPorId(Long id) {
		return artistaRepository.findById(id).orElseThrow(() -> new RuntimeException("Artista não encontrado: " + id));
	}

	@Transactional
	public Artista salvar(Artista artista) {
		return artistaRepository.save(artista);
	}

	@Transactional
	public Artista atualizar(Long id, Artista dadosAtualizados) {

		Artista artista = buscarPorId(id);

		artista.setNome(dadosAtualizados.getNome());
		artista.setTipo(dadosAtualizados.getTipo());

		return artistaRepository.save(artista);
	}

	@Transactional
	public void excluir(Long id) {
		Artista artista = buscarPorId(id);
		artistaRepository.delete(artista);
	}

	public List<Artista> buscarPorIds(List<Long> ids) {
		List<Artista> artistas = artistaRepository.findAllById(ids);

		if (artistas.size() != ids.size()) {
			throw new RuntimeException("Um ou mais artistas não foram encontrados.");
		}

		return artistas;
	}

	public Page<Artista> buscarPorNome(String nome, Pageable paginacao) {
		return artistaRepository.findByNomeContainingIgnoreCase(nome, paginacao);
	}

}
