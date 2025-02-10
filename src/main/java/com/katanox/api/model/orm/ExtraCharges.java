package com.katanox.api.model.orm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "extra_charges_flat")
public class ExtraCharges {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "extra_charges_flat_seq")
    @SequenceGenerator(name = "extra_charges_flat_seq", sequenceName = "extra_charges_flat_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "description", length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "charge_type", nullable = false)
    private ChargeType chargeType;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private Double price;

    @Column(name = "currency", nullable = false)
    private String currency;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false, foreignKey = @ForeignKey(name = "extra_charges_flat_hotel_id_foreign"))
    private Hotel hotel;

    public ExtraCharges(String description, ChargeType chargeType, Double price, String currency, Hotel hotel) {
        this.description = description;
        this.chargeType = chargeType;
        this.price = price;
        this.currency = currency;
        this.hotel = hotel;
    }
}

