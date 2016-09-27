package org.politicos.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Politic.
 */

@Document(collection = "politic")
public class Politic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("camarapk")
    private Long camarapk;

    @Field("nome")
    private String nome;

    @Field("codinomes")
    private String codinomes;

    @Field("foto")
    private String foto;

    @Field("urls")
    private String urls;

    @Field("created")
    private ZonedDateTime created;

    @Field("updated")
    private ZonedDateTime updated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCamarapk() {
        return camarapk;
    }

    public Politic camarapk(Long camarapk) {
        this.camarapk = camarapk;
        return this;
    }

    public void setCamarapk(Long camarapk) {
        this.camarapk = camarapk;
    }

    public String getNome() {
        return nome;
    }

    public Politic nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodinomes() {
        return codinomes;
    }

    public Politic codinomes(String codinomes) {
        this.codinomes = codinomes;
        return this;
    }

    public void setCodinomes(String codinomes) {
        this.codinomes = codinomes;
    }

    public String getFoto() {
        return foto;
    }

    public Politic foto(String foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getUrls() {
        return urls;
    }

    public Politic urls(String urls) {
        this.urls = urls;
        return this;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public Politic created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public Politic updated(ZonedDateTime updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Politic politic = (Politic) o;
        if(politic.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, politic.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Politic{" +
            "id=" + id +
            ", camarapk='" + camarapk + "'" +
            ", nome='" + nome + "'" +
            ", codinomes='" + codinomes + "'" +
            ", foto='" + foto + "'" +
            ", urls='" + urls + "'" +
            ", created='" + created + "'" +
            ", updated='" + updated + "'" +
            '}';
    }
}
