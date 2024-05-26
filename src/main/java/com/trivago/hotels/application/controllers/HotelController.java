package com.trivago.hotels.application.controllers;

import com.trivago.hotels.application.dtos.CreateHotelDto;
import com.trivago.hotels.domain.entities.Hotel;
import com.trivago.hotels.domain.services.HotelService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
@AllArgsConstructor
public class HotelController {

    private HotelService hotelService;

    @PostMapping("")
    public ResponseEntity<Hotel> create(@RequestBody @Valid CreateHotelDto dto) {
        var response = hotelService.create(dto.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<List<Hotel>> findAll() {
        var response = hotelService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> findOne(@PathVariable(name = "id") Long id) {
        var response = hotelService.findOne(id);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        hotelService.delete(id);
        return ResponseEntity.ok().build();
    }
}
