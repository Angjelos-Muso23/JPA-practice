package com.lhind.model.resource;

import com.lhind.model.entity.Flight;
import com.lhind.model.enums.Status;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

public record FlightResource(String origin, String destination, String airline, String flightNumber, Date departureDate, Date arrivalDate, Status status) {

}
