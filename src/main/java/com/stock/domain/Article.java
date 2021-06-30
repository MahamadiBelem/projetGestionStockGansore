package com.stock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Article.
 */
@Entity
@Table(name = "article")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code_article", nullable = false)
    private String codeArticle;

    @Column(name = "libelle_article")
    private String libelleArticle;

    @Column(name = "pa_article")
    private Float paArticle;

    @Column(name = "pv_article")
    private Float pvArticle;

    @Column(name = "stock_initial_article")
    private Integer stockInitialArticle;

    @Column(name = "date_enregistrement_article")
    private LocalDate dateEnregistrementArticle;

    @Lob
    @Column(name = "image_article")
    private byte[] imageArticle;

    @Column(name = "image_article_content_type")
    private String imageArticleContentType;

    @OneToMany(mappedBy = "article")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Facture> factures = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "articles", allowSetters = true)
    private Categorie categorie;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeArticle() {
        return codeArticle;
    }

    public Article codeArticle(String codeArticle) {
        this.codeArticle = codeArticle;
        return this;
    }

    public void setCodeArticle(String codeArticle) {
        this.codeArticle = codeArticle;
    }

    public String getLibelleArticle() {
        return libelleArticle;
    }

    public Article libelleArticle(String libelleArticle) {
        this.libelleArticle = libelleArticle;
        return this;
    }

    public void setLibelleArticle(String libelleArticle) {
        this.libelleArticle = libelleArticle;
    }

    public Float getPaArticle() {
        return paArticle;
    }

    public Article paArticle(Float paArticle) {
        this.paArticle = paArticle;
        return this;
    }

    public void setPaArticle(Float paArticle) {
        this.paArticle = paArticle;
    }

    public Float getPvArticle() {
        return pvArticle;
    }

    public Article pvArticle(Float pvArticle) {
        this.pvArticle = pvArticle;
        return this;
    }

    public void setPvArticle(Float pvArticle) {
        this.pvArticle = pvArticle;
    }

    public Integer getStockInitialArticle() {
        return stockInitialArticle;
    }

    public Article stockInitialArticle(Integer stockInitialArticle) {
        this.stockInitialArticle = stockInitialArticle;
        return this;
    }

    public void setStockInitialArticle(Integer stockInitialArticle) {
        this.stockInitialArticle = stockInitialArticle;
    }

    public LocalDate getDateEnregistrementArticle() {
        return dateEnregistrementArticle;
    }

    public Article dateEnregistrementArticle(LocalDate dateEnregistrementArticle) {
        this.dateEnregistrementArticle = dateEnregistrementArticle;
        return this;
    }

    public void setDateEnregistrementArticle(LocalDate dateEnregistrementArticle) {
        this.dateEnregistrementArticle = dateEnregistrementArticle;
    }

    public byte[] getImageArticle() {
        return imageArticle;
    }

    public Article imageArticle(byte[] imageArticle) {
        this.imageArticle = imageArticle;
        return this;
    }

    public void setImageArticle(byte[] imageArticle) {
        this.imageArticle = imageArticle;
    }

    public String getImageArticleContentType() {
        return imageArticleContentType;
    }

    public Article imageArticleContentType(String imageArticleContentType) {
        this.imageArticleContentType = imageArticleContentType;
        return this;
    }

    public void setImageArticleContentType(String imageArticleContentType) {
        this.imageArticleContentType = imageArticleContentType;
    }

    public Set<Facture> getFactures() {
        return factures;
    }

    public Article factures(Set<Facture> factures) {
        this.factures = factures;
        return this;
    }

    public Article addFacture(Facture facture) {
        this.factures.add(facture);
        facture.setArticle(this);
        return this;
    }

    public Article removeFacture(Facture facture) {
        this.factures.remove(facture);
        facture.setArticle(null);
        return this;
    }

    public void setFactures(Set<Facture> factures) {
        this.factures = factures;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public Article categorie(Categorie categorie) {
        this.categorie = categorie;
        return this;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Article)) {
            return false;
        }
        return id != null && id.equals(((Article) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Article{" +
            "id=" + getId() +
            ", codeArticle='" + getCodeArticle() + "'" +
            ", libelleArticle='" + getLibelleArticle() + "'" +
            ", paArticle=" + getPaArticle() +
            ", pvArticle=" + getPvArticle() +
            ", stockInitialArticle=" + getStockInitialArticle() +
            ", dateEnregistrementArticle='" + getDateEnregistrementArticle() + "'" +
            ", imageArticle='" + getImageArticle() + "'" +
            ", imageArticleContentType='" + getImageArticleContentType() + "'" +
            "}";
    }
}
