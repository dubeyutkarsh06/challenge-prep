package com.katanox.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoomDateDto {
    private Long id;

    private Double price;

    private LocalDate date;

    private String currency;

}
