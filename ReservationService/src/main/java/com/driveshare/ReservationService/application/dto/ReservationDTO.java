package com.driveshare.ReservationService.application.dto;

import java.time.LocalDateTime;

import com.driveshare.ReservationService.domain.model.ReservationStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    
    private Long id;
    private Float total_fare;
    private ReservationStatus status;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private Long parkingId;
    private Long hostId;
    private Long guestId;
    private Long ReviewId;
    private Long vehiculeId;
    private Long paymentId;

}
