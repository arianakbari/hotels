package com.trivago.hotels.infrastructure.services;

import com.trivago.hotels.domain.constants.SubscriptionStatus;
import com.trivago.hotels.domain.entities.Hotel;
import com.trivago.hotels.domain.entities.Subscription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubscriptionRepositoryImplTest {

    @Mock
    private SubscriptionJPARepository subscriptionJPARepository;

    @InjectMocks
    private SubscriptionRepositoryImpl subscriptionRepositoryImpl;

    private Subscription subscription;
    private Hotel hotel;

    @BeforeEach
    public void setUp() {
        hotel = Hotel.builder()
                .id(1L)
                .name("Test name")
                .build();

        subscription = Subscription.builder()
                .id(1L)
                .hotel(hotel)
                .build();
    }

    @Test
    public void save_shouldReturnSavedSubscription() {
        when(subscriptionJPARepository.save(any())).thenReturn(SubscriptionMapper.fromDomain(subscription));
        Subscription result = subscriptionRepositoryImpl.save(subscription);
        assertEquals(subscription, result);
    }

    @Test
    public void findAll_shouldReturnAllSubscriptions() {
        when(subscriptionJPARepository.findByStatusAndStartDateMonth(any(), any()))
                .thenReturn(Collections.singletonList(SubscriptionMapper.fromDomain(subscription)));
        List<Subscription> result = subscriptionRepositoryImpl.findAll(SubscriptionStatus.ACTIVE, 5);
        assertEquals(1, result.size());
        assertEquals(subscription, result.get(0));
    }

    @Test
    public void findOneById_shouldReturnSubscription() {
        when(subscriptionJPARepository.findById(1L)).thenReturn(Optional.of(SubscriptionMapper.fromDomain(subscription)));
        Optional<Subscription> result = subscriptionRepositoryImpl.findOne(1L);
        assertTrue(result.isPresent());
        assertEquals(subscription, result.get());
    }

    @Test
    public void findOneById_shouldReturnEmptyWhenNotFound() {
        when(subscriptionJPARepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Subscription> result = subscriptionRepositoryImpl.findOne(1L);
        assertTrue(result.isEmpty());
    }

    @Test
    public void findHotelsActiveSubscription_shouldReturnActiveSubscription() {
        when(subscriptionJPARepository.findByHotelIdAndStatus(any(), eq(SubscriptionStatus.ACTIVE)))
                .thenReturn(Optional.of(SubscriptionMapper.fromDomain(subscription)));
        Optional<Subscription> result = subscriptionRepositoryImpl.findHotelsActiveSubscription(1L);
        assertTrue(result.isPresent());
        assertEquals(subscription, result.get());
    }

    @Test
    public void findAllByNextPaymentDate_shouldReturnSubscriptionsWithNextPaymentDate() {
        when(subscriptionJPARepository.findAllByNextPaymentDateAndStatus(any(), eq(SubscriptionStatus.ACTIVE)))
                .thenReturn(Collections.singletonList(SubscriptionMapper.fromDomain(subscription)));
        List<Subscription> result = subscriptionRepositoryImpl.findAllByNextPaymentDate(LocalDate.now());
        assertEquals(1, result.size());
        assertEquals(subscription, result.get(0));
    }
}
