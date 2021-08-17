package com.stock.service.dto;

import java.time.LocalDate;

public class ArticleDTO {
    public Long id;
    public String codeArticle;
    public String libelleArticle;
    public Float pvArticle;
    public Float paArticle;
    public Integer stockInitialArticle;
    public LocalDate dateEnregistrementArticle;
    public String imageArticleContentType;
    public Long categorieId;
    public String codeCategorie;
    public String libelleCategorie;
    private byte[] imageArticle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeArticle() {
        return codeArticle;
    }

    public void setCodeArticle(String codeArticle) {
        this.codeArticle = codeArticle;
    }

    public String getLibelleArticle() {
        return libelleArticle;
    }

    public void setLibelleArticle(String libelleArticle) {
        this.libelleArticle = libelleArticle;
    }

    public Float getPvArticle() {
        return pvArticle;
    }

    public void setPvArticle(Float pvArticle) {
        this.pvArticle = pvArticle;
    }

    public Integer getStockInitialArticle() {
        return stockInitialArticle;
    }

    public void setStockInitialArticle(Integer stockInitialArticle) {
        this.stockInitialArticle = stockInitialArticle;
    }

    public LocalDate getDateEnregistrementArticle() {
        return dateEnregistrementArticle;
    }

    public void setDateEnregistrementArticle(LocalDate dateEnregistrementArticle) {
        this.dateEnregistrementArticle = dateEnregistrementArticle;
    }

    public String getImageArticleContentType() {
        return imageArticleContentType;
    }

    public void setImageArticleContentType(String imageArticleContentType) {
        this.imageArticleContentType = imageArticleContentType;
    }

    public Long getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Long categorieId) {
        this.categorieId = categorieId;
    }

    public Float getPaArticle() {
        return paArticle;
    }

    public void setPaArticle(Float paArticle) {
        this.paArticle = paArticle;
    }

    public String getCodeCategorie() {
        return codeCategorie;
    }

    public void setCodeCategorie(String codeCategorie) {
        this.codeCategorie = codeCategorie;
    }

    public String getLibelleCategorie() {
        return libelleCategorie;
    }

    public void setLibelleCategorie(String libelleCategorie) {
        this.libelleCategorie = libelleCategorie;
    }

    public byte[] getImageArticle() {
        return imageArticle;
    }

    public void setImageArticle(byte[] imageArticle) {
        this.imageArticle = imageArticle;
    }
}
