package com.trivago.hotels.domain.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ActiveSubscriptionExistException extends RuntimeException {
    private Long hotelId;
}
