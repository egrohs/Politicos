package org.politicos.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.politicos.domain.Politico;
import org.politicos.service.PoliticoService;
import org.politicos.web.rest.util.HeaderUtil;
import org.politicos.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Politico.
 */
@RestController
@RequestMapping("/api")
public class PoliticoResource {

    private final Logger log = LoggerFactory.getLogger(PoliticoResource.class);
        
    @Inject
    private PoliticoService politicoService;

    /**
     * POST  /politicos : Create a new politico.
     *
     * @param politico the politico to create
     * @return the ResponseEntity with status 201 (Created) and with body the new politico, or with status 400 (Bad Request) if the politico has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/politicos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Politico> createPolitico(@RequestBody Politico politico) throws URISyntaxException {
        log.debug("REST request to save Politico : {}", politico);
        if (politico.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("politico", "idexists", "A new politico cannot already have an ID")).body(null);
        }
        Politico result = politicoService.save(politico);
        return ResponseEntity.created(new URI("/api/politicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("politico", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /politicos : Updates an existing politico.
     *
     * @param politico the politico to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated politico,
     * or with status 400 (Bad Request) if the politico is not valid,
     * or with status 500 (Internal Server Error) if the politico couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/politicos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Politico> updatePolitico(@RequestBody Politico politico) throws URISyntaxException {
        log.debug("REST request to update Politico : {}", politico);
        if (politico.getId() == null) {
            return createPolitico(politico);
        }
        Politico result = politicoService.save(politico);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("politico", politico.getId().toString()))
            .body(result);
    }

    /**
     * GET  /politicos : get all the politicos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of politicos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/politicos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Politico>> getAllPoliticos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Politicos");
        Page<Politico> page = politicoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/politicos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /politicos/:id : get the "id" politico.
     *
     * @param id the id of the politico to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the politico, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/politicos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Politico> getPolitico(@PathVariable Long id) {
        log.debug("REST request to get Politico : {}", id);
        Politico politico = politicoService.findOne(id);
        return Optional.ofNullable(politico)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /politicos/:id : delete the "id" politico.
     *
     * @param id the id of the politico to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/politicos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePolitico(@PathVariable Long id) {
        log.debug("REST request to delete Politico : {}", id);
        politicoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("politico", id.toString())).build();
    }

    /**
     * SEARCH  /_search/politicos?query=:query : search for the politico corresponding
     * to the query.
     *
     * @param query the query of the politico search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/_search/politicos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Politico>> searchPoliticos(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Politicos for query {}", query);
        Page<Politico> page = politicoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/politicos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
