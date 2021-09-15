package com.stock.service.mapper;

import com.stock.domain.Categorie;
import com.stock.service.dto.CategorieDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CategorieMapper extends EntityMapper<CategorieDTO, Categorie> {

    CategorieDTO toDto(Categorie categorie);

    Categorie toEntity(CategorieDTO categorieDTO);

    default Categorie fromId(Long id) {
        if (id == null) {
            return null;
        }
        Categorie profil = new Categorie();
        profil.setId(id);
        return profil;
    }
}
