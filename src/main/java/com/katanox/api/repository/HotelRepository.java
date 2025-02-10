package com.katanox.api.repository;

import com.katanox.api.model.orm.Hotel;

import java.util.List;

public interface HotelRepository {
    public List<Hotel> findAll();
}
