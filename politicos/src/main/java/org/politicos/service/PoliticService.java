package org.politicos.service;

import org.politicos.domain.Politic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Politic.
 */
public interface PoliticService {

    /**
     * Save a politic.
     *
     * @param politic the entity to save
     * @return the persisted entity
     */
    Politic save(Politic politic);

    /**
     *  Get all the politics.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Politic> findAll(Pageable pageable);

    /**
     *  Get the "id" politic.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Politic findOne(String id);

    /**
     *  Delete the "id" politic.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
