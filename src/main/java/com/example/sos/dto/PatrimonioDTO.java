package com.example.sos.dto;

import java.io.Serializable;

public class PatrimonioDTO implements Serializable {
    private static final long serialVersionUID = -7936467944742873125L;
    private Long id;
    private String nome;
    private String descricao;
    private String nuTombo;
    private MarcaDTO marca;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNuTombo() {
        return nuTombo;
    }

    public void setNuTombo(String nuTombo) {
        this.nuTombo = nuTombo;
    }

    public MarcaDTO getMarca() {
        return marca;
    }

    public void setMarca(MarcaDTO marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        return "PatrimonioDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", nuTombo='" + nuTombo + '\'' +
                ", marca=" + marca +
                '}';
    }
}
