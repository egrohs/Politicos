package org.politicos.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.politicos.domain.Politic;
import org.politicos.service.PoliticService;
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

/**
 * REST controller for managing Politic.
 */
@RestController
@RequestMapping("/api")
public class PoliticResource {

    private final Logger log = LoggerFactory.getLogger(PoliticResource.class);
        
    @Inject
    private PoliticService politicService;

    /**
     * POST  /politics : Create a new politic.
     *
     * @param politic the politic to create
     * @return the ResponseEntity with status 201 (Created) and with body the new politic, or with status 400 (Bad Request) if the politic has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/politics",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Politic> createPolitic(@RequestBody Politic politic) throws URISyntaxException {
        log.debug("REST request to save Politic : {}", politic);
        if (politic.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("politic", "idexists", "A new politic cannot already have an ID")).body(null);
        }
        Politic result = politicService.save(politic);
        return ResponseEntity.created(new URI("/api/politics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("politic", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /politics : Updates an existing politic.
     *
     * @param politic the politic to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated politic,
     * or with status 400 (Bad Request) if the politic is not valid,
     * or with status 500 (Internal Server Error) if the politic couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/politics",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Politic> updatePolitic(@RequestBody Politic politic) throws URISyntaxException {
        log.debug("REST request to update Politic : {}", politic);
        if (politic.getId() == null) {
            return createPolitic(politic);
        }
        Politic result = politicService.save(politic);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("politic", politic.getId().toString()))
            .body(result);
    }

    /**
     * GET  /politics : get all the politics.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of politics in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/politics",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Politic>> getAllPolitics(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Politics");
        Page<Politic> page = politicService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/politics");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /politics/:id : get the "id" politic.
     *
     * @param id the id of the politic to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the politic, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/politics/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Politic> getPolitic(@PathVariable String id) {
        log.debug("REST request to get Politic : {}", id);
        Politic politic = politicService.findOne(id);
        return Optional.ofNullable(politic)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /politics/:id : delete the "id" politic.
     *
     * @param id the id of the politic to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/politics/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePolitic(@PathVariable String id) {
        log.debug("REST request to delete Politic : {}", id);
        politicService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("politic", id.toString())).build();
    }

}
