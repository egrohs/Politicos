package org.politicos.web.rest;

import org.politicos.SiteApp;

import org.politicos.domain.Politico;
import org.politicos.repository.PoliticoRepository;
import org.politicos.service.PoliticoService;
import org.politicos.repository.search.PoliticoSearchRepository;

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
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PoliticoResource REST controller.
 *
 * @see PoliticoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SiteApp.class)
public class PoliticoResourceIntTest {

    private static final String DEFAULT_CAMARA_PK = "AAAAA";
    private static final String UPDATED_CAMARA_PK = "BBBBB";
    private static final String DEFAULT_SENADO_ID = "AAAAA";
    private static final String UPDATED_SENADO_ID = "BBBBB";
    private static final String DEFAULT_NOME = "AAAAA";
    private static final String UPDATED_NOME = "BBBBB";
    private static final String DEFAULT_CODINOMES = "AAAAA";
    private static final String UPDATED_CODINOMES = "BBBBB";
    private static final String DEFAULT_UF = "AAAAA";
    private static final String UPDATED_UF = "BBBBB";
    private static final String DEFAULT_PARTIDO_ATUAL = "AAAAA";
    private static final String UPDATED_PARTIDO_ATUAL = "BBBBB";
    private static final String DEFAULT_OUTROS_PARTIDOS = "AAAAA";
    private static final String UPDATED_OUTROS_PARTIDOS = "BBBBB";
    private static final String DEFAULT_PROFISSOES = "AAAAA";
    private static final String UPDATED_PROFISSOES = "BBBBB";
    private static final String DEFAULT_CARGO = "AAAAA";
    private static final String UPDATED_CARGO = "BBBBB";
    private static final String DEFAULT_LEGISLATURAS = "AAAAA";
    private static final String UPDATED_LEGISLATURAS = "BBBBB";
    private static final String DEFAULT_FOTO = "AAAAA";
    private static final String UPDATED_FOTO = "BBBBB";
    private static final String DEFAULT_URLS = "AAAAA";
    private static final String UPDATED_URLS = "BBBBB";

    @Inject
    private PoliticoRepository politicoRepository;

    @Inject
    private PoliticoService politicoService;

    @Inject
    private PoliticoSearchRepository politicoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPoliticoMockMvc;

