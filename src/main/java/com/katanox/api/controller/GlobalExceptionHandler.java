package com.katanox.api.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.katanox.api.exception.RoomNotFoundException;
import com.katanox.api.model.response.DateExceptionResponse;
import com.katanox.api.model.response.InvalidDataExceptionResponse;
import com.katanox.api.model.response.RoomExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<DateExceptionResponse> handleDateException(DateTimeParseException dateTimeParseException){
        DateExceptionResponse dateExceptionResponse = new DateExceptionResponse();
        dateExceptionResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
        dateExceptionResponse.setMessage("Invalid date format. Expected date format: yyyy-mm-dd");
        dateExceptionResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(dateExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<InvalidDataExceptionResponse> handleInvalidDataException(JsonParseException jsonParseException){
        InvalidDataExceptionResponse invalidDataExceptionResponse = new InvalidDataExceptionResponse();
        invalidDataExceptionResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
        invalidDataExceptionResponse.setMessage("Invalid input! Please check and try again");
        invalidDataExceptionResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(invalidDataExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<RoomExceptionResponse> handleRoomNotFoundException(RoomNotFoundException roomNotFoundException){
        RoomExceptionResponse roomExceptionResponse = new RoomExceptionResponse();
        roomExceptionResponse.setCode(HttpStatus.NOT_FOUND.value());
        roomExceptionResponse.setMessage(roomNotFoundException.getMessage());
        roomExceptionResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(roomExceptionResponse,HttpStatus.NOT_FOUND);
    }
}
