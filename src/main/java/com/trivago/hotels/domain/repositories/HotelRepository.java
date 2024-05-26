package com.trivago.hotels.domain.repositories;

import com.trivago.hotels.domain.entities.Hotel;

import java.util.List;
import java.util.Optional;

public interface HotelRepository {
    Hotel save(Hotel hotel);
    List<Hotel> findAll();
    Optional<Hotel> findOne(Long id);
    Optional<Hotel> findOne(String name);
    Long delete(Long id);
}
