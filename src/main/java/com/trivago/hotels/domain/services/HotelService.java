package com.trivago.hotels.domain.services;


import com.trivago.hotels.domain.entities.Hotel;

import java.util.List;

public interface HotelService {
    Hotel create(String name);
    List<Hotel> findAll();
    Hotel findOne(Long id);
    void delete(Long id);
}
