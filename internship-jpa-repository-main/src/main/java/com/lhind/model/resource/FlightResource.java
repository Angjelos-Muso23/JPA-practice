package com.lhind.model.resource;

import com.lhind.model.enums.Status;
import java.util.Date;

public record FlightResource(String origin, String destination, String airline, String flightNumber, Date departureDate, Date arrivalDate, Status status) {

}
