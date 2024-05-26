package com.trivago.hotels.domain.services;

import com.trivago.hotels.domain.constants.SubscriptionStatus;
import com.trivago.hotels.domain.constants.SubscriptionTerm;
import com.trivago.hotels.domain.entities.Hotel;
import com.trivago.hotels.domain.entities.Subscription;
import com.trivago.hotels.domain.exceptions.ActiveSubscriptionExistException;
import com.trivago.hotels.domain.exceptions.InvalidSubscriptionStatusException;
import com.trivago.hotels.domain.exceptions.SubscriptionNotFoundException;
import com.trivago.hotels.domain.repositories.SubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceImplTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private HotelService hotelService;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    private Hotel hotel;
    private Subscription subscription;

    @BeforeEach
    void setUp() {
        hotel = Hotel.builder()
                .id(1L)
                .name("Test name")
                .build();

        subscription = Subscription.builder()
                .id(1L)
                .build();
    }

    @Test
    void create_ShouldThrowException_WhenActiveSubscriptionExists() {
        Long hotelId = hotel.getId();
        LocalDate startDate = LocalDate.now();
        SubscriptionTerm term = SubscriptionTerm.MONTHLY;

        when(hotelService.findOne(hotelId)).thenReturn(hotel);
        when(subscriptionRepository.findHotelsActiveSubscription(hotelId)).thenReturn(Optional.of(new Subscription()));

        assertThrows(ActiveSubscriptionExistException.class, () -> subscriptionService.create(hotelId, term, startDate));
    }

    @Test
    void create_ShouldSaveSubscription_WhenNoActiveSubscriptionExists() {
        Long hotelId = hotel.getId();
        LocalDate startDate = LocalDate.now();
        SubscriptionTerm term = SubscriptionTerm.MONTHLY;

        when(hotelService.findOne(hotelId)).thenReturn(hotel);
        when(subscriptionRepository.findHotelsActiveSubscription(hotelId)).thenReturn(Optional.empty());
        when(subscriptionRepository.save(any(Subscription.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Subscription subscription = subscriptionService.create(hotelId, term, startDate);

        assertNotNull(subscription);
        assertEquals(startDate.plusMonths(1), subscription.getNextPaymentDate());
        assertEquals(SubscriptionStatus.ACTIVE, subscription.getStatus());
        verify(subscriptionRepository).save(subscription);
    }

    @Test
    void cancel_ShouldThrowException_WhenSubscriptionNotFound() {
        when(subscriptionRepository.findOne(subscription.getId())).thenReturn(Optional.empty());

        assertThrows(SubscriptionNotFoundException.class, () -> subscriptionService.cancel(subscription.getId()));
    }

    @Test
    void cancel_ShouldThrowException_WhenSubscriptionStatusIsNotActive() {
        subscription.setStatus(SubscriptionStatus.CANCELED);

        when(subscriptionRepository.findOne(subscription.getId())).thenReturn(Optional.of(subscription));

        assertThrows(InvalidSubscriptionStatusException.class, () -> subscriptionService.cancel(subscription.getId()));
    }

    @Test
    void cancel_ShouldUpdateSubscriptionStatus_WhenSubscriptionIsActive() {
        subscription.setStatus(SubscriptionStatus.ACTIVE);

        when(subscriptionRepository.findOne(subscription.getId())).thenReturn(Optional.of(subscription));

        subscriptionService.cancel(subscription.getId());

        assertEquals(SubscriptionStatus.CANCELED, subscription.getStatus());
        assertNotNull(subscription.getEndDate());
        verify(subscriptionRepository).save(subscription);
    }

    @Test
    void restart_ShouldThrowException_WhenSubscriptionNotFound() {

        when(subscriptionRepository.findOne(subscription.getId())).thenReturn(Optional.empty());

        assertThrows(SubscriptionNotFoundException.class, () -> subscriptionService.restart(subscription.getId()));
    }

    @Test
    void restart_ShouldThrowException_WhenSubscriptionStatusIsNotExpired() {
        subscription.setStatus(SubscriptionStatus.ACTIVE);

        when(subscriptionRepository.findOne(subscription.getId())).thenReturn(Optional.of(subscription));

        assertThrows(InvalidSubscriptionStatusException.class, () -> subscriptionService.restart(subscription.getId()));
    }

    @Test
    void restart_ShouldUpdateSubscriptionStatus_WhenSubscriptionIsExpired() {
        subscription.setStatus(SubscriptionStatus.EXPIRED);
        subscription.setTerm(SubscriptionTerm.MONTHLY);

        when(subscriptionRepository.findOne(subscription.getId())).thenReturn(Optional.of(subscription));

        subscriptionService.restart(subscription.getId());

        assertEquals(SubscriptionStatus.ACTIVE, subscription.getStatus());
        assertNotNull(subscription.getStartDate());
        assertNotNull(subscription.getNextPaymentDate());
        assertNull(subscription.getEndDate());
        verify(subscriptionRepository).save(subscription);
    }
}
