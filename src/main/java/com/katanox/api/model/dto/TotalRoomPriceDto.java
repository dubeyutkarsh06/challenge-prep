package com.katanox.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TotalRoomPriceDto {
    private Long id;

    private Double priceAfterTax;

    private Double totalPrice;

    private String currency;
}
