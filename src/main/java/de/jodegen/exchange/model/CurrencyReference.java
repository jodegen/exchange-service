package de.jodegen.exchange.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "currency_reference")
@NoArgsConstructor
@Getter
@Setter
public class CurrencyReference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency_code", unique = true, nullable = false)
    private String code;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    public CurrencyReference(String code) {
        this.code = code;
    }
}
