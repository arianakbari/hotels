package com.trivago.hotels.domain.exceptions;

import com.trivago.hotels.domain.constants.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidSubscriptionStatusException extends RuntimeException {
    private Long id;
    private SubscriptionStatus status;
}
