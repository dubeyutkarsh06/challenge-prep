package com.katanox.api.service.impl;

import com.katanox.api.repository.impl.JPAExtraChargesPercentageRepository;
import com.katanox.api.repository.impl.JPAExtraChargesRepository;
import com.katanox.api.repository.impl.JPAPriceRepository;
import com.katanox.api.model.request.SearchRequest;
import com.katanox.api.model.dto.TotalRoomPriceDto;
import com.katanox.api.model.response.SearchResponse;
import com.katanox.api.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultSearchService implements SearchService {
    private JPAPriceRepository JPAPriceRepository;

    private JPAExtraChargesRepository JPAExtraChargesRepository;

    private JPAExtraChargesPercentageRepository JPAExtraChargesPercentageRepository;

    @Value("${currency}")
    private String currency;

    @Autowired
    public DefaultSearchService(JPAPriceRepository JPAPriceRepository, JPAExtraChargesRepository JPAExtraChargesRepository, JPAExtraChargesPercentageRepository JPAExtraChargesPercentageRepository){
        this.JPAPriceRepository = JPAPriceRepository;
        this.JPAExtraChargesRepository = JPAExtraChargesRepository;
        this.JPAExtraChargesPercentageRepository = JPAExtraChargesPercentageRepository;
    }

    @Override
    public List<SearchResponse> searchAvailabilities(SearchRequest searchRequest){
        List<TotalRoomPriceDto> totalRoomPriceDtoResponse = this.JPAPriceRepository.getRoomsPricesInDateRange(searchRequest.getCheckin(),searchRequest.getCheckout(), searchRequest.getHotelId(), this.currency);

        totalRoomPriceDtoResponse = this.JPAExtraChargesRepository.addExtraCharges(totalRoomPriceDtoResponse,searchRequest.getCheckin(),searchRequest.getCheckout(), searchRequest.getHotelId(), this.currency);

        totalRoomPriceDtoResponse = this.JPAExtraChargesPercentageRepository.addExtraChargesPercentage(totalRoomPriceDtoResponse,searchRequest.getCheckin(),searchRequest.getCheckout(), searchRequest.getHotelId(), this.currency);

        return totalRoomPriceDtoResponse.stream()
                .map(row -> new SearchResponse(searchRequest.getHotelId(), row.getId(), row.getTotalPrice().longValue(), this.currency))
                .collect(Collectors.toList());
    }

    @Override
    public List<SearchResponse> searchAvailabilitiesWithPriceExcludingVAT(SearchRequest searchRequest){
        List<TotalRoomPriceDto> totalRoomPriceDtoResponse = this.JPAPriceRepository.getRoomsExVATPricesInDateRange(searchRequest.getCheckin(),searchRequest.getCheckout(), searchRequest.getHotelId(), this.currency);

        totalRoomPriceDtoResponse = this.JPAExtraChargesRepository.addExtraCharges(totalRoomPriceDtoResponse,searchRequest.getCheckin(),searchRequest.getCheckout(), searchRequest.getHotelId(), this.currency);

        totalRoomPriceDtoResponse = this.JPAExtraChargesPercentageRepository.addExtraChargesPercentage(totalRoomPriceDtoResponse,searchRequest.getCheckin(),searchRequest.getCheckout(), searchRequest.getHotelId(), this.currency);

        return totalRoomPriceDtoResponse.stream()
                .map(row -> new SearchResponse(searchRequest.getHotelId(), row.getId(), row.getTotalPrice().longValue(), this.currency))
                .collect(Collectors.toList());
    }

}
