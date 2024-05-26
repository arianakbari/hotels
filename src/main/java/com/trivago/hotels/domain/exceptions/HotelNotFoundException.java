package com.trivago.hotels.domain.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HotelNotFoundException extends RuntimeException {
    private Long id;
}
