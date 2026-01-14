package com.seplag.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "album_imagem")
public class AlbumImagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "album_id")
    @JsonIgnore // Evita loop infinito ao listar álbuns
    private Album album;

    @Column(name = "object_key", nullable = false)
    private String objectKey;

    public AlbumImagem() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Album getAlbum() { return album; }
    public void setAlbum(Album album) { this.album = album; }
    public String getObjectKey() { return objectKey; }
    public void setObjectKey(String objectKey) { this.objectKey = objectKey; }
}