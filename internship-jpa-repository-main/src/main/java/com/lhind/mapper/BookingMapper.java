package com.lhind.mapper;

import com.lhind.model.entity.Booking;
import com.lhind.model.entity.Flight;
import com.lhind.model.entity.Users;
import com.lhind.model.resource.BookingResource;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    public Booking toEntity(BookingResource resource, Users user, Flight flight) {
        if (resource == null) return null;

        Booking booking = new Booking();
        booking.setBookingDate(resource.bookingDate());
        booking.setStatus(resource.status());
        booking.setUser(user);
        booking.setFlight(flight);

        return booking;
    }

    public BookingResource toResource(Booking booking) {
        if (booking == null) return null;

        return new BookingResource(
                booking.getBookingDate(),
                booking.getStatus(),
                booking.getUser().getId(),
                booking.getFlight().getId()
        );
    }

    public void updateBookingFromResource(BookingResource resource, Booking booking, Users user, Flight flight) {
        if (resource == null || booking == null) return;

        if (resource.bookingDate() != null) booking.setBookingDate(resource.bookingDate());
        if (resource.status() != null) booking.setStatus(resource.status());
        if (user != null) booking.setUser(user);
        if (flight != null) booking.setFlight(flight);
    }
}
