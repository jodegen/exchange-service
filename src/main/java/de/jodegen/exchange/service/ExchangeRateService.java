package de.jodegen.exchange.service;

import de.jodegen.exchange.mapper.ExchangeRateMapper;
import de.jodegen.exchange.repository.ExchangeRateRepository;
import de.jodegen.exchange.rest.model.ExchangeRateDto;
import lombok.*;
import org.springframework.stereotype.Service;

import java.math.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final ExchangeRateMapper exchangeRateMapper;

    public ExchangeRateDto getExchangeRate(@NonNull String currencyCode) {
        return exchangeRateRepository.findCurrentExchangeRate(currencyCode)
                .map(exchangeRateMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Currency code not found: " + currencyCode));
    }

    public List<ExchangeRateDto> getExchangeRates() {
        return exchangeRateMapper.toDtoList(exchangeRateRepository.findLatestExchangeRatesForAllCurrencies());
    }

    public List<ExchangeRateDto> getExchangeRateHistory(@NonNull String currencyCode) {
        return exchangeRateRepository.findAllByCurrency(currencyCode)
                .stream()
                .map(exchangeRateMapper::toDto)
                .toList();
    }

    public BigDecimal convert(@NonNull String fromCurrency, @NonNull String toCurrency, @NonNull BigDecimal amount) {
        if (fromCurrency.equalsIgnoreCase(toCurrency)) {
            return amount;
        }

        BigDecimal fromRate = getExchangeRate(fromCurrency).getRate(); // EUR -> FROM
        BigDecimal toRate = getExchangeRate(toCurrency).getRate();     // EUR -> TO

        BigDecimal eurAmount = amount.multiply(fromRate);         // FROM → EUR
        return eurAmount.divide(toRate, 6, RoundingMode.HALF_UP); // EUR → TO
    }
}
