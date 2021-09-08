package com.stock.service;


import com.stock.domain.Article;
import com.stock.domain.Client;
import com.stock.domain.Commande;
import com.stock.domain.LigneCommande;
import com.stock.repository.ArticleRepository;
import com.stock.repository.ClientRepository;
import com.stock.repository.CommandeRepository;
import com.stock.repository.LigneCommandeRepository;
import com.stock.service.dto.ArticleDTO;
import com.stock.service.dto.CommandeDTO;
import com.stock.service.mapper.ArticleMapper;
import com.stock.service.mapper.CommandeMapper;
import com.stock.web.rest.errors.CustomParameterizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommandeService {
    private final Logger log = LoggerFactory.getLogger(CommandeService.class);
    private final ClientRepository clientRepository;
    private final CommandeMapper commandeMapper;
    private final CommandeRepository commandeRepository;
    private final LigneCommandeRepository ligneCommandeRepository;
    private final ArticleMapper articleMapper;
    private final ArticleRepository articleRepository;

    public CommandeService(CommandeRepository commandeRepository, ClientRepository clientRepository, CommandeMapper commandeMapper, CommandeRepository commandeRepository1, LigneCommandeRepository ligneCommandeRepository, ArticleMapper articleMapper, ArticleRepository articleRepository) {
        this.clientRepository = clientRepository;
        this.commandeMapper = commandeMapper;
        this.commandeRepository = commandeRepository1;
        this.ligneCommandeRepository = ligneCommandeRepository;
        this.articleMapper = articleMapper;

        this.articleRepository = articleRepository;
    }

    public CommandeDTO save(CommandeDTO commandeDTO){
        log.debug("Request to save Commande : {}", commandeDTO);
        Commande commade=commandeMapper.toEntity(commandeDTO);
        if(commandeDTO.getClientId()!=null){
            Client client=clientRepository.getOne(commandeDTO.getClientId());
            if(client.getId()!=null) {
                commade.setClient(client);
            }
        }

        return commandeDTO;
    }

    @Transactional
    public CommandeDTO saveComande(CommandeDTO commandeDTO){
        log.debug("Request to save Commande : {}", commandeDTO);
        Commande commande=commandeMapper.toEntity(commandeDTO);
        Float montanTotal= (float) 0;
        Integer quantiteTotal=0;
        List<ArticleDTO> articles=commandeDTO.getArticles();
        if (articles.size()>0){
            Client client=clientRepository.getOne(commandeDTO.getClientId());
            if(client.getId()!=null){
                commande.setClient(client);
                commande=commandeRepository.save(commande);

                for(ArticleDTO articleDTO: articles){
                    LigneCommande ligneCommande=new LigneCommande();
                    Article article=articleMapper.toEntity(articleDTO);
                    quantiteTotal=quantiteTotal+articleDTO.getQuantite();
                    montanTotal=montanTotal+article.getPvArticle();
                    ligneCommande.setArticle(article);
                    ligneCommande.setQuantiteCommander(articleDTO.getQuantite());
                    ligneCommande.setCommande(commande);
                    ligneCommandeRepository.save(ligneCommande);
                    article.setStockInitialArticle(article.getStockInitialArticle()-articleDTO.getQuantite());
                    articleRepository.save(article);
                }
                commande.setMontantTotal(montanTotal);
                commande.setQuantiteCommande(quantiteTotal);
                commandeRepository.save(commande);
                return commandeMapper.toDto(commande);
            } else{
                throw new CustomParameterizedException("Client introuvable");
            }

        } else{
            throw new CustomParameterizedException("Veillez selectionnez au moins un article ");
        }
    }
}
