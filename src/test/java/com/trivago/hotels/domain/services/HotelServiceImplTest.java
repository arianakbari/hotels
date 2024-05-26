package com.trivago.hotels.domain.services;

import com.trivago.hotels.domain.entities.Hotel;
import com.trivago.hotels.domain.exceptions.HotelAlreadyExistException;
import com.trivago.hotels.domain.exceptions.HotelNotFoundException;
import com.trivago.hotels.domain.repositories.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HotelServiceImplTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelServiceImpl hotelService;

    private Hotel hotel;

    @BeforeEach
    void setUp() {
        hotel = Hotel.builder()
                .name("Test Hotel")
                .build();
    }

    @Test
    void create_shouldThrowException_whenHotelAlreadyExists() {
        when(hotelRepository.findOne(hotel.getName())).thenReturn(Optional.of(hotel));

        assertThrows(HotelAlreadyExistException.class, () -> hotelService.create(hotel.getName()));

        verify(hotelRepository, times(1)).findOne(hotel.getName());
        verify(hotelRepository, never()).save(any(Hotel.class));
    }

    @Test
    void create_shouldSaveHotel_whenHotelDoesNotExist() {
        when(hotelRepository.findOne(hotel.getName())).thenReturn(Optional.empty());
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        Hotel createdHotel = hotelService.create(hotel.getName());

        assertNotNull(createdHotel);
        assertEquals(hotel.getName(), createdHotel.getName());

        verify(hotelRepository, times(1)).findOne(hotel.getName());
        verify(hotelRepository, times(1)).save(any(Hotel.class));
    }

    @Test
    void findAll_shouldReturnListOfHotels() {
        List<Hotel> hotels = List.of(hotel);
        when(hotelRepository.findAll()).thenReturn(hotels);

        List<Hotel> foundHotels = hotelService.findAll();

        assertNotNull(foundHotels);
        assertEquals(1, foundHotels.size());
        assertEquals(hotel.getName(), foundHotels.get(0).getName());

        verify(hotelRepository, times(1)).findAll();
    }

    @Test
    void findOne_shouldReturnHotel_whenHotelExists() {
        when(hotelRepository.findOne(1L)).thenReturn(Optional.of(hotel));

        Hotel foundHotel = hotelService.findOne(1L);

        assertNotNull(foundHotel);
        assertEquals(hotel.getName(), foundHotel.getName());

        verify(hotelRepository, times(1)).findOne(1L);
    }

    @Test
    void findOne_shouldThrowException_whenHotelDoesNotExist() {
        when(hotelRepository.findOne(1L)).thenReturn(Optional.empty());

        assertThrows(HotelNotFoundException.class, () -> hotelService.findOne(1L));

        verify(hotelRepository, times(1)).findOne(1L);
    }

    @Test
    void delete_shouldDeleteHotel_whenHotelExists() {
        when(hotelRepository.delete(1L)).thenReturn(1L);

        assertDoesNotThrow(() -> hotelService.delete(1L));

        verify(hotelRepository, times(1)).delete(1L);
    }

    @Test
    void delete_shouldThrowException_whenHotelDoesNotExist() {
        when(hotelRepository.delete(1L)).thenReturn(0L);

        assertThrows(HotelNotFoundException.class, () -> hotelService.delete(1L));

        verify(hotelRepository, times(1)).delete(1L);
    }
}
