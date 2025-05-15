package de.jodegen.exchange.rest.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ExchangeRateDto {
    private String currencyCode;
    private BigDecimal rate;
    private LocalDateTime timestamp;

    // TODO: Remove when Annotation Processing is fixed
    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
