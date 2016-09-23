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
import java.time.LocalDate;
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

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_CODENAMES = "AAAAA";
    private static final String UPDATED_CODENAMES = "BBBBB";
    private static final String DEFAULT_STATE = "AAAAA";
    private static final String UPDATED_STATE = "BBBBB";
    private static final String DEFAULT_CITY = "AAAAA";
    private static final String UPDATED_CITY = "BBBBB";

    private static final LocalDate DEFAULT_BORN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BORN = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_PHOTO = "AAAAA";
    private static final String UPDATED_PHOTO = "BBBBB";
    private static final String DEFAULT_PARTIES = "AAAAA";
    private static final String UPDATED_PARTIES = "BBBBB";
    private static final String DEFAULT_POSITIONS = "AAAAA";
    private static final String UPDATED_POSITIONS = "BBBBB";
    private static final String DEFAULT_URLS = "AAAAA";
    private static final String UPDATED_URLS = "BBBBB";
    private static final String DEFAULT_LEGISLATURES = "AAAAA";
    private static final String UPDATED_LEGISLATURES = "BBBBB";
    private static final String DEFAULT_HISTORY = "AAAAA";
    private static final String UPDATED_HISTORY = "BBBBB";

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
                .name(DEFAULT_NAME)
                .codenames(DEFAULT_CODENAMES)
                .state(DEFAULT_STATE)
                .city(DEFAULT_CITY)
                .born(DEFAULT_BORN)
                .email(DEFAULT_EMAIL)
                .photo(DEFAULT_PHOTO)
                .parties(DEFAULT_PARTIES)
                .positions(DEFAULT_POSITIONS)
                .urls(DEFAULT_URLS)
                .legislatures(DEFAULT_LEGISLATURES)
                .history(DEFAULT_HISTORY)
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
        assertThat(testPolitic.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPolitic.getCodenames()).isEqualTo(DEFAULT_CODENAMES);
        assertThat(testPolitic.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testPolitic.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testPolitic.getBorn()).isEqualTo(DEFAULT_BORN);
        assertThat(testPolitic.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPolitic.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testPolitic.getParties()).isEqualTo(DEFAULT_PARTIES);
        assertThat(testPolitic.getPositions()).isEqualTo(DEFAULT_POSITIONS);
        assertThat(testPolitic.getUrls()).isEqualTo(DEFAULT_URLS);
        assertThat(testPolitic.getLegislatures()).isEqualTo(DEFAULT_LEGISLATURES);
        assertThat(testPolitic.getHistory()).isEqualTo(DEFAULT_HISTORY);
        assertThat(testPolitic.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testPolitic.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = politicRepository.findAll().size();
        // set the field null
        politic.setName(null);

        // Create the Politic, which fails.

        restPoliticMockMvc.perform(post("/api/politics")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(politic)))
                .andExpect(status().isBadRequest());

        List<Politic> politics = politicRepository.findAll();
        assertThat(politics).hasSize(databaseSizeBeforeTest);
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
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].codenames").value(hasItem(DEFAULT_CODENAMES.toString())))
                .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
                .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
                .andExpect(jsonPath("$.[*].born").value(hasItem(DEFAULT_BORN.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO.toString())))
                .andExpect(jsonPath("$.[*].parties").value(hasItem(DEFAULT_PARTIES.toString())))
                .andExpect(jsonPath("$.[*].positions").value(hasItem(DEFAULT_POSITIONS.toString())))
                .andExpect(jsonPath("$.[*].urls").value(hasItem(DEFAULT_URLS.toString())))
                .andExpect(jsonPath("$.[*].legislatures").value(hasItem(DEFAULT_LEGISLATURES.toString())))
                .andExpect(jsonPath("$.[*].history").value(hasItem(DEFAULT_HISTORY.toString())))
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
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.codenames").value(DEFAULT_CODENAMES.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.born").value(DEFAULT_BORN.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO.toString()))
            .andExpect(jsonPath("$.parties").value(DEFAULT_PARTIES.toString()))
            .andExpect(jsonPath("$.positions").value(DEFAULT_POSITIONS.toString()))
            .andExpect(jsonPath("$.urls").value(DEFAULT_URLS.toString()))
            .andExpect(jsonPath("$.legislatures").value(DEFAULT_LEGISLATURES.toString()))
            .andExpect(jsonPath("$.history").value(DEFAULT_HISTORY.toString()))
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
                .name(UPDATED_NAME)
                .codenames(UPDATED_CODENAMES)
                .state(UPDATED_STATE)
                .city(UPDATED_CITY)
                .born(UPDATED_BORN)
                .email(UPDATED_EMAIL)
                .photo(UPDATED_PHOTO)
                .parties(UPDATED_PARTIES)
                .positions(UPDATED_POSITIONS)
                .urls(UPDATED_URLS)
                .legislatures(UPDATED_LEGISLATURES)
                .history(UPDATED_HISTORY)
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
        assertThat(testPolitic.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPolitic.getCodenames()).isEqualTo(UPDATED_CODENAMES);
        assertThat(testPolitic.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testPolitic.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testPolitic.getBorn()).isEqualTo(UPDATED_BORN);
        assertThat(testPolitic.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPolitic.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testPolitic.getParties()).isEqualTo(UPDATED_PARTIES);
        assertThat(testPolitic.getPositions()).isEqualTo(UPDATED_POSITIONS);
        assertThat(testPolitic.getUrls()).isEqualTo(UPDATED_URLS);
        assertThat(testPolitic.getLegislatures()).isEqualTo(UPDATED_LEGISLATURES);
        assertThat(testPolitic.getHistory()).isEqualTo(UPDATED_HISTORY);
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
