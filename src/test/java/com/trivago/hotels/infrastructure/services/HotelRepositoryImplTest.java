package com.trivago.hotels.infrastructure.services;

import com.trivago.hotels.domain.entities.Hotel;
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
public class HotelRepositoryImplTest {

    @Mock
    private HotelJPARepository hotelJPARepository;

    @InjectMocks
    private HotelRepositoryImpl hotelRepositoryImpl;

    private Hotel hotel;

    @BeforeEach
    void setUp() {
        hotel = Hotel.builder()
                .id(1L)
                .name("Test Hotel")
                .build();
    }

    @Test
    void save_shouldSaveHotel() {
        when(hotelJPARepository.save(any())).thenReturn(HotelMapper.fromDomain(hotel));

        Hotel savedHotel = hotelRepositoryImpl.save(hotel);

        assertNotNull(savedHotel);
        assertEquals(hotel.getId(), savedHotel.getId());
        assertEquals(hotel.getName(), savedHotel.getName());

        verify(hotelJPARepository, times(1)).save(any());
    }

    @Test
    void findAll_shouldReturnListOfHotels() {
        when(hotelJPARepository.findAll()).thenReturn(List.of(HotelMapper.fromDomain(hotel)));

        List<Hotel> hotels = hotelRepositoryImpl.findAll();

        assertNotNull(hotels);
        assertEquals(1, hotels.size());
        assertEquals(hotel, hotels.get(0));

        verify(hotelJPARepository, times(1)).findAll();
    }

    @Test
    void findOneById_shouldReturnHotel() {
        when(hotelJPARepository.findById(hotel.getId())).thenReturn(Optional.of(HotelMapper.fromDomain(hotel)));

        Optional<Hotel> foundHotel = hotelRepositoryImpl.findOne(hotel.getId());

        assertTrue(foundHotel.isPresent());
        assertEquals(hotel, foundHotel.get());

        verify(hotelJPARepository, times(1)).findById(hotel.getId());
    }

    @Test
    void findOneById_shouldReturnEmpty_whenHotelNotFound() {
        when(hotelJPARepository.findById(hotel.getId())).thenReturn(Optional.empty());

        Optional<Hotel> foundHotel = hotelRepositoryImpl.findOne(hotel.getId());

        assertFalse(foundHotel.isPresent());

        verify(hotelJPARepository, times(1)).findById(hotel.getId());
    }

    @Test
    void findOneByName_shouldReturnHotel() {
        when(hotelJPARepository.findOneByName(hotel.getName())).thenReturn(Optional.of(HotelMapper.fromDomain(hotel)));

        Optional<Hotel> foundHotel = hotelRepositoryImpl.findOne(hotel.getName());

        assertTrue(foundHotel.isPresent());
        assertEquals(hotel, foundHotel.get());

        verify(hotelJPARepository, times(1)).findOneByName(hotel.getName());
    }

    @Test
    void findOneByName_shouldReturnEmpty_whenHotelNotFound() {
        when(hotelJPARepository.findOneByName(hotel.getName())).thenReturn(Optional.empty());

        Optional<Hotel> foundHotel = hotelRepositoryImpl.findOne(hotel.getName());

        assertFalse(foundHotel.isPresent());

        verify(hotelJPARepository, times(1)).findOneByName(hotel.getName());
    }

    @Test
    void delete_shouldReturnDeletedRecordsCount() {
        when(hotelJPARepository.deleteHotelById(hotel.getId())).thenReturn(1L);

        Long deletedRecords = hotelRepositoryImpl.delete(hotel.getId());

        assertEquals(1L, deletedRecords);

        verify(hotelJPARepository, times(1)).deleteHotelById(hotel.getId());
    }
}
