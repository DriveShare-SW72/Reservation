package com.driveshare.ReservationService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.driveshare.ReservationService.application.dto.ReservationDTO;
import com.driveshare.ReservationService.application.service.impl.ReservationServiceImpl;
import com.driveshare.ReservationService.domain.model.Reservation;
import com.driveshare.ReservationService.domain.model.ReservationStatus;
import com.driveshare.ReservationService.repository.ReservationRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindReservationsByHost() {
        Long hostId = 1L;
        List<Reservation> reservations = Arrays.asList(new Reservation(), new Reservation());
        when(reservationRepository.findByHostId(hostId)).thenReturn(reservations);
        when(modelMapper.map(any(Reservation.class), eq(ReservationDTO.class))).thenAnswer(i -> new ReservationDTO());
        List<ReservationDTO> result = reservationService.findReservationsByHost(hostId);
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(reservationRepository).findByHostId(hostId);
    }

    @Test
    void testCreateReservation() {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setGuestId(1L);
        Reservation reservation = new Reservation();
        when(modelMapper.map(reservationDTO, Reservation.class)).thenReturn(reservation);
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        when(modelMapper.map(reservation, ReservationDTO.class)).thenReturn(reservationDTO);

        ReservationDTO createdReservation = reservationService.createReservation(reservationDTO);

        assertNotNull(createdReservation);
        verify(reservationRepository).save(reservation);
    }

    @Test
    void testCancelReservation() {
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        reservation.setStatus(ReservationStatus.CONFIRMED);
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        reservationService.cancelReservation(reservationId);
        assertEquals(ReservationStatus.CANCELLED, reservation.getStatus(),
                "The reservation status should be CANCELLED");
        verify(reservationRepository).save(reservation);
    }
}
