package com.katanox.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.wildfly.common.annotation.NotNull;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationRequest {
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate checkin;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate checkout;
    @NotNull
    public long hotelId;

    @NotNull
    public long roomId;

}
