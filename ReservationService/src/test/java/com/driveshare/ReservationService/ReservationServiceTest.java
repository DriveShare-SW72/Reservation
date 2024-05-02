package com.driveshare.ReservationService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.driveshare.ReservationService.application.dto.ReservationDTO;
import com.driveshare.ReservationService.application.service.impl.ReservationServiceImpl;
import com.driveshare.ReservationService.common.exception.ResourceNotFoundException;
import com.driveshare.ReservationService.domain.model.Reservation;
import com.driveshare.ReservationService.domain.model.ReservationStatus;
import com.driveshare.ReservationService.repository.ReservationRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private Reservation reservation;
    private ReservationDTO reservationDTO;

    @BeforeEach
void setUp() {
    reservation = new Reservation();
    reservation.setId(1L);
    reservation.setStatus(ReservationStatus.PENDING);
        
    reservationDTO = new ReservationDTO();
    reservationDTO.setId(1L);
    reservationDTO.setStatus(ReservationStatus.PENDING);
}

@Test
void createReservationShouldReturnReservationDTO() {
    when(modelMapper.map(any(ReservationDTO.class), eq(Reservation.class))).thenReturn(reservation);
    when(modelMapper.map(any(Reservation.class), eq(ReservationDTO.class))).thenReturn(reservationDTO);
    when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

    ReservationDTO result = reservationService.createReservation(reservationDTO);

    assertNotNull(result);
    verify(reservationRepository).save(reservation);
    assertEquals(ReservationStatus.PENDING, result.getStatus());
}

@Test
void updateReservationStatusShouldUpdateStatus() {
    when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation));
    when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
    when(modelMapper.map(reservation, ReservationDTO.class)).thenReturn(reservationDTO); 

    ReservationDTO result = reservationService.updateReservationStatus(1L, ReservationStatus.CONFIRMED);

    assertNotNull(result);
    verify(reservationRepository).save(reservation);
    assertEquals(ReservationStatus.CONFIRMED, reservation.getStatus());
}

@Test
void findReservationByIdWhenNotFoundShouldThrowException() {    
    Long reservationId = 1L;
    when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> {
        reservationService.findReservationById(reservationId);
    });

    verify(reservationRepository).findById(reservationId);
}
}
