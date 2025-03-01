package com.lhind.mapper;

import com.lhind.model.entity.Flight;
import com.lhind.model.entity.Users;
import com.lhind.model.resource.FlightResource;
import org.springframework.stereotype.Component;

@Component
public class FlightMapper {
    public static Flight toEntity(FlightResource resource) {
        if (resource == null) return null;

        Flight flight = new Flight();
        flight.setOrigin(resource.origin());
        flight.setDestination(resource.destination());
        flight.setFlightNumber(resource.flightNumber());
        flight.setAirline(resource.airline());
        flight.setDepartureDate(resource.departureDate());
        flight.setArrivalDate(resource.arrivalDate());
        flight.setStatus(resource.status());

        return flight;
    }

    public static void updateFlightFromResource(FlightResource resource, Flight flight) {
        if (resource == null || flight == null) return;

        if (resource.origin() != null) flight.setOrigin(resource.origin());
        if (resource.destination() != null) flight.setDestination(resource.destination());
        if (resource.flightNumber() != null) flight.setFlightNumber(resource.flightNumber());
        if (resource.airline() != null) flight.setAirline(resource.airline());
        if (resource.departureDate() != null) flight.setDepartureDate(resource.departureDate());
        if (resource.arrivalDate() != null) flight.setArrivalDate(resource.arrivalDate());
        if (resource.status() != null) flight.setStatus(resource.status());
    }
}
