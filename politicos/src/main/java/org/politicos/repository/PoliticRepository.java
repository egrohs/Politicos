package org.politicos.repository;

import org.politicos.domain.Politic;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Politic entity.
 */
@SuppressWarnings("unused")
public interface PoliticRepository extends MongoRepository<Politic,String> {

}
