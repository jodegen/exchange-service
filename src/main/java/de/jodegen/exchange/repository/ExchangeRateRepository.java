package de.jodegen.exchange.repository;

import de.jodegen.exchange.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    @Query("""
                SELECT e FROM ExchangeRate e
                WHERE e.timestamp = (
                    SELECT MAX(innerE.timestamp)
                    FROM ExchangeRate innerE
                    WHERE innerE.reference = e.reference
                )
            """)
    List<ExchangeRate> findLatestExchangeRatesForAllCurrencies();

    @Query("""
                SELECT e FROM ExchangeRate e
                WHERE e.reference.code = :currencyCode
                ORDER BY e.timestamp DESC
            """)
    Optional<ExchangeRate> findCurrentExchangeRate(@Param("currencyCode") String currencyCode);

    @Query("""
                SELECT e FROM ExchangeRate e
                WHERE e.reference.code = :currencyCode
                ORDER BY e.timestamp DESC
            """)
    List<ExchangeRate> findAllByCurrency(@Param("currencyCode") String currencyCode);
}
