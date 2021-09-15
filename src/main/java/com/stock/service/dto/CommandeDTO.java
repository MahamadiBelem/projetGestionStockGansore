package com.stock.service.dto;

import com.stock.domain.Article;

import java.time.LocalDate;
import java.util.List;

public class CommandeDTO {

    private Long id;

    private String codeCommande;

    private LocalDate dateCommande;

    private String libelleCommande;

    private Float quantiteCommande;

    private Boolean statusCommande;

    private Float montantVerseCommande;

    private LocalDate dateLivraison;

    private Long clientId;

    private List<ArticleDTO> articles;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeCommande() {
        return codeCommande;
    }

    public void setCodeCommande(String codeCommande) {
        this.codeCommande = codeCommande;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getLibelleCommande() {
        return libelleCommande;
    }

    public void setLibelleCommande(String libelleCommande) {
        this.libelleCommande = libelleCommande;
    }

    public Float getQuantiteCommande() {
        return quantiteCommande;
    }

    public void setQuantiteCommande(Float quantiteCommande) {
        this.quantiteCommande = quantiteCommande;
    }

    public Boolean getStatusCommande() {
        return statusCommande;
    }

    public void setStatusCommande(Boolean statusCommande) {
        this.statusCommande = statusCommande;
    }

    public Float getMontantVerseCommande() {
        return montantVerseCommande;
    }

    public void setMontantVerseCommande(Float montantVerseCommande) {
        this.montantVerseCommande = montantVerseCommande;
    }

    public LocalDate getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(LocalDate dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public List<ArticleDTO> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleDTO> articles) {
        this.articles = articles;
    }
}
