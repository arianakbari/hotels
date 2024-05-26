package com.trivago.hotels.infrastructure.services;

import com.trivago.hotels.domain.entities.Subscription;

class SubscriptionMapper {
    public static Subscription toDomain(com.trivago.hotels.infrastructure.models.Subscription subscription) {
        return Subscription.builder()
                .id(subscription.getId())
                .startDate(subscription.getStartDate())
                .term(subscription.getTerm())
                .status(subscription.getStatus())
                .nextPaymentDate(subscription.getNextPaymentDate())
                .endDate(subscription.getEndDate())
                .hotel(HotelMapper.toDomain(subscription.getHotel()))
                .build();
    }

    public static com.trivago.hotels.infrastructure.models.Subscription fromDomain(Subscription subscription) {
        return com.trivago.hotels.infrastructure.models.Subscription.builder()
                .id(subscription.getId())
                .startDate(subscription.getStartDate())
                .term(subscription.getTerm())
                .status(subscription.getStatus())
                .nextPaymentDate(subscription.getNextPaymentDate())
                .endDate(subscription.getEndDate())
                .hotel(HotelMapper.fromDomain(subscription.getHotel()))
                .build();
    }
}
