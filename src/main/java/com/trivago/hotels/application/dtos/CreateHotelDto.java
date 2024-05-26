package com.trivago.hotels.application.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
public class CreateHotelDto {

    @NotBlank(message = "Invalid name!")
    @NotNull(message = "Invalid name!")
    private String name;
}
