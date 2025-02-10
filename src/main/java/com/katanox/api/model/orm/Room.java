package com.katanox.api.model.orm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false, foreignKey = @ForeignKey(name = "room_hotel_id_foreign"))
    private Hotel hotel;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "number_of_this_type", nullable = false, columnDefinition = "INT DEFAULT 1")
    private int numberOfThisType;

    @Column(name = "max_adults", nullable = false, columnDefinition = "INT DEFAULT 2")
    private int maxAdults;

    @Column(name = "max_children", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int maxChildren;

    @Column(name = "max_occupancy", nullable = false, columnDefinition = "INT DEFAULT 2")
    private int maxOccupancy;

    public Room(Hotel hotel, String name, String description, int numberOfThisType, int maxAdults, int maxChildren, int maxOccupancy) {
        this.hotel = hotel;
        this.name = name;
        this.description = description;
        this.numberOfThisType = numberOfThisType;
        this.maxAdults = maxAdults;
        this.maxChildren = maxChildren;
        this.maxOccupancy = maxOccupancy;
    }
}
