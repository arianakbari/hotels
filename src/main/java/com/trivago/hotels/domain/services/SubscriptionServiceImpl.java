package com.trivago.hotels.domain.services;

import com.trivago.hotels.domain.constants.SubscriptionStatus;
import com.trivago.hotels.domain.constants.SubscriptionTerm;
import com.trivago.hotels.domain.entities.Subscription;
import com.trivago.hotels.domain.exceptions.ActiveSubscriptionExistException;
import com.trivago.hotels.domain.exceptions.InvalidSubscriptionStatusException;
import com.trivago.hotels.domain.exceptions.SubscriptionNotFoundException;
import com.trivago.hotels.domain.repositories.SubscriptionRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private SubscriptionRepository subscriptionRepository;
    private HotelService hotelService;

    @Override
    public Subscription create(Long hotelId, SubscriptionTerm term, LocalDate startDate) {
        var hotel = hotelService.findOne(hotelId);

        var activeSubscriptionOptional = subscriptionRepository.findHotelsActiveSubscription(hotel.getId());

        if (activeSubscriptionOptional.isPresent()) {
            throw new ActiveSubscriptionExistException(hotel.getId());
        }

        var nextPaymentDate = startDate.plusMonths(term == SubscriptionTerm.MONTHLY ? 1 : 12);

        return subscriptionRepository.save(
                Subscription
                    .builder()
                    .startDate(startDate)
                    .nextPaymentDate(nextPaymentDate)
                    .term(term)
                    .status(SubscriptionStatus.ACTIVE)
                    .hotel(hotel)
                    .build()
        );

    }

    @Override
    public List<Subscription> findAll(SubscriptionStatus status, Integer month) {
        return subscriptionRepository.findAll(status, month);
    }

    @Override
    public void cancel(Long id) {
        var subscription = subscriptionRepository.findOne(id).orElseThrow(() -> new SubscriptionNotFoundException(id));

        if (subscription.getStatus() != SubscriptionStatus.ACTIVE) {
            throw new InvalidSubscriptionStatusException(id, SubscriptionStatus.ACTIVE);
        }

        subscription.setStatus(SubscriptionStatus.CANCELED);
        subscription.setEndDate(LocalDate.now());
        subscription.setNextPaymentDate(null);

        subscriptionRepository.save(subscription);
    }

    @Override
    public void restart(Long id) {
        var subscription = subscriptionRepository.findOne(id).orElseThrow(() -> new SubscriptionNotFoundException(id));

        if (subscription.getStatus() != SubscriptionStatus.EXPIRED) {
            throw new InvalidSubscriptionStatusException(id, SubscriptionStatus.EXPIRED);
        }

        subscription.setStatus(SubscriptionStatus.ACTIVE);
        subscription.setStartDate(LocalDate.now());
        subscription.setNextPaymentDate(
                LocalDate
                        .now()
                        .plusMonths(subscription.getTerm() == SubscriptionTerm.MONTHLY ? 1 : 12)
        );
        subscription.setEndDate(null);

        subscriptionRepository.save(subscription);
    }

    @Override
    public void processPayments() {
        var today = LocalDate.now(ZoneId.of("CET"));
        // get all subscriptions that have payment date of today
        var subscriptions = subscriptionRepository.findAllByNextPaymentDate(today);
        // based on result of payment update the status of each subscription
        subscriptions.forEach(s -> {
            // process payments
            // used some random logic to mock this action
            // in real applications should have its separate class and logic
            var paymentResult = new Random().nextInt(2) == 1;
            if (paymentResult) {
                s.setNextPaymentDate(today);
                subscriptionRepository.save(s);
            } else {
                s.setNextPaymentDate(null);
                s.setEndDate(today);
                s.setStatus(SubscriptionStatus.EXPIRED);
                subscriptionRepository.save(s);
            }
        });
    }
}
