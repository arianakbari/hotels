package com.trivago.hotels.domain.services;

import com.trivago.hotels.domain.entities.Hotel;
import com.trivago.hotels.domain.exceptions.HotelAlreadyExistException;
import com.trivago.hotels.domain.exceptions.HotelNotFoundException;
import com.trivago.hotels.domain.repositories.HotelRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class HotelServiceImpl implements HotelService {

    private HotelRepository hotelRepository;

    @Override
    public Hotel create(String name) {
        var hotel = hotelRepository.findOne(name);

        if (hotel.isPresent()) {
            throw new HotelAlreadyExistException(name);
        }

        return hotelRepository.save(
                Hotel.builder()
                        .name(name)
                        .build()
        );
    }

    @Override
    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel findOne(Long id) {
        var hotelOptional = hotelRepository.findOne(id);

        if (hotelOptional.isEmpty()) {
            throw new HotelNotFoundException(id);
        }
        return hotelOptional.get();
    }

    @Override
    public void delete(Long id) {
        var deletedRecords = hotelRepository.delete(id);

        if (deletedRecords == 0) {
            throw new HotelNotFoundException(id);
        }
    }
}
