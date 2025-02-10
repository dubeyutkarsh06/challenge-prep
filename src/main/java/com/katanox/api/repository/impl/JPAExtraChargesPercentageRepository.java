package com.katanox.api.repository.impl;

import com.katanox.api.model.orm.ExtraChargesPercentage;
import com.katanox.api.model.dto.RoomDateDto;
import com.katanox.api.model.dto.TotalRoomPriceDto;
import com.katanox.api.repository.ExtraChargesPercentageRepository;
import com.katanox.api.repository.ExtraChargesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JPAExtraChargesPercentageRepository implements ExtraChargesPercentageRepository {

    private EntityManager entityManager;
    private JPARoomRepository JPARoomRepository;

    @Autowired
    public JPAExtraChargesPercentageRepository(EntityManager entityManager, JPARoomRepository JPARoomRepository){
        this.entityManager = entityManager;
        this.JPARoomRepository = JPARoomRepository;
    }

    @Override
    public List<ExtraChargesPercentage> findAll() {
        TypedQuery<ExtraChargesPercentage> query = this.entityManager.createQuery("FROM ExtraChargesPercentage ", ExtraChargesPercentage.class);
        return query.getResultList();
    }

    @Override
    public List<TotalRoomPriceDto> addExtraChargesPercentage(List<TotalRoomPriceDto> totalRoomPriceDtoRespons, LocalDate startDate, LocalDate endDate, Long hotelId, String currency){

        List<Double> firstNightChargesPercentage = addFirstNightPercentCharges(hotelId);

        List<Double> totalAmountChargesPercentage = addTotalAmountPercentCharges(hotelId);

        for(TotalRoomPriceDto response: totalRoomPriceDtoRespons){
            Double addFirstNightCharges = 0.0;
            List<RoomDateDto> roomDateDtoRespons = this.JPARoomRepository.getRoomsByDate(startDate,hotelId);

            Double roomFirstNightCharge = roomDateDtoRespons
                    .stream()
                    .filter(row -> row.getId().equals(response.getId()))
                    .filter(row -> row.getCurrency().equalsIgnoreCase(response.getCurrency()))
                    .map(row -> row.getPrice())
                    .findFirst()
                    .get();

            for(int i=0;i<firstNightChargesPercentage.size();i++){
                addFirstNightCharges += (firstNightChargesPercentage.get(i) * (roomFirstNightCharge)) / 100;
            }
            response.setTotalPrice(response.getTotalPrice() + addFirstNightCharges);
        }

        for(TotalRoomPriceDto response: totalRoomPriceDtoRespons){
            Double totalAmountChages = 0.0;
            for(int i=0;i<totalAmountChargesPercentage.size();i++){
                totalAmountChages += (totalAmountChargesPercentage.get(i) * (response.getPriceAfterTax())) / 100;
            }
            response.setTotalPrice(response.getTotalPrice() + totalAmountChages);
        }

        return totalRoomPriceDtoRespons;
    }

    @Override
    public List<Double> addFirstNightPercentCharges(Long hotelId){
        TypedQuery<Object[]> extraCharges = this.entityManager.createQuery("select e.appliedOn, e.percentage from ExtraChargesPercentage e where e.hotel.id=:hotelId", Object[].class);

        extraCharges.setParameter("hotelId", hotelId);

        return extraCharges.getResultList()
                .stream()
                .filter(row -> row!=null)
                .filter(row -> row[0].toString().equalsIgnoreCase("first_night"))
                .map(row -> (Double) row[1])
                .collect(Collectors.toList());
    }

    @Override
    public List<Double> addTotalAmountPercentCharges(Long hotelId){
        TypedQuery<Object[]> extraCharges = this.entityManager.createQuery("select e.appliedOn, e.percentage from ExtraChargesPercentage e where e.hotel.id=:hotelId", Object[].class);

        extraCharges.setParameter("hotelId", hotelId);

        return extraCharges.getResultList()
                .stream()
                .filter(row -> row!=null)
                .filter(row -> row[0].toString().equalsIgnoreCase("total_amount"))
                .map(row -> (Double) row[1])
                .collect(Collectors.toList());
    }
}
