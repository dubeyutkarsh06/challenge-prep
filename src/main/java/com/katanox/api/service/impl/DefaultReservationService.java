package com.katanox.api.service.impl;

import com.katanox.api.model.request.ReservationRequest;
import com.katanox.api.model.request.ReservationServiceRequest;
import com.katanox.api.model.request.SearchRequest;
import com.katanox.api.model.response.ReservationResponse;
import com.katanox.api.model.response.SearchResponse;
import com.katanox.api.repository.impl.JPAPriceRepository;
import com.katanox.api.service.ReservationService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class DefaultReservationService implements ReservationService {

    private DefaultSearchService defaultSearchService;
    private JPAPriceRepository JPAPriceRepository;

    private RabbitTemplate rabbitTemplate;

    @Value("${katanox.rabbitmq.queue}")
    private String queue;

    @Value("${katanox.rabbitmq.queue}")
    private String exchange;

    @Value("${katanox.rabbitmq.routingkey}")
    private String routingKey;

    @Autowired
    public DefaultReservationService(DefaultSearchService defaultSearchService, JPAPriceRepository JPAPriceRepository, RabbitTemplate rabbitTemplate){
        this.defaultSearchService = defaultSearchService;
        this.JPAPriceRepository = JPAPriceRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    @Override
    public ReservationResponse createReservation(ReservationRequest reservationRequest){
        List<SearchResponse> searchResponseListWithVAT = this.defaultSearchService.searchAvailabilities(new SearchRequest(reservationRequest.getCheckin(),reservationRequest.getCheckout(), reservationRequest.getHotelId()));

        SearchResponse searchResponseWithVAT = searchResponseListWithVAT.stream()
                .filter(row -> row.getRoomId() == reservationRequest.getRoomId())
                .findFirst()
                .get();

        List<SearchResponse> searchResponseListExVAT = this.defaultSearchService.searchAvailabilitiesWithPriceExcludingVAT(new SearchRequest(reservationRequest.getCheckin(),reservationRequest.getCheckout(), reservationRequest.getHotelId()));

        SearchResponse searchResponseExVAT = searchResponseListExVAT.stream()
                .filter(row -> row.getRoomId() == reservationRequest.getRoomId())
                .findFirst()
                .get();

        ReservationServiceRequest reservationServiceRequest = new ReservationServiceRequest(UUID.randomUUID().toString(),reservationRequest.getRoomId(), searchResponseExVAT.getPrice(), searchResponseWithVAT.getPrice());
        this.rabbitTemplate.convertAndSend(this.exchange,this.routingKey, reservationServiceRequest);
        Logger logger = Logger.getLogger(DefaultReservationService.class.getName());
        logger.info("Message Request for Room " + reservationRequest.roomId  +  " sent to the external Reservation Service!");


        this.JPAPriceRepository.updateAvailableRoomQuantity(reservationRequest);

        return new ReservationResponse(reservationRequest.getHotelId(), reservationRequest.getRoomId(), searchResponseExVAT.getPrice(), searchResponseWithVAT.getPrice(), reservationRequest.getCheckin(), reservationRequest.getCheckout());

    }
}
