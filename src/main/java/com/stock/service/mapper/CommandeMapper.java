package com.stock.service.mapper;


import com.stock.domain.Client;
import com.stock.domain.Commande;
import com.stock.service.dto.CommandeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {Client.class})
public interface CommandeMapper extends EntityMapper<CommandeDTO, Commande> {

    @Mapping(source = "client.id", target = "clientId")
    CommandeDTO toDto(Commande commande);


    @Mapping(source = "clientId", target = "client.id")
    Commande toEntity(CommandeDTO commandeDTO);

    default Commande fromId(Long id) {
        if (id == null) {
            return null;
        }
        Commande commande = new Commande();
        commande.setId(id);
        return commande;
    }
}
