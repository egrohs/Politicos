package org.politicos.service.impl;

import org.politicos.service.PoliticService;
import org.politicos.domain.Politic;
import org.politicos.repository.PoliticRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Politic.
 */
@Service
public class PoliticServiceImpl implements PoliticService{

    private final Logger log = LoggerFactory.getLogger(PoliticServiceImpl.class);
    
    @Inject
    private PoliticRepository politicRepository;

    /**
     * Save a politic.
     *
     * @param politic the entity to save
     * @return the persisted entity
     */
    public Politic save(Politic politic) {
        log.debug("Request to save Politic : {}", politic);
        Politic result = politicRepository.save(politic);
        return result;
    }

    /**
     *  Get all the politics.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Politic> findAll(Pageable pageable) {
        log.debug("Request to get all Politics");
        Page<Politic> result = politicRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one politic by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Politic findOne(String id) {
        log.debug("Request to get Politic : {}", id);
        Politic politic = politicRepository.findOne(id);
        return politic;
    }

    /**
     *  Delete the  politic by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Politic : {}", id);
        politicRepository.delete(id);
    }
}
