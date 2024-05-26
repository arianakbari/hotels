package com.trivago.hotels.infrastructure.services;

import com.trivago.hotels.domain.entities.Hotel;
import com.trivago.hotels.domain.exceptions.HotelNotFoundException;
import com.trivago.hotels.domain.repositories.HotelRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
@AllArgsConstructor
public class HotelRepositoryImpl implements HotelRepository {

    private HotelJPARepository hotelJPARepository;

    @Override
    public Hotel save(Hotel hotel) {
        return HotelMapper.toDomain(hotelJPARepository.save(HotelMapper.fromDomain(hotel)));
    }

    @Override
    public List<Hotel> findAll() {
        return hotelJPARepository.findAll()
                .stream()
                .map(HotelMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Hotel> findOne(Long id) {
        var hotelOptional = hotelJPARepository.findById(id);

        return hotelOptional.map(HotelMapper::toDomain);
    }

    @Override
    public Optional<Hotel> findOne(String name) {
        var hotelOptional = hotelJPARepository.findOneByName(name);

        return hotelOptional.map(HotelMapper::toDomain);
    }

    @Transactional
    @Override
    public Long delete(Long id) {
        return hotelJPARepository.deleteHotelById(id);
    }
}
