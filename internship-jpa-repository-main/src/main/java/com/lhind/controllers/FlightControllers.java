package com.lhind.controllers;

import com.lhind.model.entity.Flight;
import com.lhind.model.resource.FlightResource;
import com.lhind.model.resource.FlightSearchResource;
import com.lhind.model.resource.UsersResource;
import com.lhind.repository.FlightRepository;
import com.lhind.services.FlightServices;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    public ResponseEntity<Void> createFlights(@RequestBody final FlightResource flight) {
        flightServices.create(flight);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<FlightResource>> getAllFlights() {
        return ResponseEntity.ok(flightServices.getAllFlights());
    }

    @GetMapping(path = "/{flightId}", produces = "application/json")
    public ResponseEntity<FlightResource> getFlightById(@PathVariable("flightId") final Long flightId) {
        return flightServices.getFlightById(flightId).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/search", produces = "application/json")
    public ResponseEntity<List<FlightResource>> getAllFlightByDepartureDateAndOrigin(
            @RequestParam("departureDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date departureDate,
            @RequestParam("origin") String origin) {

        return ResponseEntity.ok(flightServices.getAllFlightByDepartureDateAndOrigin(departureDate, origin));
    }

    @PutMapping(path = "/{flightId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> updateFlight(@PathVariable("flightId") final Long flightId, @RequestBody final FlightResource flight) {
        if (!flightRepository.existsById(flightId)) {
            return ResponseEntity.notFound().build();
        }
        flightServices.update(flightId, flight);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{flightId}", produces = "application/json")
    public ResponseEntity<Void> deleteFlight(@PathVariable("flightId") final Long flightId) {
        if (!flightRepository.existsById(flightId)) {
            return ResponseEntity.notFound().build();
        }
        flightServices.delete(flightId);
        return ResponseEntity.noContent().build();
    }
}
