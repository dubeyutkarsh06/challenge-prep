package com.katanox.api.model.orm;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "extra_charges_percentage")
public class ExtraChargesPercentage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "extra_charges_percentage_seq")
    @SequenceGenerator(name = "extra_charges_percentage_seq", sequenceName = "extra_charges_percentage_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "description", length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "applied_on", nullable = false)
    private AppliedOn appliedOn;

    @Column(name = "percentage", nullable = false, precision = 5, scale = 2)
    private Double percentage;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false, foreignKey = @ForeignKey(name = "extra_charges_percentage_hotel_id_foreign"))
    private Hotel hotel;


}
