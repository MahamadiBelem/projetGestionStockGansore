package com.stock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Commande.
 */
@Entity
@Table(name = "commande")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Commande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_commande")
    private String codeCommande;

    @Column(name = "date_commande")
    private LocalDate dateCommande;

    @Column(name = "libelle_commande")
    private String libelleCommande;

    @Column(name = "quantite_commande")
    private Float quantiteCommande;

    @Column(name = "status_commande")
    private Boolean statusCommande;

    @Column(name = "montant_verse_commande")
    private Float montantVerseCommande;

    @Column(name = "date_livraison")
    private LocalDate dateLivraison;

    @OneToMany(mappedBy = "commande")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Facture> factures = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "commandes", allowSetters = true)
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeCommande() {
        return codeCommande;
    }

    public Commande codeCommande(String codeCommande) {
        this.codeCommande = codeCommande;
        return this;
    }

    public void setCodeCommande(String codeCommande) {
        this.codeCommande = codeCommande;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public Commande dateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
        return this;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getLibelleCommande() {
        return libelleCommande;
    }

    public Commande libelleCommande(String libelleCommande) {
        this.libelleCommande = libelleCommande;
        return this;
    }

    public void setLibelleCommande(String libelleCommande) {
        this.libelleCommande = libelleCommande;
    }

    public Float getQuantiteCommande() {
        return quantiteCommande;
    }

    public Commande quantiteCommande(Float quantiteCommande) {
        this.quantiteCommande = quantiteCommande;
        return this;
    }

    public void setQuantiteCommande(Float quantiteCommande) {
        this.quantiteCommande = quantiteCommande;
    }

    public Boolean isStatusCommande() {
        return statusCommande;
    }

    public Commande statusCommande(Boolean statusCommande) {
        this.statusCommande = statusCommande;
        return this;
    }

    public void setStatusCommande(Boolean statusCommande) {
        this.statusCommande = statusCommande;
    }

    public Float getMontantVerseCommande() {
        return montantVerseCommande;
    }

    public Commande montantVerseCommande(Float montantVerseCommande) {
        this.montantVerseCommande = montantVerseCommande;
        return this;
    }

    public void setMontantVerseCommande(Float montantVerseCommande) {
        this.montantVerseCommande = montantVerseCommande;
    }

    public LocalDate getDateLivraison() {
        return dateLivraison;
    }

    public Commande dateLivraison(LocalDate dateLivraison) {
        this.dateLivraison = dateLivraison;
        return this;
    }

    public void setDateLivraison(LocalDate dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public Set<Facture> getFactures() {
        return factures;
    }

    public Commande factures(Set<Facture> factures) {
        this.factures = factures;
        return this;
    }

    public Commande addFacture(Facture facture) {
        this.factures.add(facture);
        facture.setCommande(this);
        return this;
    }

    public Commande removeFacture(Facture facture) {
        this.factures.remove(facture);
        facture.setCommande(null);
        return this;
    }

    public void setFactures(Set<Facture> factures) {
        this.factures = factures;
    }

    public Client getClient() {
        return client;
    }

    public Commande client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commande)) {
            return false;
        }
        return id != null && id.equals(((Commande) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Commande{" +
            "id=" + getId() +
            ", codeCommande='" + getCodeCommande() + "'" +
            ", dateCommande='" + getDateCommande() + "'" +
            ", libelleCommande='" + getLibelleCommande() + "'" +
            ", quantiteCommande=" + getQuantiteCommande() +
            ", statusCommande='" + isStatusCommande() + "'" +
            ", montantVerseCommande=" + getMontantVerseCommande() +
            ", dateLivraison='" + getDateLivraison() + "'" +
            "}";
    }
}
