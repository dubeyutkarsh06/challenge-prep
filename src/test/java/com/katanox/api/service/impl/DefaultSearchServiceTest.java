package com.katanox.api.service.impl;

import com.katanox.api.model.request.SearchRequest;
import com.katanox.api.model.response.SearchResponse;
import com.katanox.api.repository.impl.JPAExtraChargesPercentageRepository;
import com.katanox.api.repository.impl.JPAExtraChargesRepository;
import com.katanox.api.repository.impl.JPAPriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;

import static com.katanox.api.service.impl.data.DefaultSearchServiceDataTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultSearchServiceTest {

    @Mock
    private JPAPriceRepository JPAPriceRepository;

    @Mock
    private JPAExtraChargesRepository JPAExtraChargesRepository;

    @Mock
    private JPAExtraChargesPercentageRepository JPAExtraChargesPercentageRepository;

    @InjectMocks
    private DefaultSearchService defaultSearchService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(defaultSearchService, "currency", "EUR");
    }

    @Test
    void testSearchAvailabilities() {
        // Given
        SearchRequest searchRequest = new SearchRequest(LocalDate.now(), LocalDate.now().plusDays(3), 1L);

        // When
        when(JPAPriceRepository.getRoomsPricesInDateRange(any(LocalDate.class), any(LocalDate.class), any(Long.class), anyString()))
                .thenReturn(roomPriceListIncludingVAT1);
        when(JPAExtraChargesRepository.addExtraCharges(anyList(), any(LocalDate.class), any(LocalDate.class), any(Long.class), anyString()))
                .thenReturn(roomPriceListIncludingVAT2);
        when(JPAExtraChargesPercentageRepository.addExtraChargesPercentage(anyList(), any(LocalDate.class), any(LocalDate.class), any(Long.class), anyString()))
                .thenReturn(roomPriceListIncludingVAT3);

        List<SearchResponse> responses = defaultSearchService.searchAvailabilities(searchRequest);

        // Then
        assertEquals(1, responses.size());
        assertEquals(1L, responses.get(0).getHotelId());
        assertEquals(1L, responses.get(0).getRoomId());
        assertEquals(200.00, responses.get(0).getPrice());
        assertEquals("EUR", responses.get(0).getCurrency());
    }

    @Test
    void testSearchAvailabilitiesWithPriceExcludingVAT() {
        // Given
        SearchRequest searchRequest = new SearchRequest(LocalDate.now(), LocalDate.now().plusDays(3), 1L);

        // When
        when(JPAPriceRepository.getRoomsExVATPricesInDateRange(any(LocalDate.class), any(LocalDate.class), any(Long.class), anyString()))
                .thenReturn(roomPriceListIncludingVAT1);
        when(JPAExtraChargesRepository.addExtraCharges(anyList(), any(LocalDate.class), any(LocalDate.class), any(Long.class), anyString()))
                .thenReturn(roomPriceListIncludingVAT2);
        when(JPAExtraChargesPercentageRepository.addExtraChargesPercentage(anyList(), any(LocalDate.class), any(LocalDate.class), any(Long.class), anyString()))
                .thenReturn(roomPriceListIncludingVAT3);
        List<SearchResponse> responses = defaultSearchService.searchAvailabilities(searchRequest);

        // Then
        assertEquals(1, responses.size());
        assertEquals(1L, responses.get(0).getHotelId());
        assertEquals(1L, responses.get(0).getRoomId());
        assertEquals(200.00, responses.get(0).getPrice());
        assertEquals("EUR", responses.get(0).getCurrency());
    }
}
