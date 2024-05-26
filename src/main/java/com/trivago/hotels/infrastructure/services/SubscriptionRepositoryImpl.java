package com.trivago.hotels.infrastructure.services;

import com.trivago.hotels.domain.constants.SubscriptionStatus;
import com.trivago.hotels.domain.entities.Subscription;
import com.trivago.hotels.domain.repositories.SubscriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    private SubscriptionJPARepository subscriptionJPARepository;

    @Override
    public Subscription save(Subscription subscription) {
        return SubscriptionMapper.toDomain(subscriptionJPARepository.save(SubscriptionMapper.fromDomain(subscription)));
    }

    @Override
    public List<Subscription> findAll(SubscriptionStatus status, Integer month) {
        return subscriptionJPARepository.findByStatusAndStartDateMonth(status, month)
                .stream()
                .map(SubscriptionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Subscription> findOne(Long id) {
        return subscriptionJPARepository.findById(id).map(SubscriptionMapper::toDomain);
    }

    @Override
    public Optional<Subscription> findHotelsActiveSubscription(Long hotelId) {
        return subscriptionJPARepository.findByHotelIdAndStatus(hotelId, SubscriptionStatus.ACTIVE).map(SubscriptionMapper::toDomain);
    }

    @Override
    public List<Subscription> findAllByNextPaymentDate(LocalDate nextPaymentDate) {
        return subscriptionJPARepository.findAllByNextPaymentDateAndStatus(nextPaymentDate, SubscriptionStatus.ACTIVE)
                .stream()
                .map(SubscriptionMapper::toDomain)
                .collect(Collectors.toList());
    }
}
