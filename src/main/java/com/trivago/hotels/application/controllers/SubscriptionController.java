package com.trivago.hotels.application.controllers;

import com.trivago.hotels.application.dtos.CreateSubscriptionDto;
import com.trivago.hotels.domain.constants.SubscriptionStatus;
import com.trivago.hotels.domain.entities.Subscription;
import com.trivago.hotels.domain.services.SubscriptionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscriptions")
@AllArgsConstructor
public class SubscriptionController {
    private SubscriptionService subscriptionService;

    @PostMapping("")
    public ResponseEntity<Subscription> create(@RequestBody @Valid CreateSubscriptionDto dto) {
        var response = subscriptionService.create(dto.getHotelId(), dto.getTerm(), dto.getStartDate());
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<List<Subscription>> findAll(
            @RequestParam(required = false) SubscriptionStatus status,
            @RequestParam(required = false) Integer month
    ) {
        var response = subscriptionService.findAll(status, month);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable(name = "id") Long id) {
        subscriptionService.cancel(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/restart")
    public ResponseEntity<Void> restart(@PathVariable(name = "id") Long id) {
        subscriptionService.restart(id);
        return ResponseEntity.ok().build();
    }
}
