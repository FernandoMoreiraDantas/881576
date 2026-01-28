package com.seplag.dto;

import java.util.List;

public class AlbumCreateRequestDTO {

    private String titulo;
    private Integer ano;
    private List<Long> artistaIds;
    
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public List<Long> getArtistaIds() {
		return artistaIds;
	}
	public void setArtistaIds(List<Long> artistaIds) {
		this.artistaIds = artistaIds;
	}
    
}

