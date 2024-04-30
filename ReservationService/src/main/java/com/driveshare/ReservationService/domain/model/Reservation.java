package com.driveshare.ReservationService.domain.model;

import java.time.LocalDateTime;

import com.driveshare.ReservationService.common.exception.ValidationException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Builder
public class Reservation {

    //TODO verificar si vehicule y parking seran desde domain. Si es asi, se debe cambiar el tipo de dato a Vehicle y Parking
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //El nombre debe ser diferente fare no representa el precio del alquiler de cochera. deberia ser total_cost
    private Float total_fare;

    private ReservationStatus status;

    private LocalDateTime start_date;

    private LocalDateTime end_date;

    private Long parkingId;

    private Long hostId;

    private Long guestId;

    private Long reviewId;

    private Long vehiculeId;

    private Long paymentId;
    
    public Reservation() {
        this.status = ReservationStatus.PENDING;
    }

    // Método para confirmar la reserva
    public void confirmReservation() {
        if (this.status == ReservationStatus.PENDING) {
            this.status = ReservationStatus.CONFIRMED;
        } else {
            throw new ValidationException("Reservation can only be confirmed if it is currently pending.");
        }
    }

    // Método para activar la reserva
    public void activateReservation() {
        if (this.status == ReservationStatus.CONFIRMED && LocalDateTime.now().isAfter(this.start_date)) {
            this.status = ReservationStatus.ACTIVE;
        } else {
            throw new ValidationException("Reservation can only be activated if it is confirmed and the start time has passed.");
        }
    }

    // Método para completar la reserva
    public void completeReservation() {
        if (this.status == ReservationStatus.ACTIVE && LocalDateTime.now().isAfter(this.start_date)) {
            this.status = ReservationStatus.COMPLETED;
        } else {
            throw new ValidationException("Reservation can only be completed if it is active and the end time has passed.");
        }
    }

    // Método para cancelar la reserva
    public void cancelReservation() {
        if (this.status == ReservationStatus.PENDING || this.status == ReservationStatus.CONFIRMED) {
            this.status = ReservationStatus.CANCELLED;
        } else {
            throw new ValidationException("Reservation can only be cancelled if it is pending or confirmed.");
        }
    }

    // Método para marcar la reserva como fallida
    public void failReservation() {
        this.status = ReservationStatus.FAILED;
    }


    public void updateStatus(ReservationStatus newStatus) {
        switch (newStatus) {
            case CONFIRMED:
                confirmReservation();
                break;
            case ACTIVE:
                activateReservation();
                break;
            case COMPLETED:
                completeReservation();
                break;
            case CANCELLED:
                cancelReservation();
                break;
            case FAILED:
                failReservation();
                break;
            default:
                throw new ValidationException("Attempting to set an unrecognized status.");
        }
    }

    
}
