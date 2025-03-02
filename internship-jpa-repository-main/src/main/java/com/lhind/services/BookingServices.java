package com.lhind.services;

import com.lhind.mapper.BookingMapper;
import com.lhind.model.entity.Booking;
import com.lhind.model.entity.Flight;
import com.lhind.model.entity.Users;
import com.lhind.model.resource.BookingResource;
import com.lhind.repository.BookingRepository;
import com.lhind.repository.FlightRepository;
import com.lhind.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServices {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;

    public BookingServices(BookingRepository bookingRepository, UserRepository userRepository, FlightRepository flightRepository, BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.flightRepository = flightRepository;
        this.bookingMapper = bookingMapper;
    }

    public List<BookingResource> getAllBookings() {
        return bookingRepository.findAllByOrderByBookingDateDesc().stream()
                .map(bookingMapper::toResource)
                .toList();
    }

    public Optional<BookingResource> getBookingByIdForUser(Long id, Long user_id) {
        return bookingRepository.findByIdAndUserId(id, user_id).map(bookingMapper::toResource);
    }

    public List<BookingResource> getAllBookingsByUser(Long user_id) {
        return bookingRepository.findByUserId(user_id).stream().map(bookingMapper::toResource).toList();
    }

    public List<BookingResource> getAllBookingsByFlight(Long flight_id) {
        return bookingRepository.findByFlightId(flight_id).stream().map(bookingMapper::toResource).toList();
    }

    public void create(BookingResource resource) {
        Users user = userRepository.findById(resource.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Flight flight = flightRepository.findById(resource.flightId())
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        Booking booking = bookingMapper.toEntity(resource, user, flight);
        bookingRepository.save(booking);
    }

    public void update(Long bookingId, BookingResource bookingResource) {
        Users user = userRepository.findById(bookingResource.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Flight flight = flightRepository.findById(bookingResource.flightId())
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        bookingRepository.findById(bookingId).ifPresent(booking -> {
            bookingMapper.updateBookingFromResource(bookingResource, booking, user, flight);
            bookingRepository.save(booking);
        });
    }

    public void delete(Long bookingId) {
        if (bookingRepository.existsById(bookingId)) {
            bookingRepository.deleteById(bookingId);
        }
    }
}
