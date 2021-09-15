package com.stock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Facture.
 */
@Entity
@Table(name = "facture")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Facture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero_facture")
    private String numeroFacture;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "libelle_facture")
    private String libelleFacture;

    @Column(name = "prescipteur_facture")
    private String prescipteurFacture;

    @ManyToOne
    @JsonIgnoreProperties(value = "factures", allowSetters = true)
    private Commande commande;

    @ManyToOne
    @JsonIgnoreProperties(value = "factures", allowSetters = true)
    private Article article;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroFacture() {
        return numeroFacture;
    }

    public Facture numeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
        return this;
    }

    public void setNumeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
    }

    public String getLibelleFacture() {
        return libelleFacture;
    }

    public Facture libelleFacture(String libelleFacture) {
        this.libelleFacture = libelleFacture;
        return this;
    }

    public void setLibelleFacture(String libelleFacture) {
        this.libelleFacture = libelleFacture;
    }

    public String getPrescipteurFacture() {
        return prescipteurFacture;
    }

    public Facture prescipteurFacture(String prescipteurFacture) {
        this.prescipteurFacture = prescipteurFacture;
        return this;
    }

    public void setPrescipteurFacture(String prescipteurFacture) {
        this.prescipteurFacture = prescipteurFacture;
    }

    public Commande getCommande() {
        return commande;
    }

    public Facture commande(Commande commande) {
        this.commande = commande;
        return this;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Article getArticle() {
        return article;
    }

    public Facture article(Article article) {
        this.article = article;
        return this;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Facture)) {
            return false;
        }
        return id != null && id.equals(((Facture) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Facture{" +
            "id=" + getId() +
            ", numeroFacture='" + getNumeroFacture() + "'" +
            ", libelleFacture='" + getLibelleFacture() + "'" +
            ", prescipteurFacture='" + getPrescipteurFacture() + "'" +
            "}";
    }
}
