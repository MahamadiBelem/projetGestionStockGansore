package com.stock.web.rest;

import com.stock.domain.Categorie;
import com.stock.repository.CategorieRepository;
import com.stock.service.dto.CategorieDTO;
import com.stock.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.stock.domain.Categorie}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CategorieResource {

    private final Logger log = LoggerFactory.getLogger(CategorieResource.class);

    private static final String ENTITY_NAME = "categorie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategorieRepository categorieRepository;

    public CategorieResource(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    /**
     * {@code POST  /categories} : Create a new categorie.
     *
     * @param categorie the categorie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categorie, or with status {@code 400 (Bad Request)} if the categorie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categories")
    public ResponseEntity<Categorie> createCategorie(@Valid @RequestBody Categorie categorie) throws URISyntaxException {
        log.debug("REST request to save Categorie : {}", categorie);
        if (categorie.getId() != null) {
            throw new BadRequestAlertException("A new categorie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Categorie result = categorieRepository.save(categorie);
        return ResponseEntity.created(new URI("/api/categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categories} : Updates an existing categorie.
     *
     * @param categorie the categorie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorie,
     * or with status {@code 400 (Bad Request)} if the categorie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categorie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categories")
    public ResponseEntity<Categorie> updateCategorie(@Valid @RequestBody Categorie categorie) throws URISyntaxException {
        log.debug("REST request to update Categorie : {}", categorie);
        if (categorie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Categorie result = categorieRepository.save(categorie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categorie.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /categories} : get all the categories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categories in body.
     */
    @GetMapping("/categories")
    public ResponseEntity<List<Categorie>> getAllCategories(Pageable pageable) {
        log.debug("REST request to get a page of Categories");
        Page<Categorie> page = categorieRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categories/:id} : get the "id" categorie.
     *
     * @param id the id of the categorie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categorie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categories/{id}")
    public ResponseEntity<Categorie> getCategorie(@PathVariable Long id) {
        log.debug("REST request to get Categorie : {}", id);
        Optional<Categorie> categorie = categorieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(categorie);
    }

    /**
     * {@code DELETE  /categories/:id} : delete the "id" categorie.
     *
     * @param id the id of the categorie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable Long id) {
        log.debug("REST request to delete Categorie : {}", id);
        categorieRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
