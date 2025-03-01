package com.lhind.controllers;

import com.lhind.model.entity.Booking;
import com.lhind.model.resource.BookingResource;
import com.lhind.repository.BookingRepository;
import com.lhind.services.BookingServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.lhind.controllers.BookingControllers.BOOKINGS_BASE_PATH;

@RestController
@RequestMapping(BOOKINGS_BASE_PATH)
public class BookingControllers {
    static final String BOOKINGS_BASE_PATH = "/bookings";

    private final BookingRepository bookingRepository;
    private final BookingServices bookingServices;

    public BookingControllers(final BookingRepository bookingRepository, final BookingServices bookingServices) {
        this.bookingRepository = bookingRepository;
        this.bookingServices = bookingServices;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<Void> createUser(@RequestBody final Booking booking) {
        if (bookingRepository.existsById(booking.getId())) {
            bookingServices.create(booking);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingServices.getAllBookings());
    }

    @GetMapping(path = "/user/{userId}/booking/{bookingId}", produces = "application/json")
    public ResponseEntity<Booking> getFlightById(@PathVariable("userId") final Long userId, @PathVariable("bookingId") final Long bookingId) {
        return bookingServices.getBookingByIdForUser(bookingId, userId).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/user/{userId}", produces = "application/json")
    public ResponseEntity<List<Booking>> getAllBookingsByUser(@PathVariable("userId") final Long userId) {
        return ResponseEntity.ok(bookingServices.getAllBookingsByUser(userId));
    }

    @GetMapping(path = "/user/{flightId}", produces = "application/json")
    public ResponseEntity<List<Booking>> getAllBookingsByFlight(@PathVariable("flightId") final Long flightId) {
        return ResponseEntity.ok(bookingServices.getAllBookingsByFlight(flightId));
    }

    @PutMapping(path = "{bookingId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> updateBooking(@PathVariable("bookingId") final Long bookingId, @RequestBody final BookingResource booking) {
        bookingServices.update(bookingId, booking);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{bookingId}", produces = "application/json")
    public ResponseEntity<Void> deleteBooking(@PathVariable("bookingId") final Long bookingId) {
        bookingServices.delete(bookingId);
        return ResponseEntity.noContent().build();
    }
}
