package com.driveshare.ReservationService.infrastructure.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.driveshare.ReservationService.application.dto.ReservationDTO;
import com.driveshare.ReservationService.application.service.ReservationServise;
import com.driveshare.ReservationService.domain.model.ReservationStatus;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {
    
    @Autowired
    private ReservationServise reservationService;

     @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
        ReservationDTO newReservation = reservationService.createReservation(reservationDTO);
        return ResponseEntity.ok(newReservation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> updateReservation(@PathVariable Long id, @RequestBody ReservationDTO reservationDTO) {
        ReservationDTO updatedReservation = reservationService.updateReservation(id, reservationDTO);
        return ResponseEntity.ok(updatedReservation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        ReservationDTO reservationDTO = reservationService.findReservationById(id);
        return ResponseEntity.ok(reservationDTO);
    }

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        List<ReservationDTO> reservations = reservationService.findAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByStatus(@PathVariable ReservationStatus status) {
        List<ReservationDTO> reservations = reservationService.findReservationsByStatus(status);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/host/{idHost}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByHost(@PathVariable("idHost") Long idHost) {
        List<ReservationDTO> reservations = reservationService.findReservationsByHost(idHost);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/guest/{idGuest}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByGuest(@PathVariable("idGuest") Long idGuest) {
        List<ReservationDTO> reservations = reservationService.findReservationsByGuest(idGuest);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/parking/{idParking}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByParking(@PathVariable("idParking") Long idParking) {
        List<ReservationDTO> reservations = reservationService.findReservationsByParking(idParking);
        return ResponseEntity.ok(reservations);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ReservationDTO> updateReservationStatus(@PathVariable Long id, @RequestParam("status") ReservationStatus status) {
        ReservationDTO updatedReservation = reservationService.updateReservationStatus(id, status);
        return ResponseEntity.ok(updatedReservation);
    }
}
