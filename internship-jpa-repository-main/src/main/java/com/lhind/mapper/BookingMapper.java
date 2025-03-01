package com.lhind.mapper;

import com.lhind.model.entity.Booking;
import com.lhind.model.resource.BookingResource;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    public static Booking toEntity(BookingResource resource) {
        if (resource == null) return null;

        Booking booking = new Booking();
        booking.setBookingDate(resource.bookingDate());
        booking.setStatus(resource.status());
        booking.setUser(resource.user());
        booking.setFlight(resource.flight());

        return booking;
    }
    public static void updateBookingFromResource(BookingResource resource, Booking booking) {
        if (resource == null || booking == null) return;

        if (resource.bookingDate() != null) booking.setBookingDate(resource.bookingDate());
        if (resource.status() != null) booking.setStatus(resource.status());
        if (resource.user() != null) booking.setUser(resource.user());
        if (resource.flight() != null) booking.setFlight(resource.flight());
    }
}
