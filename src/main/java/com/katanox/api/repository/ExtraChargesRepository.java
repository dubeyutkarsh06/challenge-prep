package com.katanox.api.repository;

import com.katanox.api.model.dto.TotalRoomPriceDto;
import com.katanox.api.model.orm.ExtraCharges;

import java.time.LocalDate;
import java.util.List;

public interface ExtraChargesRepository {
    public List<ExtraCharges> findAll();

    public List<TotalRoomPriceDto> addExtraCharges(List<TotalRoomPriceDto> totalRoomPriceDtoResponse, LocalDate startDate, LocalDate endDate, Long hotel_id, String currency);

    public Double oneOffExtraPrice(Long hotel_id, String currency);

    public Double perNightExtraPrice(Long hotel_id, String currency);
}
