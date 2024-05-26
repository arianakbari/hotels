package com.trivago.hotels.infrastructure.services;

import com.trivago.hotels.domain.constants.SubscriptionStatus;
import com.trivago.hotels.infrastructure.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
interface SubscriptionJPARepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByHotelIdAndStatus(Long hotelId, SubscriptionStatus status);
    List<Subscription> findAllByNextPaymentDateAndStatus(LocalDate nextPaymentDate, SubscriptionStatus status);

    @Query("SELECT s FROM Subscription s WHERE " +
            "(:status IS NULL OR s.status = :status) AND " +
            "(:month IS NULL OR MONTH(s.startDate) = :month)")
    List<Subscription> findByStatusAndStartDateMonth(
            @Param("status") SubscriptionStatus status,
            @Param("month") Integer month
    );
}
