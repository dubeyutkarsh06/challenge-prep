package com.katanox.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.wildfly.common.annotation.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationServiceRequest {

    @NotNull
    public String messageId;

    @NotNull
    public long roomId;

    @NotNull
    public long priceBeforeTax;

    @NotNull
    public long priceAfterTax;

}
