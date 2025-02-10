package com.katanox.api.repository.impl;

import com.katanox.api.exception.RoomNotFoundException;
import com.katanox.api.model.orm.Price;
import com.katanox.api.model.dto.TotalRoomPriceDto;
import com.katanox.api.model.request.ReservationRequest;
import com.katanox.api.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JPAPriceRepository implements PriceRepository {

    private EntityManager entityManager;
    @Autowired
    public JPAPriceRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public List<Price> findAll() {
        TypedQuery<Price> query = this.entityManager.createQuery("FROM Price ", Price.class);
        return query.getResultList();
    }

    @Override
    public List<TotalRoomPriceDto> getRoomsPricesInDateRange(LocalDate startDate, LocalDate endDate, Long hotelId, String currency) {
        TypedQuery<Object[]> query = this.entityManager.createQuery(" select r.id, sum(p.priceAfterTax), p.currency from Hotel h JOIN Room r on h.id=r.hotel.id JOIN Price p on r.id=p.id.room.id WHERE h.id=:hotelId and p.currency=:currency and p.id.date >= :start_date and p.id.date < :end_date and p.quantity > 0 group by r.id,p.currency ", Object[].class);

        query.setParameter("start_date", startDate);
        query.setParameter("end_date", endDate);
        query.setParameter("hotelId", hotelId);
        query.setParameter("currency", currency);
        if(query.getResultList().size() == 0) {
            throw new RoomNotFoundException("No available rooms in the provided date range: " + startDate + " - " + endDate);
        }

        return query.getResultList().stream()
                .map(row -> new TotalRoomPriceDto((Long) row[0], (Double) row[1], (Double) row[1], (String) row[2]// Convert to Double
                ))
                .collect(Collectors.toList());

    }

    @Override
    public List<TotalRoomPriceDto> getRoomsExVATPricesInDateRange(LocalDate startDate, LocalDate endDate, Long hotelId, String currency) {
        TypedQuery<Object[]> query = this.entityManager.createQuery(" select r.id, sum((p.priceAfterTax) - (p.priceAfterTax * h.vat) / 100), p.currency " +
                "from Hotel h JOIN Room r on h.id=r.hotel.id JOIN Price p on r.id=p.id.room.id " +
                "WHERE h.id=:hotelId and p.currency=:currency and p.id.date >= :start_date " +
                "and p.id.date < :end_date and p.quantity > 0 group by r.id,p.currency ", Object[].class);

        query.setParameter("start_date", startDate);
        query.setParameter("end_date", endDate);
        query.setParameter("hotelId", hotelId);
        query.setParameter("currency", currency);
        if(query.getResultList().size() == 0) {
            throw new RoomNotFoundException("No available rooms in the provided date range: " + startDate + " - " + endDate);
        }

        return query.getResultList().stream()
                .map(row -> new TotalRoomPriceDto((Long) row[0], (Double) row[1], (Double) row[1], (String) row[2]// Convert to Double
                ))
                .collect(Collectors.toList());

    }


    @Override
    public Integer updateAvailableRoomQuantity(ReservationRequest reservationRequest){
        int numberUpdate = this.entityManager.createQuery("update Price p set p.quantity = p.quantity - 1 where p.id.room.id =:roomId and p.id.date >= :startDate and p.id.date < :endDate")
                .setParameter("roomId", reservationRequest.getRoomId())
                .setParameter("startDate", reservationRequest.getCheckin())
                .setParameter("endDate", reservationRequest.getCheckout())
                .executeUpdate();
        if(numberUpdate == 0){
            throw new RoomNotFoundException("Application ran into and error while reserving rooms!");
        }
        return numberUpdate;
    }

}
