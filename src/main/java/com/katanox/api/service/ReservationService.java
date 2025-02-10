package com.katanox.api.service;

import com.katanox.api.model.request.ReservationRequest;
import com.katanox.api.model.response.ReservationResponse;

public interface ReservationService {

    public ReservationResponse createReservation(ReservationRequest reservationRequest);
}
