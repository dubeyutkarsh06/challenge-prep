package com.katanox.api.service.impl.data;

import com.katanox.api.model.dto.TotalRoomPriceDto;
import com.katanox.api.model.request.SearchRequest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class DefaultSearchServiceDataTest {
    public static SearchRequest searchRequestIncludingVAT = new SearchRequest(LocalDate.now(), LocalDate.now().plusDays(3), 1L);
    public static SearchRequest searchRequestExcludingVAT = new SearchRequest(LocalDate.now(), LocalDate.now().plusDays(3), 1L);

    public static TotalRoomPriceDto roomPriceIncludingVATDto1 = new TotalRoomPriceDto(1L, 200.00 ,220.00, "EUR");
    public static List<TotalRoomPriceDto> roomPriceListIncludingVAT1 = Collections.singletonList(roomPriceIncludingVATDto1);

    public static TotalRoomPriceDto roomPriceIncludingVATDto2 = new TotalRoomPriceDto(1L, 200.00 ,220.00, "EUR");
    public static List<TotalRoomPriceDto> roomPriceListIncludingVAT2 = Collections.singletonList(roomPriceIncludingVATDto2);

    public static TotalRoomPriceDto roomPriceIncludingVATDto3 = new TotalRoomPriceDto(1L, 200.00 ,220.00, "EUR");
    public static List<TotalRoomPriceDto> roomPriceListIncludingVAT3 = Collections.singletonList(roomPriceIncludingVATDto3);

    public static TotalRoomPriceDto roomPriceExcludingVATDto1 = new TotalRoomPriceDto(1L, 200.00 ,220.00, "EUR");
    public static List<TotalRoomPriceDto> roomPriceListExcludingVAT1 = Collections.singletonList(roomPriceExcludingVATDto1);

    public static TotalRoomPriceDto roomPriceExcludingVATDto2 = new TotalRoomPriceDto(1L, 200.00 ,220.00, "EUR");
    public static List<TotalRoomPriceDto> roomPriceListExcludingVAT2 = Collections.singletonList(roomPriceExcludingVATDto2);

    public static TotalRoomPriceDto roomPriceExcludingVATDto3 = new TotalRoomPriceDto(1L, 200.00 ,220.00, "EUR");
    public static List<TotalRoomPriceDto> roomPriceListExcludingVAT3 = Collections.singletonList(roomPriceExcludingVATDto3);
}
