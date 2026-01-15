package com.seplag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seplag.model.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    // Métodos de paginação já estão inclusos por padrão
}
