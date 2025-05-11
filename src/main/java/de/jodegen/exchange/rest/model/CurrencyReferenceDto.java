package de.jodegen.exchange.rest.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyReferenceDto {
    private String code;
    private String label;
}
