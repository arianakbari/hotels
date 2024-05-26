package com.trivago.hotels.application.dtos;

import com.trivago.hotels.domain.constants.SubscriptionTerm;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateSubscriptionDto {

    @NotNull(message = "Invalid hotel id!")
    private Long hotelId;

    @NotNull(message = "Invalid term")
    private SubscriptionTerm term;

    @NotNull(message = "Invalid start date")
    @FutureOrPresent(message = "Start date must be today or some date in future")
    private LocalDate startDate;
}
