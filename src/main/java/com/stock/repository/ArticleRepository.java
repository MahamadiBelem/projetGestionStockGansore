package com.stock.repository;

import com.stock.domain.Article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Spring Data  repository for the Article entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("select a from Article a where ( (:codeArticle is null or a.codeArticle=:codeArticle) and " +
        "(:libelleArticle is null or a.libelleArticle=:libelleArticle) and " +
        "(:categorieId is null or a.categorie.id=:categorieId) )")
    Page<Article> findAllWithCriteria(@Param("codeArticle") String codeArticle,
                                      @Param("libelleArticle") String libelleArticle,@Param("categorieId") Long categorieId, Pageable pageable);
}
