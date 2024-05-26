package com.trivago.hotels.application.jobs;

import com.trivago.hotels.domain.services.SubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@EnableAsync
@AllArgsConstructor
public class SubscriptionJob {
    private SubscriptionService subscriptionService;

    @Async
    @Scheduled(cron = "* 15 0 * * *", zone = "CET")
    protected void processSubscriptionPayments() {
        subscriptionService.processPayments();
    }
}
