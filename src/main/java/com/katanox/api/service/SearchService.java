package com.katanox.api.service;

import com.katanox.api.model.request.SearchRequest;
import com.katanox.api.model.response.SearchResponse;

import java.util.List;

public interface SearchService {
    public List<SearchResponse> searchAvailabilities(SearchRequest searchRequest);

    public List<SearchResponse> searchAvailabilitiesWithPriceExcludingVAT(SearchRequest searchRequest);
}
