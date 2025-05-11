package de.jodegen.exchange.service;

import de.jodegen.exchange.fetcher.ExchangeRateFetcher;
import de.jodegen.exchange.mapper.CurrencyReferenceMapper;
import de.jodegen.exchange.model.CurrencyReference;
import de.jodegen.exchange.repository.CurrencyReferenceRepository;
import de.jodegen.exchange.rest.model.CurrencyReferenceDto;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CurrencyReferenceService {

    private final CurrencyReferenceMapper currencyReferenceMapper;
    private final CurrencyReferenceRepository currencyReferenceRepository;
    private final ExchangeRateFetcher exchangeRateFetcher;

    public void registerCurrency(@NonNull String currencyCode) {
        try {
            Currency.getInstance(currencyCode.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid currency code: " + currencyCode);
        }

        if (currencyReferenceRepository.existsByCode(currencyCode)) {
            throw new IllegalArgumentException("Currency already registered: " + currencyCode);
        }
        CurrencyReference currencyReference = new CurrencyReference(currencyCode);
        currencyReferenceRepository.save(currencyReference);

        exchangeRateFetcher.fetchAndStoreRates();
    }

    public List<CurrencyReferenceDto> getAllCurrencyReferences() {
        return currencyReferenceRepository.findAll()
                .stream()
                .map(currencyReferenceMapper::toDto)
                .toList();
    }
}
