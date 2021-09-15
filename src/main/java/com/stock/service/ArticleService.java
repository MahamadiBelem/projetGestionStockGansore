package com.stock.service;

import com.stock.domain.Article;
import com.stock.domain.Categorie;
import com.stock.repository.ArticleRepository;
import com.stock.repository.CategorieRepository;
import com.stock.service.dto.ArticleDTO;
import com.stock.service.dto.XArticleDTO;
import com.stock.service.mapper.ArticleMapper;
import com.stock.web.rest.errors.CustomParameterizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class ArticleService {
    private final Logger log = LoggerFactory.getLogger(ProfilService.class);
    private final CategorieRepository categorieRepository;
    private final ArticleMapper articleMapper;
    private final ArticleRepository articleRepository;

    public ArticleService(CategorieRepository categorieRepository, ArticleMapper articleMapper, ArticleRepository articleRepository) {
        this.categorieRepository = categorieRepository;
        this.articleMapper = articleMapper;
        this.articleRepository = articleRepository;
    }

    public ArticleDTO save(ArticleDTO articleDTO){
        log.debug("Request to save Article : {}", articleDTO);
        Article article=articleMapper.toEntity(articleDTO);

        if(articleDTO.getId()!=null){
            article=articleRepository.getOne(articleDTO.getId());
            if(article.getId()!=null){
                article.setDateEnregistrementArticle(LocalDate.now());
                article.setStockInitialArticle(articleDTO.getQuantite());
                article=articleRepository.save(article);
            } else {
                throw new CustomParameterizedException("Veillez selectionnez une catégorie");
            }

        } else {
            if(articleDTO.getCategorieId()!=null) {
                Categorie categorie=categorieRepository.getOne(articleDTO.getCategorieId());
                article.setCategorie(categorie);
                article.setDateEnregistrementArticle(LocalDate.now());
                article.setStockInitialArticle(articleDTO.getQuantite());
                article=articleRepository.save(article);

            } else {
                throw new CustomParameterizedException("Veillez selectionnez une catégorie");
            }
        }

        return articleMapper.toDto(article);

    }

    public Page<ArticleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all articles");
       return   articleRepository.findAll(pageable).map(articleMapper::toDto);
    }

    public Page<ArticleDTO> findAllWithCriteria(ArticleDTO articleDTO,Pageable pageable) {
        log.debug("Request to get all articles");
        return   articleRepository.findAllWithCriteria(articleDTO.getCodeArticle(),
            articleDTO.getLibelleArticle(),articleDTO.getCategorieId(),pageable).map(articleMapper::toDto);
    }

    public ArticleDTO findOne(Long articleId) {
        log.debug("requete to get article"+articleId);
        Article article=articleRepository.getOne(articleId);
        if(article.getId()!=null){
            return articleMapper.toDto(article);
        }
        return null;
    }

    public ArticleDTO addQuantite(ArticleDTO articleDTO) {
        log.debug("Request to add quantité"+articleDTO);
        //Article article =articleMapper.toEntity(articleDTO);
        Article article=articleRepository.getOne(articleDTO.getId());
        if(article.getId()!=null){
            article.setStockInitialArticle(article.getStockInitialArticle()+articleDTO.getQuantite());
            articleRepository.save(article);
            return articleMapper.toDto(article);
        }
        throw new CustomParameterizedException("Article introuvable");
    }


}
