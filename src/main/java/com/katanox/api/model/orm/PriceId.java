package com.katanox.api.model.orm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class PriceId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false)
    private Room room;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    public PriceId(Room room, LocalDate date) {
        this.room = room;
        this.date = date;
    }

    // Override equals and hashCode for correct composite key handling
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceId priceId = (PriceId) o;
        return room.equals(priceId.room) && date.equals(priceId.date);
    }

    @Override
    public int hashCode() {
        return 31 * room.hashCode() + date.hashCode();
    }
}
