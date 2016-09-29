package org.politicos.repository;

import org.politicos.domain.Politico;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Politico entity.
 */
@SuppressWarnings("unused")
public interface PoliticoRepository extends JpaRepository<Politico,Long> {

}
