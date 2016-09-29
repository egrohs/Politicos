package org.politicos.service.impl;

import org.politicos.service.PoliticoService;
import org.politicos.domain.Politico;
import org.politicos.repository.PoliticoRepository;
import org.politicos.repository.search.PoliticoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Politico.
 */
@Service
@Transactional
public class PoliticoServiceImpl implements PoliticoService{

    private final Logger log = LoggerFactory.getLogger(PoliticoServiceImpl.class);
    
    @Inject
    private PoliticoRepository politicoRepository;

    @Inject
    private PoliticoSearchRepository politicoSearchRepository;

    /**
     * Save a politico.
     *
     * @param politico the entity to save
     * @return the persisted entity
     */
    public Politico save(Politico politico) {
        log.debug("Request to save Politico : {}", politico);
        Politico result = politicoRepository.save(politico);
        politicoSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the politicos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Politico> findAll(Pageable pageable) {
        log.debug("Request to get all Politicos");
        Page<Politico> result = politicoRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one politico by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Politico findOne(Long id) {
        log.debug("Request to get Politico : {}", id);
        Politico politico = politicoRepository.findOne(id);
        return politico;
    }

    /**
     *  Delete the  politico by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Politico : {}", id);
        politicoRepository.delete(id);
        politicoSearchRepository.delete(id);
    }

    /**
     * Search for the politico corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Politico> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Politicos for query {}", query);
        Page<Politico> result = politicoSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
