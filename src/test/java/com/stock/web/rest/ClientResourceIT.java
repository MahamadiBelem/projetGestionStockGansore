package com.stock.web.rest;

import com.stock.GestionStockApp;
import com.stock.domain.Client;
import com.stock.repository.ClientRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ClientResource} REST controller.
 */
@SpringBootTest(classes = GestionStockApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ClientResourceIT {

    private static final String DEFAULT_NUMERO_CNIB_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CNIB_CLIENT = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CLIENT = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_CLIENT = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE_1_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_1_CLIENT = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE_2_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_2_CLIENT = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_VILLE_CLIENT = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_CLIENT = "BBBBBBBBBB";

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientMockMvc;

    private Client client;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createEntity(EntityManager em) {
        Client client = new Client()
            .numeroCnibClient(DEFAULT_NUMERO_CNIB_CLIENT)
            .nomClient(DEFAULT_NOM_CLIENT)
            .prenomClient(DEFAULT_PRENOM_CLIENT)
            .telephone1Client(DEFAULT_TELEPHONE_1_CLIENT)
            .telephone2Client(DEFAULT_TELEPHONE_2_CLIENT)
            .villeClient(DEFAULT_VILLE_CLIENT)
            .emailClient(DEFAULT_EMAIL_CLIENT);
        return client;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createUpdatedEntity(EntityManager em) {
        Client client = new Client()
            .numeroCnibClient(UPDATED_NUMERO_CNIB_CLIENT)
            .nomClient(UPDATED_NOM_CLIENT)
            .prenomClient(UPDATED_PRENOM_CLIENT)
            .telephone1Client(UPDATED_TELEPHONE_1_CLIENT)
            .telephone2Client(UPDATED_TELEPHONE_2_CLIENT)
            .villeClient(UPDATED_VILLE_CLIENT)
            .emailClient(UPDATED_EMAIL_CLIENT);
        return client;
    }

    @BeforeEach
    public void initTest() {
        client = createEntity(em);
    }

    @Test
    @Transactional
    public void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();
        // Create the Client
        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getNumeroCnibClient()).isEqualTo(DEFAULT_NUMERO_CNIB_CLIENT);
        assertThat(testClient.getNomClient()).isEqualTo(DEFAULT_NOM_CLIENT);
        assertThat(testClient.getPrenomClient()).isEqualTo(DEFAULT_PRENOM_CLIENT);
        assertThat(testClient.getTelephone1Client()).isEqualTo(DEFAULT_TELEPHONE_1_CLIENT);
        assertThat(testClient.getTelephone2Client()).isEqualTo(DEFAULT_TELEPHONE_2_CLIENT);
        assertThat(testClient.getVilleClient()).isEqualTo(DEFAULT_VILLE_CLIENT);
        assertThat(testClient.getEmailClient()).isEqualTo(DEFAULT_EMAIL_CLIENT);
    }

    @Test
    @Transactional
    public void createClientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // Create the Client with an existing ID
        client.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllClients() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList
        restClientMockMvc.perform(get("/api/clients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroCnibClient").value(hasItem(DEFAULT_NUMERO_CNIB_CLIENT)))
            .andExpect(jsonPath("$.[*].nomClient").value(hasItem(DEFAULT_NOM_CLIENT)))
            .andExpect(jsonPath("$.[*].prenomClient").value(hasItem(DEFAULT_PRENOM_CLIENT)))
            .andExpect(jsonPath("$.[*].telephone1Client").value(hasItem(DEFAULT_TELEPHONE_1_CLIENT)))
            .andExpect(jsonPath("$.[*].telephone2Client").value(hasItem(DEFAULT_TELEPHONE_2_CLIENT)))
            .andExpect(jsonPath("$.[*].villeClient").value(hasItem(DEFAULT_VILLE_CLIENT)))
            .andExpect(jsonPath("$.[*].emailClient").value(hasItem(DEFAULT_EMAIL_CLIENT)));
    }
    
    @Test
    @Transactional
    public void getClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", client.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(client.getId().intValue()))
            .andExpect(jsonPath("$.numeroCnibClient").value(DEFAULT_NUMERO_CNIB_CLIENT))
            .andExpect(jsonPath("$.nomClient").value(DEFAULT_NOM_CLIENT))
            .andExpect(jsonPath("$.prenomClient").value(DEFAULT_PRENOM_CLIENT))
            .andExpect(jsonPath("$.telephone1Client").value(DEFAULT_TELEPHONE_1_CLIENT))
            .andExpect(jsonPath("$.telephone2Client").value(DEFAULT_TELEPHONE_2_CLIENT))
            .andExpect(jsonPath("$.villeClient").value(DEFAULT_VILLE_CLIENT))
            .andExpect(jsonPath("$.emailClient").value(DEFAULT_EMAIL_CLIENT));
    }
    @Test
    @Transactional
    public void getNonExistingClient() throws Exception {
        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client
        Client updatedClient = clientRepository.findById(client.getId()).get();
        // Disconnect from session so that the updates on updatedClient are not directly saved in db
        em.detach(updatedClient);
        updatedClient
            .numeroCnibClient(UPDATED_NUMERO_CNIB_CLIENT)
            .nomClient(UPDATED_NOM_CLIENT)
            .prenomClient(UPDATED_PRENOM_CLIENT)
            .telephone1Client(UPDATED_TELEPHONE_1_CLIENT)
            .telephone2Client(UPDATED_TELEPHONE_2_CLIENT)
            .villeClient(UPDATED_VILLE_CLIENT)
            .emailClient(UPDATED_EMAIL_CLIENT);

        restClientMockMvc.perform(put("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedClient)))
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getNumeroCnibClient()).isEqualTo(UPDATED_NUMERO_CNIB_CLIENT);
        assertThat(testClient.getNomClient()).isEqualTo(UPDATED_NOM_CLIENT);
        assertThat(testClient.getPrenomClient()).isEqualTo(UPDATED_PRENOM_CLIENT);
        assertThat(testClient.getTelephone1Client()).isEqualTo(UPDATED_TELEPHONE_1_CLIENT);
        assertThat(testClient.getTelephone2Client()).isEqualTo(UPDATED_TELEPHONE_2_CLIENT);
        assertThat(testClient.getVilleClient()).isEqualTo(UPDATED_VILLE_CLIENT);
        assertThat(testClient.getEmailClient()).isEqualTo(UPDATED_EMAIL_CLIENT);
    }

    @Test
    @Transactional
    public void updateNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc.perform(put("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeDelete = clientRepository.findAll().size();

        // Delete the client
        restClientMockMvc.perform(delete("/api/clients/{id}", client.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
