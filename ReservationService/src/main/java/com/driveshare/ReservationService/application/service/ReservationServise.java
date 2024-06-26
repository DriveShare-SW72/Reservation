package com.driveshare.ReservationService.application.service;

import java.util.List;

import com.driveshare.ReservationService.application.dto.ReservationDTO;
import com.driveshare.ReservationService.domain.model.ReservationStatus;

public interface ReservationServise {
    
    public abstract ReservationDTO createReservation(ReservationDTO reservationDTO);
    public abstract ReservationDTO updateReservation(Long id, ReservationDTO reservation);
    public abstract ReservationDTO findReservationById(Long id);
    public abstract List<ReservationDTO> findAllReservations();
    public abstract void cancelReservation(Long id);
    public abstract List<ReservationDTO> findReservationsByStatus(ReservationStatus status);
    public abstract List<ReservationDTO> findReservationsByHost(Long idHost);
    public abstract List<ReservationDTO> findReservationsByGuest(Long idGuest);
    public abstract List<ReservationDTO> findReservationsByParking(Long idParking);
    public abstract ReservationDTO updateReservationStatus(Long id, ReservationStatus newStatus);

}
