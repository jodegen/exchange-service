package de.jodegen.exchange.repository;

import de.jodegen.exchange.model.CurrencyReference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyReferenceRepository extends JpaRepository<CurrencyReference, Long> {

    boolean existsByCode(String code);
}
