package com.trivago.hotels.infrastructure.models;

import com.trivago.hotels.domain.constants.SubscriptionStatus;
import com.trivago.hotels.domain.constants.SubscriptionTerm;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "subscriptions")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column()
    private LocalDate nextPaymentDate;

    @Column
    private LocalDate endDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SubscriptionTerm term;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "hotelId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_hotel_subscription"))
    private Hotel hotel;
}
