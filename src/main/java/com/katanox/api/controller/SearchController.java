package com.katanox.api.controller;

import com.katanox.api.model.orm.Hotel;
import com.katanox.api.model.request.SearchRequest;
import com.katanox.api.model.response.SearchResponse;
import com.katanox.api.repository.impl.JPAHotelRepository;
import com.katanox.api.service.LogWriterService;
import com.katanox.api.service.impl.DefaultSearchService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("search")
public class SearchController {

    @Value("${env}")
    String environment;

    @Autowired
    LogWriterService logWriterService;

    private com.katanox.api.repository.impl.JPAHotelRepository JPAHotelRepository;

    private DefaultSearchService defaultSearchService;

    @Autowired
    public SearchController(DefaultSearchService defaultSearchService, JPAHotelRepository JPAHotelRepository) {
        this.logWriterService = new LogWriterService();
        this.JPAHotelRepository = JPAHotelRepository;
        this.defaultSearchService = defaultSearchService;
    }

    @Operation(
            summary = "Search available hotels",
            description = "Search for available hotels based on check-in and check-out dates."
    )
    @PostMapping(
            path = "/",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    ResponseEntity<List<SearchResponse>> search(
            @RequestBody SearchRequest request
    ) {
        List<SearchResponse> results = defaultSearchService.searchAvailabilities(request);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping(path = "/hotels")
    public List<Hotel> getHotels(){
        return this.JPAHotelRepository.findAll();
    }


}
