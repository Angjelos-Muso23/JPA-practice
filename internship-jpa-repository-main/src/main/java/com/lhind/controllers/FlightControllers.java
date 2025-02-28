package com.lhind.controllers;

import com.lhind.model.entity.Flight;
import com.lhind.model.entity.Users;
import com.lhind.model.resource.FlightResource;
import com.lhind.model.resource.UsersResource;
import com.lhind.repository.FlightRepository;
import com.lhind.repository.UserRepository;
import com.lhind.services.FlightServices;
import com.lhind.services.UserServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.lhind.controllers.FlightControllers.FLIGHTS_BASE_PATH;

@RestController
@RequestMapping(FLIGHTS_BASE_PATH)
public class FlightControllers {
    static final String FLIGHTS_BASE_PATH = "/flights";

    private final FlightRepository flightRepository;
    private final FlightServices flightServices;

    public FlightControllers(final FlightRepository flightRepository, final FlightServices flightServices) {
        this.flightRepository = flightRepository;
        this.flightServices = flightServices;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<Void> createUser(@RequestBody final Flight flight) {
        if (flightRepository.existsById(flight.getId())) {
            flightServices.create(flight);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Flight>> getAllFlights() {
        return ResponseEntity.ok(flightServices.getAllFlights());
    }

    @GetMapping(path = "/{flightId}", produces = "application/json")
    public ResponseEntity<Flight> getFlightById(@PathVariable("flightId") final Long flightId) {
        return flightServices.getFlightById(flightId).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/search", produces = "application/json")
    public ResponseEntity<List<Flight>> getAllFlightByDepartureDateAndOrigin(@RequestParam("departureDate") final Date departureDate, @RequestParam("origin") final String origin) {
        return ResponseEntity.ok(flightServices.getAllFlightByDepartureDateAndOrigin(departureDate, origin));
    }

    @PutMapping(path = "{flightId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> updateFlight(@PathVariable("flightId") final Long flightId, @RequestBody final FlightResource flight) {
        flightServices.update(flightId, flight);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{flightId}", produces = "application/json")
    public ResponseEntity<Void> deleteFlight(@PathVariable("flightId") final Long flightId) {
        flightServices.delete(flightId);
        return ResponseEntity.noContent().build();
    }
}
