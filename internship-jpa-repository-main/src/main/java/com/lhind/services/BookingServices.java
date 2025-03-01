package com.lhind.services;

import com.lhind.mapper.BookingMapper;
import com.lhind.model.entity.Booking;
import com.lhind.model.resource.BookingResource;
import com.lhind.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServices {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    public BookingServices(BookingRepository bookingRepository, BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAllByOrderByBookingDateDesc().stream().toList();
    }

    public Optional<Booking> getBookingByIdForUser(Long id, Long user_id) {
        return bookingRepository.findByIdAndUserId(id, user_id);
    }

    public List<Booking> getAllBookingsByUser(Long user_id) {
        return bookingRepository.findByUserId(user_id).stream().toList();
    }

    public List<Booking> getAllBookingsByFlight(Long flight_id) {
        return bookingRepository.findByUserId(flight_id).stream().toList();
    }

    public void create(BookingResource resource) {
        Booking booking = BookingMapper.toEntity(resource);
        bookingRepository.save(booking);
    }

    public void update(Long bookingId, BookingResource bookingResource) {
        bookingRepository.findById(bookingId).ifPresent(booking -> {
            bookingMapper.updateBookingFromResource(bookingResource, booking);
            bookingRepository.save(booking);
        });
    }

    public void delete(Long bookingId) {
        if (bookingRepository.existsById(bookingId)) {
            bookingRepository.deleteById(bookingId);
        }
    }
}
