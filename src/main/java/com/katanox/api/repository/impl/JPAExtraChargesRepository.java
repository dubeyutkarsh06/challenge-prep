package com.katanox.api.repository.impl;

import com.katanox.api.model.orm.ChargeType;
import com.katanox.api.model.orm.ExtraCharges;
import com.katanox.api.model.dto.TotalRoomPriceDto;
import com.katanox.api.repository.ExtraChargesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

@Repository
public class JPAExtraChargesRepository implements ExtraChargesRepository {
    private EntityManager entityManager;
    @Autowired
    public JPAExtraChargesRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public List<ExtraCharges> findAll() {
        TypedQuery<ExtraCharges> query = this.entityManager.createQuery("FROM ExtraCharges ", ExtraCharges.class);
        return query.getResultList();
    }

    @Override
    public List<TotalRoomPriceDto> addExtraCharges(List<TotalRoomPriceDto> totalRoomPriceDtoResponse, LocalDate startDate, LocalDate endDate, Long hotel_id, String currency){


        Double oneOffPrice = oneOffExtraPrice(hotel_id,currency);

        int diff = endDate.compareTo(startDate);
        Double perNightPrice = perNightExtraPrice(hotel_id,currency);

        for(TotalRoomPriceDto response: totalRoomPriceDtoResponse){
            response.setTotalPrice(response.getPriceAfterTax() + diff * perNightPrice + oneOffPrice);
        }
        return totalRoomPriceDtoResponse;
    }

    @Override
    public Double oneOffExtraPrice(Long hotel_id, String currency){
        TypedQuery<Object[]> query = this.entityManager.createQuery("select sum(eflat.price), eflat.chargeType from ExtraCharges eflat where eflat.hotel.id=:hotelId and eflat.currency=:currency group by eflat.chargeType", Object[].class);
        query.setParameter("hotelId",hotel_id);
        query.setParameter("currency",currency);

        return query.getResultList().stream()
                .filter(row -> ((ChargeType)row[1]).toString().equals("once"))
                .map(row -> (Double) row[0])
                .findFirst()
                .get();
    }

    @Override
    public Double perNightExtraPrice(Long hotel_id, String currency){
        TypedQuery<Object[]> query = this.entityManager.createQuery("select sum(eflat.price), eflat.chargeType from ExtraCharges eflat where eflat.hotel.id=:hotelId and eflat.currency=:currency group by eflat.chargeType", Object[].class);
        query.setParameter("hotelId",hotel_id);
        query.setParameter("currency", currency);

        return query.getResultList().stream()
                .filter(row -> (row[1]).toString().equals("per_night"))
                .map(row -> (Double) row[0])
                .findFirst()
                .get();
    }

}
