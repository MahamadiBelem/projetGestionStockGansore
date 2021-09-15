package com.stock.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero_cnib_client")
    private String numeroCnibClient;

    @Column(name = "nom_client")
    private String nomClient;

    @Column(name = "prenom_client")
    private String prenomClient;

    @Column(name = "telephone_1_client")
    private String telephone1Client;

    @Column(name = "telephone_2_client")
    private String telephone2Client;

    @Column(name = "ville_client")
    private String villeClient;

    @Column(name = "email_client")
    private String emailClient;

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Commande> commandes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCnibClient() {
        return numeroCnibClient;
    }

    public Client numeroCnibClient(String numeroCnibClient) {
        this.numeroCnibClient = numeroCnibClient;
        return this;
    }

    public void setNumeroCnibClient(String numeroCnibClient) {
        this.numeroCnibClient = numeroCnibClient;
    }

    public String getNomClient() {
        return nomClient;
    }

    public Client nomClient(String nomClient) {
        this.nomClient = nomClient;
        return this;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getPrenomClient() {
        return prenomClient;
    }

    public Client prenomClient(String prenomClient) {
        this.prenomClient = prenomClient;
        return this;
    }

    public void setPrenomClient(String prenomClient) {
        this.prenomClient = prenomClient;
    }

    public String getTelephone1Client() {
        return telephone1Client;
    }

    public Client telephone1Client(String telephone1Client) {
        this.telephone1Client = telephone1Client;
        return this;
    }

    public void setTelephone1Client(String telephone1Client) {
        this.telephone1Client = telephone1Client;
    }

    public String getTelephone2Client() {
        return telephone2Client;
    }

    public Client telephone2Client(String telephone2Client) {
        this.telephone2Client = telephone2Client;
        return this;
    }

    public void setTelephone2Client(String telephone2Client) {
        this.telephone2Client = telephone2Client;
    }

    public String getVilleClient() {
        return villeClient;
    }

    public Client villeClient(String villeClient) {
        this.villeClient = villeClient;
        return this;
    }

    public void setVilleClient(String villeClient) {
        this.villeClient = villeClient;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public Client emailClient(String emailClient) {
        this.emailClient = emailClient;
        return this;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    public Set<Commande> getCommandes() {
        return commandes;
    }

    public Client commandes(Set<Commande> commandes) {
        this.commandes = commandes;
        return this;
    }

    public Client addCommande(Commande commande) {
        this.commandes.add(commande);
        commande.setClient(this);
        return this;
    }

    public Client removeCommande(Commande commande) {
        this.commandes.remove(commande);
        commande.setClient(null);
        return this;
    }

    public void setCommandes(Set<Commande> commandes) {
        this.commandes = commandes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", numeroCnibClient='" + getNumeroCnibClient() + "'" +
            ", nomClient='" + getNomClient() + "'" +
            ", prenomClient='" + getPrenomClient() + "'" +
            ", telephone1Client='" + getTelephone1Client() + "'" +
            ", telephone2Client='" + getTelephone2Client() + "'" +
            ", villeClient='" + getVilleClient() + "'" +
            ", emailClient='" + getEmailClient() + "'" +
            "}";
    }
}
