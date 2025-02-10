package com.katanox.api.repository;

import com.katanox.api.model.dto.TotalRoomPriceDto;
import com.katanox.api.model.orm.ExtraChargesPercentage;

import java.time.LocalDate;
import java.util.List;

public interface ExtraChargesPercentageRepository {

    public List<ExtraChargesPercentage> findAll();

    public List<TotalRoomPriceDto> addExtraChargesPercentage(List<TotalRoomPriceDto> totalRoomPriceDtoRespons, LocalDate startDate, LocalDate endDate, Long hotelId, String currency);

    public List<Double> addFirstNightPercentCharges(Long hotelId);

    public List<Double> addTotalAmountPercentCharges(Long hotelId);
}
