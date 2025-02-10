package com.katanox.api.repository;

import com.katanox.api.model.dto.RoomDateDto;
import com.katanox.api.model.orm.Room;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository {
    public List<Room> findAll();

    public List<RoomDateDto> getRoomsByDate(LocalDate date, Long hotel_id);

}
