package com.trivago.hotels.infrastructure.services;

import com.trivago.hotels.infrastructure.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface HotelJPARepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findOneByName(String name);
    Long deleteHotelById(Long id);
}
