package com.seplag.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seplag.model.Artista;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long> {
	// Busca artistas que contenham o nome informado, suportando paginação e ordenação
    Page<Artista> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
	
}
