package com.katanox.api.repository.impl;

import com.katanox.api.model.orm.Room;
import com.katanox.api.model.dto.RoomDateDto;
import com.katanox.api.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JPARoomRepository implements RoomRepository {
    private EntityManager entityManager;

    @Autowired
    public JPARoomRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public List<Room> findAll() {
        TypedQuery<Room> query = this.entityManager.createQuery("FROM Room ", Room.class);
        return query.getResultList();
    }

    @Override
    public List<RoomDateDto> getRoomsByDate(LocalDate date, Long hotel_id){
        return this.entityManager.createQuery("select r.id, p.priceAfterTax, p.id.date, p.currency " +
                        "from Hotel h JOIN Room r on h.id=r.hotel.id join Price p on r.id=p.id.room.id " +
                        "where h.id=:hotel_id and p.id.date=:date", Object[].class)
                .setParameter("hotel_id",hotel_id)
                .setParameter("date",date)
                .getResultList()
                .stream()
                .map(row -> new RoomDateDto((Long) row[0], (Double) row[1], (LocalDate) row[2], (String) row[3]))
                .collect(Collectors.toList());
    }
}
