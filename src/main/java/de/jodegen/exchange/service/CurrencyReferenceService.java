package de.jodegen.exchange.service;

import de.jodegen.exchange.mapper.CurrencyReferenceMapper;
import de.jodegen.exchange.model.CurrencyReference;
import de.jodegen.exchange.repository.CurrencyReferenceRepository;
import de.jodegen.exchange.rest.model.CurrencyReferenceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyReferenceService {

    private final CurrencyReferenceMapper currencyReferenceMapper;
    private final CurrencyReferenceRepository currencyReferenceRepository;

    public List<CurrencyReferenceDto> getAllCurrencyReferences() {
        return currencyReferenceRepository.findAll()
                .stream()
                .map(currencyReferenceMapper::toDto)
                .toList();
    }
}
