package com.seplag.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "regional")
public class Regional {
    @Id
    private Integer id; // Definido como PRIMARY KEY manual no seu SQL

    @Column(nullable = false)
    private String nome;

    private Boolean ativo = true;

    public Regional() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}
