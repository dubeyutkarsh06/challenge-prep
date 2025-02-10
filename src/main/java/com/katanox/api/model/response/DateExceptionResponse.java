package com.katanox.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DateExceptionResponse {

    private int errorCode;

    private String message;

    private long timeStamp;

}
