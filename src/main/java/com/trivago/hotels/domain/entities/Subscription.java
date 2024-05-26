package com.trivago.hotels.domain.entities;

import com.trivago.hotels.domain.constants.SubscriptionStatus;
import com.trivago.hotels.domain.constants.SubscriptionTerm;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class Subscription {
    private Long id;

    private LocalDate startDate;

    private LocalDate nextPaymentDate;

    private LocalDate endDate;

    private SubscriptionStatus status;

    private SubscriptionTerm term;

    private Hotel hotel;
}
