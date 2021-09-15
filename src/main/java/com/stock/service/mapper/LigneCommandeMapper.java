package com.stock.service.mapper;


import com.stock.domain.*;
import com.stock.service.dto.LigneCommandeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LigneCommande} and its DTO {@link LigneCommandeDTO}.
 */
@Mapper(componentModel = "spring", uses = {CommandeMapper.class, ArticleMapper.class})
public interface LigneCommandeMapper extends EntityMapper<LigneCommandeDTO, LigneCommande> {

    @Mapping(source = "commande.id", target = "commandeId")
    @Mapping(source = "article.id", target = "articleId")
    LigneCommandeDTO toDto(LigneCommande ligneCommande);

    @Mapping(source = "commandeId", target = "commande")
    @Mapping(source = "articleId", target = "article")
    LigneCommande toEntity(LigneCommandeDTO ligneCommandeDTO);

    default LigneCommande fromId(Long id) {
        if (id == null) {
            return null;
        }
        LigneCommande ligneCommande = new LigneCommande();
        ligneCommande.setId(id);
        return ligneCommande;
    }
}
