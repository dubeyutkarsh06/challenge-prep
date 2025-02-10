package com.katanox.api.controller;

import com.katanox.api.model.request.ReservationRequest;
import com.katanox.api.model.response.ReservationResponse;
import com.katanox.api.service.impl.DefaultReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("reserve")
public class ReservationController {

    private DefaultReservationService defaultReservationService;

    @Autowired
    public ReservationController(DefaultReservationService defaultReservationService){
        this.defaultReservationService = defaultReservationService;
    }

    @PostMapping(path = "/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest reservationRequest){
        ReservationResponse reservationResponse = this.defaultReservationService.createReservation(reservationRequest);

        return new ResponseEntity<>(reservationResponse, HttpStatus.CREATED);
    }
}
