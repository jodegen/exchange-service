package de.jodegen.exchange.fetcher.impl;

import de.jodegen.exchange.fetcher.ExchangeRateFetcher;
import de.jodegen.exchange.model.ExchangeRate;
import de.jodegen.exchange.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EcbExchangeRateFetcher implements ExchangeRateFetcher {

    private static final String ECB_URL = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

    private final RestTemplate restTemplate;
    private final CurrencyReferenceRepository currencyReferenceRepository;
    private final ExchangeRateRepository exchangeRateRepository;

    @Override
    public void fetchAndStoreRates() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(ECB_URL, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                parseAndSave(response.getBody());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch ECB rates", e);
        }
    }

    private void parseAndSave(String xml) throws Exception {
        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new InputSource(new StringReader(xml)));

        NodeList cubeNodes = doc.getElementsByTagName("Cube");
        LocalDate date = null;
        List<ExchangeRate> rates = new ArrayList<>();

        for (int i = 0; i < cubeNodes.getLength(); i++) {
            Element el = (Element) cubeNodes.item(i);
            if (el.hasAttribute("time")) {
                date = LocalDate.parse(el.getAttribute("time"));
            }
            if (el.hasAttribute("currency") && el.hasAttribute("rate") && date != null) {
                String currencyCode = el.getAttribute("currency");
                BigDecimal rate = new BigDecimal(el.getAttribute("rate"));

                currencyReferenceRepository.findByCode(currencyCode).ifPresent(ref -> {
                    ExchangeRate exchangeRate = new ExchangeRate();
                    exchangeRate.setReference(ref);
                    exchangeRate.setRate(rate);
                    exchangeRate.setTimestamp(LocalDateTime.now());
                    rates.add(exchangeRate);
                });
            }
        }
        exchangeRateRepository.saveAll(rates);
    }
}
