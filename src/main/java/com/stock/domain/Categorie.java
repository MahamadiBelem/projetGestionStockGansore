package com.stock.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Categorie.
 */
@Entity
@Table(name = "categorie")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Categorie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code_categorie", nullable = false)
    private String codeCategorie;

    @Column(name = "libelle_categorie")
    private String libelleCategorie;

    @OneToMany(mappedBy = "categorie")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Article> articles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeCategorie() {
        return codeCategorie;
    }

    public Categorie codeCategorie(String codeCategorie) {
        this.codeCategorie = codeCategorie;
        return this;
    }

    public void setCodeCategorie(String codeCategorie) {
        this.codeCategorie = codeCategorie;
    }

    public String getLibelleCategorie() {
        return libelleCategorie;
    }

    public Categorie libelleCategorie(String libelleCategorie) {
        this.libelleCategorie = libelleCategorie;
        return this;
    }

    public void setLibelleCategorie(String libelleCategorie) {
        this.libelleCategorie = libelleCategorie;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public Categorie articles(Set<Article> articles) {
        this.articles = articles;
        return this;
    }

    public Categorie addArticle(Article article) {
        this.articles.add(article);
        article.setCategorie(this);
        return this;
    }

    public Categorie removeArticle(Article article) {
        this.articles.remove(article);
        article.setCategorie(null);
        return this;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Categorie)) {
            return false;
        }
        return id != null && id.equals(((Categorie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Categorie{" +
            "id=" + getId() +
            ", codeCategorie='" + getCodeCategorie() + "'" +
            ", libelleCategorie='" + getLibelleCategorie() + "'" +
            "}";
    }
}
