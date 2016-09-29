package org.politicos.web.rest;

import org.politicos.PoliticosApp;

import org.politicos.domain.Politic;
import org.politicos.repository.PoliticRepository;
import org.politicos.service.PoliticService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PoliticResource REST controller.
 *
 * @see PoliticResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PoliticosApp.class)
public class PoliticResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));


    private static final Long DEFAULT_CAMARAPK = 1L;
    private static final Long UPDATED_CAMARAPK = 2L;
    private static final String DEFAULT_NOME = "AAAAA";
    private static final String UPDATED_NOME = "BBBBB";
    private static final String DEFAULT_CODINOMES = "AAAAA";
    private static final String UPDATED_CODINOMES = "BBBBB";
    private static final String DEFAULT_FOTO = "AAAAA";
    private static final String UPDATED_FOTO = "BBBBB";
    private static final String DEFAULT_URLS = "AAAAA";
    private static final String UPDATED_URLS = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATED_STR = dateTimeFormatter.format(DEFAULT_CREATED);

    private static final ZonedDateTime DEFAULT_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_UPDATED_STR = dateTimeFormatter.format(DEFAULT_UPDATED);

    @Inject
    private PoliticRepository politicRepository;

    @Inject
    private PoliticService politicService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPoliticMockMvc;

    private Politic politic;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PoliticResource politicResource = new PoliticResource();
        ReflectionTestUtils.setField(politicResource, "politicService", politicService);
        this.restPoliticMockMvc = MockMvcBuilders.standaloneSetup(politicResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Politic createEntity() {
        Politic politic = new Politic()
                .camarapk(DEFAULT_CAMARAPK)
                .nome(DEFAULT_NOME)
                .codinomes(DEFAULT_CODINOMES)
                .foto(DEFAULT_FOTO)
                .urls(DEFAULT_URLS)
                .created(DEFAULT_CREATED)
                .updated(DEFAULT_UPDATED);
        return politic;
    }

    @Before
    public void initTest() {
        politicRepository.deleteAll();
        politic = createEntity();
    }

    @Test
    public void createPolitic() throws Exception {
        int databaseSizeBeforeCreate = politicRepository.findAll().size();

        // Create the Politic

        restPoliticMockMvc.perform(post("/api/politics")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(politic)))
                .andExpect(status().isCreated());

        // Validate the Politic in the database
        List<Politic> politics = politicRepository.findAll();
        assertThat(politics).hasSize(databaseSizeBeforeCreate + 1);
        Politic testPolitic = politics.get(politics.size() - 1);
        assertThat(testPolitic.getCamarapk()).isEqualTo(DEFAULT_CAMARAPK);
        assertThat(testPolitic.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPolitic.getCodinomes()).isEqualTo(DEFAULT_CODINOMES);
        assertThat(testPolitic.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testPolitic.getUrls()).isEqualTo(DEFAULT_URLS);
        assertThat(testPolitic.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testPolitic.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    public void getAllPolitics() throws Exception {
        // Initialize the database
        politicRepository.save(politic);

        // Get all the politics
        restPoliticMockMvc.perform(get("/api/politics?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(politic.getId())))
                .andExpect(jsonPath("$.[*].camarapk").value(hasItem(DEFAULT_CAMARAPK.intValue())))
                .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
                .andExpect(jsonPath("$.[*].codinomes").value(hasItem(DEFAULT_CODINOMES.toString())))
                .andExpect(jsonPath("$.[*].foto").value(hasItem(DEFAULT_FOTO.toString())))
                .andExpect(jsonPath("$.[*].urls").value(hasItem(DEFAULT_URLS.toString())))
                .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED_STR)))
                .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED_STR)));
    }

    @Test
    public void getPolitic() throws Exception {
        // Initialize the database
        politicRepository.save(politic);

        // Get the politic
        restPoliticMockMvc.perform(get("/api/politics/{id}", politic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(politic.getId()))
            .andExpect(jsonPath("$.camarapk").value(DEFAULT_CAMARAPK.intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.codinomes").value(DEFAULT_CODINOMES.toString()))
            .andExpect(jsonPath("$.foto").value(DEFAULT_FOTO.toString()))
            .andExpect(jsonPath("$.urls").value(DEFAULT_URLS.toString()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED_STR))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED_STR));
    }

    @Test
    public void getNonExistingPolitic() throws Exception {
        // Get the politic
        restPoliticMockMvc.perform(get("/api/politics/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updatePolitic() throws Exception {
        // Initialize the database
        politicService.save(politic);

        int databaseSizeBeforeUpdate = politicRepository.findAll().size();

        // Update the politic
        Politic updatedPolitic = politicRepository.findOne(politic.getId());
        updatedPolitic
                .camarapk(UPDATED_CAMARAPK)
                .nome(UPDATED_NOME)
                .codinomes(UPDATED_CODINOMES)
                .foto(UPDATED_FOTO)
                .urls(UPDATED_URLS)
                .created(UPDATED_CREATED)
                .updated(UPDATED_UPDATED);

        restPoliticMockMvc.perform(put("/api/politics")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPolitic)))
                .andExpect(status().isOk());

        // Validate the Politic in the database
        List<Politic> politics = politicRepository.findAll();
        assertThat(politics).hasSize(databaseSizeBeforeUpdate);
        Politic testPolitic = politics.get(politics.size() - 1);
        assertThat(testPolitic.getCamarapk()).isEqualTo(UPDATED_CAMARAPK);
        assertThat(testPolitic.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPolitic.getCodinomes()).isEqualTo(UPDATED_CODINOMES);
        assertThat(testPolitic.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testPolitic.getUrls()).isEqualTo(UPDATED_URLS);
        assertThat(testPolitic.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testPolitic.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    public void deletePolitic() throws Exception {
        // Initialize the database
        politicService.save(politic);

        int databaseSizeBeforeDelete = politicRepository.findAll().size();

        // Get the politic
        restPoliticMockMvc.perform(delete("/api/politics/{id}", politic.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Politic> politics = politicRepository.findAll();
        assertThat(politics).hasSize(databaseSizeBeforeDelete - 1);
    }
}
