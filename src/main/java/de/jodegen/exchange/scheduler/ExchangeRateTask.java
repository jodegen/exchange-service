package de.jodegen.exchange.scheduler;

import de.jodegen.exchange.fetcher.ExchangeRateFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExchangeRateTask {

    private final ExchangeRateFetcher fetcher;

    @Scheduled(cron = "0 0 4 * * *")
    public void fetchRates() {
        fetcher.fetchAndStoreRates();
    }
}
