package com.trivago.hotels.infrastructure.services;

import com.trivago.hotels.domain.entities.Hotel;

class HotelMapper {
    public static Hotel toDomain(com.trivago.hotels.infrastructure.models.Hotel hotel) {
        return Hotel.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .build();
    }

    public static com.trivago.hotels.infrastructure.models.Hotel fromDomain(Hotel hotel) {
        return com.trivago.hotels.infrastructure.models.Hotel.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .build();
    }
}
