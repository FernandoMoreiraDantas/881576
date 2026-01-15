package com.seplag.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.seplag.dto.AlbumTipoDTO;
import com.seplag.model.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
	    @Query("SELECT new com.seplag.dto.AlbumTipoDTO(a.titulo, art.nome, art.tipo) " +
	           "FROM Album a " +
	           "JOIN a.artistas art " + 
	           "WHERE art.tipo = :tipo")
	    Page<AlbumTipoDTO> buscarAlbunsPorTipoArtista(@Param("tipo") String tipo, Pageable pageable);
}
