package com.trivago.hotels.domain.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HotelAlreadyExistException extends RuntimeException {
    private String name;
}
