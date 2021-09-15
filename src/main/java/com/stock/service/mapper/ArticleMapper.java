package com.stock.service.mapper;


import com.stock.domain.Article;
import com.stock.service.dto.ArticleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategorieMapper.class})
public interface ArticleMapper extends EntityMapper<ArticleDTO, Article> {

    @Mapping(source = "categorie.id", target = "categorieId")
    @Mapping(source = "categorie.codeCategorie", target = "codeCategorie")
    @Mapping(source = "categorie.libelleCategorie", target = "libelleCategorie")
    ArticleDTO toDto(Article article);

    @Mapping(source = "categorieId", target = "categorie")
    Article toEntity(ArticleDTO articleDTO);

    default Article fromId(Long id) {
        if (id == null) {
            return null;
        }
        Article article = new Article();
        article.setId(id);
        return article;
    }
}
