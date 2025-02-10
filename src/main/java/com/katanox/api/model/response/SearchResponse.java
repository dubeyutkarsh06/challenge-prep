package com.katanox.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.wildfly.common.annotation.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchResponse {

    @NotNull
    public long hotelId;
    @NotNull
    public long roomId;
    @NotNull
    public long price;

    @NotNull
    public String currency;

}
