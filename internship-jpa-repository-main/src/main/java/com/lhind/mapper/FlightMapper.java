package com.lhind.mapper;

import com.lhind.model.entity.Flight;
import com.lhind.model.resource.FlightResource;
import org.springframework.stereotype.Component;

@Component
public class FlightMapper {
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
