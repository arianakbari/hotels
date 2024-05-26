package com.trivago.hotels.domain.repositories;

import com.trivago.hotels.domain.constants.SubscriptionStatus;
import com.trivago.hotels.domain.constants.SubscriptionTerm;
import com.trivago.hotels.domain.entities.Hotel;
import com.trivago.hotels.domain.entities.Subscription;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository {
    Subscription save(Subscription subscription);
    List<Subscription> findAll(SubscriptionStatus status, Integer month);
    Optional<Subscription> findOne(Long id);
    Optional<Subscription> findHotelsActiveSubscription(Long hotelId);
    List<Subscription> findAllByNextPaymentDate(LocalDate nextPaymentDate);
}
