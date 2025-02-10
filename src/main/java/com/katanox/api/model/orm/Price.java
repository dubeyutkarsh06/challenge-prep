package com.katanox.api.model.orm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "prices")
public class Price {
    @EmbeddedId
    private PriceId id; // Composite primary key

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price_after_tax", nullable = false, precision = 10, scale = 2)
    private Double priceAfterTax;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    public Price(PriceId id, int quantity, Double priceAfterTax, String currency) {
        this.id = id;
        this.quantity = quantity;
        this.priceAfterTax = priceAfterTax;
        this.currency = currency;
    }
}
