package de.jodegen.exchange.rest.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDto {
    private String currencyCode;
    private BigDecimal rate;
    private LocalDateTime timestamp;
}
