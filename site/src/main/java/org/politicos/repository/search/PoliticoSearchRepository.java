package org.politicos.repository.search;

import org.politicos.domain.Politico;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Politico entity.
 */
public interface PoliticoSearchRepository extends ElasticsearchRepository<Politico, Long> {
}
