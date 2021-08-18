package com.stock.web.rest;

import com.stock.GestionStockApp;
import com.stock.domain.Commande;
import com.stock.repository.CommandeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CommandeResource} REST controller.
 */
@SpringBootTest(classes = GestionStockApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CommandeResourceIT {

    private static final String DEFAULT_CODE_COMMANDE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_COMMANDE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_COMMANDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_COMMANDE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LIBELLE_COMMANDE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_COMMANDE = "BBBBBBBBBB";

    private static final Float DEFAULT_QUANTITE_COMMANDE = 1F;
    private static final Float UPDATED_QUANTITE_COMMANDE = 2F;

    private static final Boolean DEFAULT_STATUS_COMMANDE = false;
    private static final Boolean UPDATED_STATUS_COMMANDE = true;

    private static final Float DEFAULT_MONTANT_VERSE_COMMANDE = 1F;
    private static final Float UPDATED_MONTANT_VERSE_COMMANDE = 2F;

    private static final LocalDate DEFAULT_DATE_LIVRAISON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_LIVRAISON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommandeMockMvc;

    private Commande commande;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commande createEntity(EntityManager em) {
        Commande commande = new Commande()
            .codeCommande(DEFAULT_CODE_COMMANDE)
            .dateCommande(DEFAULT_DATE_COMMANDE)
            .libelleCommande(DEFAULT_LIBELLE_COMMANDE)
            .quantiteCommande(DEFAULT_QUANTITE_COMMANDE)
            .statusCommande(DEFAULT_STATUS_COMMANDE)
            .montantVerseCommande(DEFAULT_MONTANT_VERSE_COMMANDE)
            .dateLivraison(DEFAULT_DATE_LIVRAISON);
        return commande;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commande createUpdatedEntity(EntityManager em) {
        Commande commande = new Commande()
            .codeCommande(UPDATED_CODE_COMMANDE)
            .dateCommande(UPDATED_DATE_COMMANDE)
            .libelleCommande(UPDATED_LIBELLE_COMMANDE)
            .quantiteCommande(UPDATED_QUANTITE_COMMANDE)
            .statusCommande(UPDATED_STATUS_COMMANDE)
            .montantVerseCommande(UPDATED_MONTANT_VERSE_COMMANDE)
            .dateLivraison(UPDATED_DATE_LIVRAISON);
        return commande;
    }

    @BeforeEach
    public void initTest() {
        commande = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommande() throws Exception {
        int databaseSizeBeforeCreate = commandeRepository.findAll().size();
        // Create the Commande
        restCommandeMockMvc.perform(post("/api/commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commande)))
            .andExpect(status().isCreated());

        // Validate the Commande in the database
        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeCreate + 1);
        Commande testCommande = commandeList.get(commandeList.size() - 1);
        assertThat(testCommande.getCodeCommande()).isEqualTo(DEFAULT_CODE_COMMANDE);
        assertThat(testCommande.getDateCommande()).isEqualTo(DEFAULT_DATE_COMMANDE);
        assertThat(testCommande.getLibelleCommande()).isEqualTo(DEFAULT_LIBELLE_COMMANDE);
        assertThat(testCommande.getQuantiteCommande()).isEqualTo(DEFAULT_QUANTITE_COMMANDE);
        assertThat(testCommande.isStatusCommande()).isEqualTo(DEFAULT_STATUS_COMMANDE);
        assertThat(testCommande.getMontantVerseCommande()).isEqualTo(DEFAULT_MONTANT_VERSE_COMMANDE);
        assertThat(testCommande.getDateLivraison()).isEqualTo(DEFAULT_DATE_LIVRAISON);
    }

    @Test
    @Transactional
    public void createCommandeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commandeRepository.findAll().size();

        // Create the Commande with an existing ID
        commande.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommandeMockMvc.perform(post("/api/commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commande)))
            .andExpect(status().isBadRequest());

        // Validate the Commande in the database
        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCommandes() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList
        restCommandeMockMvc.perform(get("/api/commandes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commande.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeCommande").value(hasItem(DEFAULT_CODE_COMMANDE)))
            .andExpect(jsonPath("$.[*].dateCommande").value(hasItem(DEFAULT_DATE_COMMANDE.toString())))
            .andExpect(jsonPath("$.[*].libelleCommande").value(hasItem(DEFAULT_LIBELLE_COMMANDE)))
            .andExpect(jsonPath("$.[*].quantiteCommande").value(hasItem(DEFAULT_QUANTITE_COMMANDE.doubleValue())))
            .andExpect(jsonPath("$.[*].statusCommande").value(hasItem(DEFAULT_STATUS_COMMANDE.booleanValue())))
            .andExpect(jsonPath("$.[*].montantVerseCommande").value(hasItem(DEFAULT_MONTANT_VERSE_COMMANDE.doubleValue())))
            .andExpect(jsonPath("$.[*].dateLivraison").value(hasItem(DEFAULT_DATE_LIVRAISON.toString())));
    }
    
    @Test
    @Transactional
    public void getCommande() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get the commande
        restCommandeMockMvc.perform(get("/api/commandes/{id}", commande.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commande.getId().intValue()))
            .andExpect(jsonPath("$.codeCommande").value(DEFAULT_CODE_COMMANDE))
            .andExpect(jsonPath("$.dateCommande").value(DEFAULT_DATE_COMMANDE.toString()))
            .andExpect(jsonPath("$.libelleCommande").value(DEFAULT_LIBELLE_COMMANDE))
            .andExpect(jsonPath("$.quantiteCommande").value(DEFAULT_QUANTITE_COMMANDE.doubleValue()))
            .andExpect(jsonPath("$.statusCommande").value(DEFAULT_STATUS_COMMANDE.booleanValue()))
            .andExpect(jsonPath("$.montantVerseCommande").value(DEFAULT_MONTANT_VERSE_COMMANDE.doubleValue()))
            .andExpect(jsonPath("$.dateLivraison").value(DEFAULT_DATE_LIVRAISON.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCommande() throws Exception {
        // Get the commande
        restCommandeMockMvc.perform(get("/api/commandes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommande() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        int databaseSizeBeforeUpdate = commandeRepository.findAll().size();

        // Update the commande
        Commande updatedCommande = commandeRepository.findById(commande.getId()).get();
        // Disconnect from session so that the updates on updatedCommande are not directly saved in db
        em.detach(updatedCommande);
        updatedCommande
            .codeCommande(UPDATED_CODE_COMMANDE)
            .dateCommande(UPDATED_DATE_COMMANDE)
            .libelleCommande(UPDATED_LIBELLE_COMMANDE)
            .quantiteCommande(UPDATED_QUANTITE_COMMANDE)
            .statusCommande(UPDATED_STATUS_COMMANDE)
            .montantVerseCommande(UPDATED_MONTANT_VERSE_COMMANDE)
            .dateLivraison(UPDATED_DATE_LIVRAISON);

        restCommandeMockMvc.perform(put("/api/commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommande)))
            .andExpect(status().isOk());

        // Validate the Commande in the database
        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeUpdate);
        Commande testCommande = commandeList.get(commandeList.size() - 1);
        assertThat(testCommande.getCodeCommande()).isEqualTo(UPDATED_CODE_COMMANDE);
        assertThat(testCommande.getDateCommande()).isEqualTo(UPDATED_DATE_COMMANDE);
        assertThat(testCommande.getLibelleCommande()).isEqualTo(UPDATED_LIBELLE_COMMANDE);
        assertThat(testCommande.getQuantiteCommande()).isEqualTo(UPDATED_QUANTITE_COMMANDE);
        assertThat(testCommande.isStatusCommande()).isEqualTo(UPDATED_STATUS_COMMANDE);
        assertThat(testCommande.getMontantVerseCommande()).isEqualTo(UPDATED_MONTANT_VERSE_COMMANDE);
        assertThat(testCommande.getDateLivraison()).isEqualTo(UPDATED_DATE_LIVRAISON);
    }

    @Test
    @Transactional
    public void updateNonExistingCommande() throws Exception {
        int databaseSizeBeforeUpdate = commandeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommandeMockMvc.perform(put("/api/commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commande)))
            .andExpect(status().isBadRequest());

        // Validate the Commande in the database
        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommande() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        int databaseSizeBeforeDelete = commandeRepository.findAll().size();

        // Delete the commande
        restCommandeMockMvc.perform(delete("/api/commandes/{id}", commande.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
