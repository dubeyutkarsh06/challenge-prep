package com.katanox.api.repository;

import com.katanox.api.model.dto.TotalRoomPriceDto;
import com.katanox.api.model.orm.Price;
import com.katanox.api.model.request.ReservationRequest;

import java.time.LocalDate;
import java.util.List;

public interface PriceRepository {
    public List<Price> findAll();

    public List<TotalRoomPriceDto> getRoomsPricesInDateRange(LocalDate startDate, LocalDate endDate, Long hotelId, String currency);

    public List<TotalRoomPriceDto> getRoomsExVATPricesInDateRange(LocalDate startDate, LocalDate endDate, Long hotelId, String currency);

    public Integer updateAvailableRoomQuantity(ReservationRequest reservationRequest);

}
