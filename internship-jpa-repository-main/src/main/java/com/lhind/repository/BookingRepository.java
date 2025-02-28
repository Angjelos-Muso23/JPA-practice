package com.lhind.repository;

import com.lhind.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByOrderByBookingDateDesc();

    Optional<Booking> findByIdAndUserId(Long id, Long user_id);

    List<Booking> findByUserId(Long user_id);

    List<Booking> findByFlightId(Long flight_id);
}
