package com.trivago.hotels.application.config;

import com.trivago.hotels.domain.repositories.HotelRepository;
import com.trivago.hotels.domain.repositories.SubscriptionRepository;
import com.trivago.hotels.domain.services.HotelService;
import com.trivago.hotels.domain.services.HotelServiceImpl;
import com.trivago.hotels.domain.services.SubscriptionService;
import com.trivago.hotels.domain.services.SubscriptionServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class BeanConfiguration {

    @Bean
    public HotelService hotelService(HotelRepository hotelRepository) {
        return new HotelServiceImpl(hotelRepository);
    }

    @Bean
    public SubscriptionService subscriptionService(SubscriptionRepository subscriptionRepository, HotelService hotelService) {
        return new SubscriptionServiceImpl(subscriptionRepository, hotelService);
    }

}
