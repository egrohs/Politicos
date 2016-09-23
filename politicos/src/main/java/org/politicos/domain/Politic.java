package org.politicos.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
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

    @NotNull
    @Field("name")
    private String name;

    @Field("codenames")
    private String codenames;

    @Field("state")
    private String state;

    @Field("city")
    private String city;

    @Field("born")
    private LocalDate born;

    @Field("email")
    private String email;

    @Field("photo")
    private String photo;

    @Field("parties")
    private String parties;

    @Field("positions")
    private String positions;

    @Field("urls")
    private String urls;

    @Field("legislatures")
    private String legislatures;

    @Field("history")
    private String history;

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

    public String getName() {
        return name;
    }

    public Politic name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodenames() {
        return codenames;
    }

    public Politic codenames(String codenames) {
        this.codenames = codenames;
        return this;
    }

    public void setCodenames(String codenames) {
        this.codenames = codenames;
    }

    public String getState() {
        return state;
    }

    public Politic state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public Politic city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getBorn() {
        return born;
    }

    public Politic born(LocalDate born) {
        this.born = born;
        return this;
    }

    public void setBorn(LocalDate born) {
        this.born = born;
    }

    public String getEmail() {
        return email;
    }

    public Politic email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public Politic photo(String photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getParties() {
        return parties;
    }

    public Politic parties(String parties) {
        this.parties = parties;
        return this;
    }

    public void setParties(String parties) {
        this.parties = parties;
    }

    public String getPositions() {
        return positions;
    }

    public Politic positions(String positions) {
        this.positions = positions;
        return this;
    }

    public void setPositions(String positions) {
        this.positions = positions;
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

    public String getLegislatures() {
        return legislatures;
    }

    public Politic legislatures(String legislatures) {
        this.legislatures = legislatures;
        return this;
    }

    public void setLegislatures(String legislatures) {
        this.legislatures = legislatures;
    }

    public String getHistory() {
        return history;
    }

    public Politic history(String history) {
        this.history = history;
        return this;
    }

    public void setHistory(String history) {
        this.history = history;
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
            ", name='" + name + "'" +
            ", codenames='" + codenames + "'" +
            ", state='" + state + "'" +
            ", city='" + city + "'" +
            ", born='" + born + "'" +
            ", email='" + email + "'" +
            ", photo='" + photo + "'" +
            ", parties='" + parties + "'" +
            ", positions='" + positions + "'" +
            ", urls='" + urls + "'" +
            ", legislatures='" + legislatures + "'" +
            ", history='" + history + "'" +
            ", created='" + created + "'" +
            ", updated='" + updated + "'" +
            '}';
    }
}
