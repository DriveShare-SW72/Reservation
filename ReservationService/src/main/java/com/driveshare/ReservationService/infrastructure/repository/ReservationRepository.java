package com.driveshare.ReservationService.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.driveshare.ReservationService.domain.model.Reservation;
import com.driveshare.ReservationService.domain.model.ReservationStatus;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{
    
    List<Reservation> findByStatus(ReservationStatus status);

    List<Reservation> findByHostId(Long idHost);

    List<Reservation> findByGuestId(Long gestId);

    List<Reservation> findByParkingId(Long parkingId);
    
}
