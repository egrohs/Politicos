package org.politicos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Politico.
 */
@Entity
@Table(name = "politico")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "politico")
public class Politico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "camara_pk")
    private String camaraPk;

    @Column(name = "senado_id")
    private String senadoId;

    @Column(name = "nome")
    private String nome;

    @Column(name = "codinomes")
    private String codinomes;

    @Column(name = "uf")
    private String uf;

    @Column(name = "partido_atual")
    private String partidoAtual;

    @Column(name = "outros_partidos")
    private String outrosPartidos;

    @Column(name = "profissoes")
    private String profissoes;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "legislaturas")
    private String legislaturas;

    @Column(name = "foto")
    private String foto;

    @Column(name = "urls")
    private String urls;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCamaraPk() {
        return camaraPk;
    }

    public Politico camaraPk(String camaraPk) {
        this.camaraPk = camaraPk;
        return this;
    }

    public void setCamaraPk(String camaraPk) {
        this.camaraPk = camaraPk;
    }

    public String getSenadoId() {
        return senadoId;
    }

    public Politico senadoId(String senadoId) {
        this.senadoId = senadoId;
        return this;
    }

    public void setSenadoId(String senadoId) {
        this.senadoId = senadoId;
    }

    public String getNome() {
        return nome;
    }

    public Politico nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodinomes() {
        return codinomes;
    }

    public Politico codinomes(String codinomes) {
        this.codinomes = codinomes;
        return this;
    }

    public void setCodinomes(String codinomes) {
        this.codinomes = codinomes;
    }

    public String getUf() {
        return uf;
    }

    public Politico uf(String uf) {
        this.uf = uf;
        return this;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getPartidoAtual() {
        return partidoAtual;
    }

    public Politico partidoAtual(String partidoAtual) {
        this.partidoAtual = partidoAtual;
        return this;
    }

    public void setPartidoAtual(String partidoAtual) {
        this.partidoAtual = partidoAtual;
    }

    public String getOutrosPartidos() {
        return outrosPartidos;
    }

    public Politico outrosPartidos(String outrosPartidos) {
        this.outrosPartidos = outrosPartidos;
        return this;
    }

    public void setOutrosPartidos(String outrosPartidos) {
        this.outrosPartidos = outrosPartidos;
    }

    public String getProfissoes() {
        return profissoes;
    }

    public Politico profissoes(String profissoes) {
        this.profissoes = profissoes;
        return this;
    }

    public void setProfissoes(String profissoes) {
        this.profissoes = profissoes;
    }

    public String getCargo() {
        return cargo;
    }

    public Politico cargo(String cargo) {
        this.cargo = cargo;
        return this;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getLegislaturas() {
        return legislaturas;
    }

    public Politico legislaturas(String legislaturas) {
        this.legislaturas = legislaturas;
        return this;
    }

    public void setLegislaturas(String legislaturas) {
        this.legislaturas = legislaturas;
    }

    public String getFoto() {
        return foto;
    }

    public Politico foto(String foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getUrls() {
        return urls;
    }

    public Politico urls(String urls) {
        this.urls = urls;
        return this;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Politico politico = (Politico) o;
        if(politico.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, politico.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Politico{" +
            "id=" + id +
            ", camaraPk='" + camaraPk + "'" +
            ", senadoId='" + senadoId + "'" +
            ", nome='" + nome + "'" +
            ", codinomes='" + codinomes + "'" +
            ", uf='" + uf + "'" +
            ", partidoAtual='" + partidoAtual + "'" +
            ", outrosPartidos='" + outrosPartidos + "'" +
            ", profissoes='" + profissoes + "'" +
            ", cargo='" + cargo + "'" +
            ", legislaturas='" + legislaturas + "'" +
            ", foto='" + foto + "'" +
            ", urls='" + urls + "'" +
            '}';
    }
}
