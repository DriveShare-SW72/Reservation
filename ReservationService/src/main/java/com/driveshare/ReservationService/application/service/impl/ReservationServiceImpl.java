package com.driveshare.ReservationService.application.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.driveshare.ReservationService.application.dto.ReservationDTO;
import com.driveshare.ReservationService.application.service.ReservationServise;
import com.driveshare.ReservationService.common.exception.ResourceNotFoundException;
import com.driveshare.ReservationService.common.exception.ValidationException;
import com.driveshare.ReservationService.domain.model.Reservation;
import com.driveshare.ReservationService.domain.model.ReservationStatus;
import com.driveshare.ReservationService.repository.ReservationRepository;


@Service
public class ReservationServiceImpl implements ReservationServise{

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Transactional
    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        //TODO ver como usar el microservicio de user para poner el id del guest y host
        //TODO ver como usar el microservicio de parking para poner el id del parking
        //TODO ver como usar el microservicio de vehicle para poner el id del vehicle
        //TODO ver como usar el microservicio de payment para poner el id del payment
        //TODO ver como usar el microservicio de review para poner el id del review
        Reservation reservation = modelMapper.map(reservationDTO, Reservation.class);
        reservation = reservationRepository.save(reservation);
        return modelMapper.map(reservation, ReservationDTO.class);
    }

    @Transactional
    @Override
    public ReservationDTO updateReservation(Long id, ReservationDTO updatedReservation) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with ID: " + id));
        reservation.setStart_date(updatedReservation.getStart_date());
        reservation.setEnd_date(updatedReservation.getEnd_date());
        reservation.setParkingId(updatedReservation.getParkingId());
        reservation.setHostId(updatedReservation.getHostId());
        reservation.setGuestId(updatedReservation.getGuestId());
        reservation.setReviewId(updatedReservation.getReviewId());
        reservation.setVehiculeId(updatedReservation.getVehiculeId());
        reservation.setPaymentId(updatedReservation.getPaymentId());
        reservation.setTotal_fare(updatedReservation.getTotal_fare());
        if (updatedReservation.getStatus() != reservation.getStatus()) {
            switch (updatedReservation.getStatus()) {
                case CONFIRMED:
                    reservation.confirmReservation();
                    break;
                case ACTIVE:
                    reservation.activateReservation();
                    break;
                case COMPLETED:
                    reservation.completeReservation();
                    break;
                case CANCELLED:
                    reservation.cancelReservation();
                    break;
                case FAILED:
                    reservation.failReservation();
                    break;
                default:
                    throw new ValidationException("Unexpected status provided.");
            }
        }
        return modelMapper.map(reservationRepository.save(reservation), ReservationDTO.class);
    }

    @Transactional(readOnly = true)
    @Override
    public ReservationDTO findReservationById(Long id) {
        return modelMapper.map(reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation not found with ID: " + id))
        , ReservationDTO.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReservationDTO> findAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(reservation -> modelMapper.map(reservation, ReservationDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with ID: " + id));
        reservation.cancelReservation();
        reservationRepository.save(reservation);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReservationDTO> findReservationsByStatus(ReservationStatus status) {
        List<Reservation> reservations = reservationRepository.findByStatus(status);
        return reservations.stream()
                .map(reservation -> modelMapper.map(reservation, ReservationDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReservationDTO> findReservationsByHost(Long idHost) {
        List<Reservation> reservations = reservationRepository.findByHostId(idHost);
        return reservations.stream()
                .map(reservation -> modelMapper.map(reservation, ReservationDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReservationDTO> findReservationsByGuest(Long idGuest) {
        List<Reservation> reservations = reservationRepository.findByGuestId(idGuest);
        return reservations.stream()
                .map(reservation -> modelMapper.map(reservation, ReservationDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReservationDTO> findReservationsByParking(Long idParking) {
        List<Reservation> reservations = reservationRepository.findByParkingId(idParking);
        return reservations.stream()
                .map(reservation -> modelMapper.map(reservation, ReservationDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ReservationDTO updateReservationStatus(Long id, ReservationStatus newStatus) {
        Reservation reservation = reservationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with ID: " + id));

        reservation.updateStatus(newStatus);
        reservation = reservationRepository.save(reservation);
        return modelMapper.map(reservation, ReservationDTO.class);
    }
}
