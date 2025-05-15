package de.jodegen.exchange.rest;

import de.jodegen.exchange.rest.model.*;
import de.jodegen.exchange.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/exchange")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;
    private final CurrencyReferenceService currencyReferenceService;

    @GetMapping
    public List<ExchangeRateDto> getCurrentExchangeRates() {
        return exchangeRateService.getExchangeRates();
    }

    @GetMapping(path = "/currencies")
    public List<CurrencyReferenceDto> getRegisteredCurrencies() {
        return currencyReferenceService.getAllCurrencyReferences();
    }

    @GetMapping(path = "/{currencyCode}")
    public ExchangeRateDto getCurrentExchangeRate(@PathVariable(name = "currencyCode") String currencyCode) {
        return exchangeRateService.getExchangeRate(currencyCode);
    }

    @GetMapping(path = "/{currencyCode}/history")
    public List<ExchangeRateDto> getExchangeRateHistory(@PathVariable(name = "currencyCode") String currencyCode) {
        return exchangeRateService.getExchangeRateHistory(currencyCode);
    }

    @PostMapping(path = "/register/{currencyCode}")
    public void registerCurrency(@PathVariable(name = "currencyCode") String currencyCode) {
        currencyReferenceService.registerCurrency(currencyCode);
    }
}
