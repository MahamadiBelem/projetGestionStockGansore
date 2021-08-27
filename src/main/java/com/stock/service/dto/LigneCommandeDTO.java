package com.stock.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.stock.domain.LigneCommande} entity.
 */
public class LigneCommandeDTO implements Serializable {
    
    private Long id;

    private String description;


    private Long commandeId;

    private Long articleId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(Long commandeId) {
        this.commandeId = commandeId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LigneCommandeDTO)) {
            return false;
        }

        return id != null && id.equals(((LigneCommandeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LigneCommandeDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", commandeId=" + getCommandeId() +
            ", articleId=" + getArticleId() +
            "}";
    }
}
