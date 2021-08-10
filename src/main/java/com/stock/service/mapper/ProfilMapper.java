package com.stock.service.mapper;


import com.stock.domain.*;
import com.stock.service.dto.ProfilDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Profil} and its DTO {@link ProfilDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProfilMapper extends EntityMapper<ProfilDTO, Profil> {



    default Profil fromId(Long id) {
        if (id == null) {
            return null;
        }
        Profil profil = new Profil();
        profil.setId(id);
        return profil;
    }
}
