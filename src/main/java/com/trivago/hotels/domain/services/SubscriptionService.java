package com.trivago.hotels.domain.services;

import com.trivago.hotels.domain.constants.SubscriptionStatus;
import com.trivago.hotels.domain.constants.SubscriptionTerm;
import com.trivago.hotels.domain.entities.Subscription;

import java.time.LocalDate;
import java.util.List;

public interface SubscriptionService {
    Subscription create(Long hotelId, SubscriptionTerm term, LocalDate startDate);
    List<Subscription> findAll(SubscriptionStatus status, Integer month);
    void cancel(Long id);
    void restart(Long id);
    void processPayments();
}
