package com.stock.web.rest;

import com.stock.GestionStockApp;
import com.stock.domain.Article;
import com.stock.repository.ArticleRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ArticleResource} REST controller.
 */
@SpringBootTest(classes = GestionStockApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ArticleResourceIT {

    private static final String DEFAULT_CODE_ARTICLE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_ARTICLE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_ARTICLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_ARTICLE = "BBBBBBBBBB";

    private static final Float DEFAULT_PA_ARTICLE = 1F;
    private static final Float UPDATED_PA_ARTICLE = 2F;

    private static final Float DEFAULT_PV_ARTICLE = 1F;
    private static final Float UPDATED_PV_ARTICLE = 2F;

    private static final Integer DEFAULT_STOCK_INITIAL_ARTICLE = 1;
    private static final Integer UPDATED_STOCK_INITIAL_ARTICLE = 2;

    private static final LocalDate DEFAULT_DATE_ENREGISTREMENT_ARTICLE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ENREGISTREMENT_ARTICLE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_IMAGE_ARTICLE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_ARTICLE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_ARTICLE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_ARTICLE_CONTENT_TYPE = "image/png";

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArticleMockMvc;

    private Article article;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Article createEntity(EntityManager em) {
        Article article = new Article()
            .codeArticle(DEFAULT_CODE_ARTICLE)
            .libelleArticle(DEFAULT_LIBELLE_ARTICLE)
            .paArticle(DEFAULT_PA_ARTICLE)
            .pvArticle(DEFAULT_PV_ARTICLE)
            .stockInitialArticle(DEFAULT_STOCK_INITIAL_ARTICLE)
            .dateEnregistrementArticle(DEFAULT_DATE_ENREGISTREMENT_ARTICLE)
            .imageArticle(DEFAULT_IMAGE_ARTICLE)
            .imageArticleContentType(DEFAULT_IMAGE_ARTICLE_CONTENT_TYPE);
        return article;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Article createUpdatedEntity(EntityManager em) {
        Article article = new Article()
            .codeArticle(UPDATED_CODE_ARTICLE)
            .libelleArticle(UPDATED_LIBELLE_ARTICLE)
            .paArticle(UPDATED_PA_ARTICLE)
            .pvArticle(UPDATED_PV_ARTICLE)
            .stockInitialArticle(UPDATED_STOCK_INITIAL_ARTICLE)
            .dateEnregistrementArticle(UPDATED_DATE_ENREGISTREMENT_ARTICLE)
            .imageArticle(UPDATED_IMAGE_ARTICLE)
            .imageArticleContentType(UPDATED_IMAGE_ARTICLE_CONTENT_TYPE);
        return article;
    }

    @BeforeEach
    public void initTest() {
        article = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticle() throws Exception {
        int databaseSizeBeforeCreate = articleRepository.findAll().size();
        // Create the Article
        restArticleMockMvc.perform(post("/api/articles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isCreated());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeCreate + 1);
        Article testArticle = articleList.get(articleList.size() - 1);
        assertThat(testArticle.getCodeArticle()).isEqualTo(DEFAULT_CODE_ARTICLE);
        assertThat(testArticle.getLibelleArticle()).isEqualTo(DEFAULT_LIBELLE_ARTICLE);
        assertThat(testArticle.getPaArticle()).isEqualTo(DEFAULT_PA_ARTICLE);
        assertThat(testArticle.getPvArticle()).isEqualTo(DEFAULT_PV_ARTICLE);
        assertThat(testArticle.getStockInitialArticle()).isEqualTo(DEFAULT_STOCK_INITIAL_ARTICLE);
        assertThat(testArticle.getDateEnregistrementArticle()).isEqualTo(DEFAULT_DATE_ENREGISTREMENT_ARTICLE);
        assertThat(testArticle.getImageArticle()).isEqualTo(DEFAULT_IMAGE_ARTICLE);
        assertThat(testArticle.getImageArticleContentType()).isEqualTo(DEFAULT_IMAGE_ARTICLE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createArticleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articleRepository.findAll().size();

        // Create the Article with an existing ID
        article.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticleMockMvc.perform(post("/api/articles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeArticleIsRequired() throws Exception {
        int databaseSizeBeforeTest = articleRepository.findAll().size();
        // set the field null
        article.setCodeArticle(null);

        // Create the Article, which fails.


        restArticleMockMvc.perform(post("/api/articles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isBadRequest());

        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllArticles() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        // Get all the articleList
        restArticleMockMvc.perform(get("/api/articles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(article.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeArticle").value(hasItem(DEFAULT_CODE_ARTICLE)))
            .andExpect(jsonPath("$.[*].libelleArticle").value(hasItem(DEFAULT_LIBELLE_ARTICLE)))
            .andExpect(jsonPath("$.[*].paArticle").value(hasItem(DEFAULT_PA_ARTICLE.doubleValue())))
            .andExpect(jsonPath("$.[*].pvArticle").value(hasItem(DEFAULT_PV_ARTICLE.doubleValue())))
            .andExpect(jsonPath("$.[*].stockInitialArticle").value(hasItem(DEFAULT_STOCK_INITIAL_ARTICLE)))
            .andExpect(jsonPath("$.[*].dateEnregistrementArticle").value(hasItem(DEFAULT_DATE_ENREGISTREMENT_ARTICLE.toString())))
            .andExpect(jsonPath("$.[*].imageArticleContentType").value(hasItem(DEFAULT_IMAGE_ARTICLE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageArticle").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_ARTICLE))));
    }
    
    @Test
    @Transactional
    public void getArticle() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        // Get the article
        restArticleMockMvc.perform(get("/api/articles/{id}", article.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(article.getId().intValue()))
            .andExpect(jsonPath("$.codeArticle").value(DEFAULT_CODE_ARTICLE))
            .andExpect(jsonPath("$.libelleArticle").value(DEFAULT_LIBELLE_ARTICLE))
            .andExpect(jsonPath("$.paArticle").value(DEFAULT_PA_ARTICLE.doubleValue()))
            .andExpect(jsonPath("$.pvArticle").value(DEFAULT_PV_ARTICLE.doubleValue()))
            .andExpect(jsonPath("$.stockInitialArticle").value(DEFAULT_STOCK_INITIAL_ARTICLE))
            .andExpect(jsonPath("$.dateEnregistrementArticle").value(DEFAULT_DATE_ENREGISTREMENT_ARTICLE.toString()))
            .andExpect(jsonPath("$.imageArticleContentType").value(DEFAULT_IMAGE_ARTICLE_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageArticle").value(Base64Utils.encodeToString(DEFAULT_IMAGE_ARTICLE)));
    }
    @Test
    @Transactional
    public void getNonExistingArticle() throws Exception {
        // Get the article
        restArticleMockMvc.perform(get("/api/articles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticle() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        int databaseSizeBeforeUpdate = articleRepository.findAll().size();

        // Update the article
        Article updatedArticle = articleRepository.findById(article.getId()).get();
        // Disconnect from session so that the updates on updatedArticle are not directly saved in db
        em.detach(updatedArticle);
        updatedArticle
            .codeArticle(UPDATED_CODE_ARTICLE)
            .libelleArticle(UPDATED_LIBELLE_ARTICLE)
            .paArticle(UPDATED_PA_ARTICLE)
            .pvArticle(UPDATED_PV_ARTICLE)
            .stockInitialArticle(UPDATED_STOCK_INITIAL_ARTICLE)
            .dateEnregistrementArticle(UPDATED_DATE_ENREGISTREMENT_ARTICLE)
            .imageArticle(UPDATED_IMAGE_ARTICLE)
            .imageArticleContentType(UPDATED_IMAGE_ARTICLE_CONTENT_TYPE);

        restArticleMockMvc.perform(put("/api/articles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedArticle)))
            .andExpect(status().isOk());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
        Article testArticle = articleList.get(articleList.size() - 1);
        assertThat(testArticle.getCodeArticle()).isEqualTo(UPDATED_CODE_ARTICLE);
        assertThat(testArticle.getLibelleArticle()).isEqualTo(UPDATED_LIBELLE_ARTICLE);
        assertThat(testArticle.getPaArticle()).isEqualTo(UPDATED_PA_ARTICLE);
        assertThat(testArticle.getPvArticle()).isEqualTo(UPDATED_PV_ARTICLE);
        assertThat(testArticle.getStockInitialArticle()).isEqualTo(UPDATED_STOCK_INITIAL_ARTICLE);
        assertThat(testArticle.getDateEnregistrementArticle()).isEqualTo(UPDATED_DATE_ENREGISTREMENT_ARTICLE);
        assertThat(testArticle.getImageArticle()).isEqualTo(UPDATED_IMAGE_ARTICLE);
        assertThat(testArticle.getImageArticleContentType()).isEqualTo(UPDATED_IMAGE_ARTICLE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingArticle() throws Exception {
        int databaseSizeBeforeUpdate = articleRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleMockMvc.perform(put("/api/articles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArticle() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        int databaseSizeBeforeDelete = articleRepository.findAll().size();

        // Delete the article
        restArticleMockMvc.perform(delete("/api/articles/{id}", article.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
