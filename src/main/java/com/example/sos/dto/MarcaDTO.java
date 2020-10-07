package com.example.sos.dto;

import java.io.Serializable;

public class MarcaDTO implements Serializable {
    private static final long serialVersionUID = 28627071661566643L;

    private Long id;
    private String nome;
    private String path;

    public MarcaDTO() {
    }

    public MarcaDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public MarcaDTO(Long id, String nome, String path) {
        this.id = id;
        this.nome = nome;
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "MarcaDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