    private Politico politico;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PoliticoResource politicoResource = new PoliticoResource();
        ReflectionTestUtils.setField(politicoResource, "politicoService", politicoService);
        this.restPoliticoMockMvc = MockMvcBuilders.standaloneSetup(politicoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Politico createEntity(EntityManager em) {
        Politico politico = new Politico()
                .camaraPk(DEFAULT_CAMARA_PK)
                .senadoId(DEFAULT_SENADO_ID)
                .nome(DEFAULT_NOME)
                .codinomes(DEFAULT_CODINOMES)
                .uf(DEFAULT_UF)
                .partidoAtual(DEFAULT_PARTIDO_ATUAL)
                .outrosPartidos(DEFAULT_OUTROS_PARTIDOS)
                .profissoes(DEFAULT_PROFISSOES)
                .cargo(DEFAULT_CARGO)
                .legislaturas(DEFAULT_LEGISLATURAS)
                .foto(DEFAULT_FOTO)
                .urls(DEFAULT_URLS);
        return politico;
    }

    @Before
    public void initTest() {
        politicoSearchRepository.deleteAll();
        politico = createEntity(em);
    }

    @Test
    @Transactional
    public void createPolitico() throws Exception {
        int databaseSizeBeforeCreate = politicoRepository.findAll().size();

        // Create the Politico

        restPoliticoMockMvc.perform(post("/api/politicos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(politico)))
                .andExpect(status().isCreated());

        // Validate the Politico in the database
        List<Politico> politicos = politicoRepository.findAll();
        assertThat(politicos).hasSize(databaseSizeBeforeCreate + 1);
        Politico testPolitico = politicos.get(politicos.size() - 1);
        assertThat(testPolitico.getCamaraPk()).isEqualTo(DEFAULT_CAMARA_PK);
        assertThat(testPolitico.getSenadoId()).isEqualTo(DEFAULT_SENADO_ID);
        assertThat(testPolitico.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPolitico.getCodinomes()).isEqualTo(DEFAULT_CODINOMES);
        assertThat(testPolitico.getUf()).isEqualTo(DEFAULT_UF);
        assertThat(testPolitico.getPartidoAtual()).isEqualTo(DEFAULT_PARTIDO_ATUAL);
        assertThat(testPolitico.getOutrosPartidos()).isEqualTo(DEFAULT_OUTROS_PARTIDOS);
        assertThat(testPolitico.getProfissoes()).isEqualTo(DEFAULT_PROFISSOES);
        assertThat(testPolitico.getCargo()).isEqualTo(DEFAULT_CARGO);
        assertThat(testPolitico.getLegislaturas()).isEqualTo(DEFAULT_LEGISLATURAS);
        assertThat(testPolitico.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testPolitico.getUrls()).isEqualTo(DEFAULT_URLS);

        // Validate the Politico in ElasticSearch
        Politico politicoEs = politicoSearchRepository.findOne(testPolitico.getId());
        assertThat(politicoEs).isEqualToComparingFieldByField(testPolitico);
    }

    @Test
    @Transactional
    public void getAllPoliticos() throws Exception {
        // Initialize the database
        politicoRepository.saveAndFlush(politico);

        // Get all the politicos
        restPoliticoMockMvc.perform(get("/api/politicos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(politico.getId().intValue())))
                .andExpect(jsonPath("$.[*].camaraPk").value(hasItem(DEFAULT_CAMARA_PK.toString())))
                .andExpect(jsonPath("$.[*].senadoId").value(hasItem(DEFAULT_SENADO_ID.toString())))
                .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
                .andExpect(jsonPath("$.[*].codinomes").value(hasItem(DEFAULT_CODINOMES.toString())))
                .andExpect(jsonPath("$.[*].uf").value(hasItem(DEFAULT_UF.toString())))
                .andExpect(jsonPath("$.[*].partidoAtual").value(hasItem(DEFAULT_PARTIDO_ATUAL.toString())))
                .andExpect(jsonPath("$.[*].outrosPartidos").value(hasItem(DEFAULT_OUTROS_PARTIDOS.toString())))
                .andExpect(jsonPath("$.[*].profissoes").value(hasItem(DEFAULT_PROFISSOES.toString())))
                .andExpect(jsonPath("$.[*].cargo").value(hasItem(DEFAULT_CARGO.toString())))
                .andExpect(jsonPath("$.[*].legislaturas").value(hasItem(DEFAULT_LEGISLATURAS.toString())))
                .andExpect(jsonPath("$.[*].foto").value(hasItem(DEFAULT_FOTO.toString())))
                .andExpect(jsonPath("$.[*].urls").value(hasItem(DEFAULT_URLS.toString())));
    }

    @Test
    @Transactional
    public void getPolitico() throws Exception {
        // Initialize the database
        politicoRepository.saveAndFlush(politico);

        // Get the politico
        restPoliticoMockMvc.perform(get("/api/politicos/{id}", politico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(politico.getId().intValue()))
            .andExpect(jsonPath("$.camaraPk").value(DEFAULT_CAMARA_PK.toString()))
            .andExpect(jsonPath("$.senadoId").value(DEFAULT_SENADO_ID.toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.codinomes").value(DEFAULT_CODINOMES.toString()))
            .andExpect(jsonPath("$.uf").value(DEFAULT_UF.toString()))
            .andExpect(jsonPath("$.partidoAtual").value(DEFAULT_PARTIDO_ATUAL.toString()))
            .andExpect(jsonPath("$.outrosPartidos").value(DEFAULT_OUTROS_PARTIDOS.toString()))
            .andExpect(jsonPath("$.profissoes").value(DEFAULT_PROFISSOES.toString()))
            .andExpect(jsonPath("$.cargo").value(DEFAULT_CARGO.toString()))
            .andExpect(jsonPath("$.legislaturas").value(DEFAULT_LEGISLATURAS.toString()))
            .andExpect(jsonPath("$.foto").value(DEFAULT_FOTO.toString()))
            .andExpect(jsonPath("$.urls").value(DEFAULT_URLS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPolitico() throws Exception {
        // Get the politico
        restPoliticoMockMvc.perform(get("/api/politicos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePolitico() throws Exception {
        // Initialize the database
        politicoService.save(politico);

        int databaseSizeBeforeUpdate = politicoRepository.findAll().size();

        // Update the politico
        Politico updatedPolitico = politicoRepository.findOne(politico.getId());
        updatedPolitico
                .camaraPk(UPDATED_CAMARA_PK)
                .senadoId(UPDATED_SENADO_ID)
                .nome(UPDATED_NOME)
                .codinomes(UPDATED_CODINOMES)
                .uf(UPDATED_UF)
                .partidoAtual(UPDATED_PARTIDO_ATUAL)
                .outrosPartidos(UPDATED_OUTROS_PARTIDOS)
                .profissoes(UPDATED_PROFISSOES)
                .cargo(UPDATED_CARGO)
                .legislaturas(UPDATED_LEGISLATURAS)
                .foto(UPDATED_FOTO)
                .urls(UPDATED_URLS);

        restPoliticoMockMvc.perform(put("/api/politicos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPolitico)))
                .andExpect(status().isOk());

        // Validate the Politico in the database
        List<Politico> politicos = politicoRepository.findAll();
        assertThat(politicos).hasSize(databaseSizeBeforeUpdate);
        Politico testPolitico = politicos.get(politicos.size() - 1);
        assertThat(testPolitico.getCamaraPk()).isEqualTo(UPDATED_CAMARA_PK);
        assertThat(testPolitico.getSenadoId()).isEqualTo(UPDATED_SENADO_ID);
        assertThat(testPolitico.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPolitico.getCodinomes()).isEqualTo(UPDATED_CODINOMES);
        assertThat(testPolitico.getUf()).isEqualTo(UPDATED_UF);
        assertThat(testPolitico.getPartidoAtual()).isEqualTo(UPDATED_PARTIDO_ATUAL);
        assertThat(testPolitico.getOutrosPartidos()).isEqualTo(UPDATED_OUTROS_PARTIDOS);
        assertThat(testPolitico.getProfissoes()).isEqualTo(UPDATED_PROFISSOES);
        assertThat(testPolitico.getCargo()).isEqualTo(UPDATED_CARGO);
        assertThat(testPolitico.getLegislaturas()).isEqualTo(UPDATED_LEGISLATURAS);
        assertThat(testPolitico.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testPolitico.getUrls()).isEqualTo(UPDATED_URLS);

        // Validate the Politico in ElasticSearch
        Politico politicoEs = politicoSearchRepository.findOne(testPolitico.getId());
        assertThat(politicoEs).isEqualToComparingFieldByField(testPolitico);
    }

    @Test
    @Transactional
    public void deletePolitico() throws Exception {
        // Initialize the database
        politicoService.save(politico);

        int databaseSizeBeforeDelete = politicoRepository.findAll().size();

        // Get the politico
        restPoliticoMockMvc.perform(delete("/api/politicos/{id}", politico.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean politicoExistsInEs = politicoSearchRepository.exists(politico.getId());
        assertThat(politicoExistsInEs).isFalse();

        // Validate the database is empty
        List<Politico> politicos = politicoRepository.findAll();
        assertThat(politicos).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPolitico() throws Exception {
        // Initialize the database
        politicoService.save(politico);

        // Search the politico
        restPoliticoMockMvc.perform(get("/api/_search/politicos?query=id:" + politico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(politico.getId().intValue())))
            .andExpect(jsonPath("$.[*].camaraPk").value(hasItem(DEFAULT_CAMARA_PK.toString())))
            .andExpect(jsonPath("$.[*].senadoId").value(hasItem(DEFAULT_SENADO_ID.toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].codinomes").value(hasItem(DEFAULT_CODINOMES.toString())))
            .andExpect(jsonPath("$.[*].uf").value(hasItem(DEFAULT_UF.toString())))
            .andExpect(jsonPath("$.[*].partidoAtual").value(hasItem(DEFAULT_PARTIDO_ATUAL.toString())))
            .andExpect(jsonPath("$.[*].outrosPartidos").value(hasItem(DEFAULT_OUTROS_PARTIDOS.toString())))
            .andExpect(jsonPath("$.[*].profissoes").value(hasItem(DEFAULT_PROFISSOES.toString())))
            .andExpect(jsonPath("$.[*].cargo").value(hasItem(DEFAULT_CARGO.toString())))
            .andExpect(jsonPath("$.[*].legislaturas").value(hasItem(DEFAULT_LEGISLATURAS.toString())))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(DEFAULT_FOTO.toString())))
            .andExpect(jsonPath("$.[*].urls").value(hasItem(DEFAULT_URLS.toString())));
    }
}
