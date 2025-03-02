package com.lhind.services;

import com.lhind.mapper.FlightMapper;
import com.lhind.model.entity.Flight;
import com.lhind.model.resource.FlightResource;
import com.lhind.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FlightServices {
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    public FlightServices(final FlightRepository flightRepository, final FlightMapper flightMapper) {
        this.flightRepository = flightRepository;
        this.flightMapper = flightMapper;
    }

    public List<FlightResource> getAllFlights() {
        return flightRepository.findAll().stream().map(flightMapper::toResource).toList();
    }

    public Optional<FlightResource> getFlightById(Long flightId) {
        return flightRepository.findById(flightId).map(flightMapper::toResource);
    }

    public List<FlightResource> getAllFlightByDepartureDateAndOrigin(Date departureDate, String origin) {
        return flightRepository.findByDepartureDateAndOrigin(departureDate, origin).stream().map(flightMapper::toResource).toList();
    }

    public void create(FlightResource resource) {
        Flight flight = flightMapper.toEntity(resource);
        flightRepository.save(flight);
    }

    public void update(Long flightId, FlightResource flightResource) {
        flightRepository.findById(flightId).ifPresent(flight -> {
            flightMapper.updateFlightFromResource(flightResource, flight);
            flightRepository.save(flight);
        });
    }

    public  void delete(Long flightId) {
        if (flightRepository.existsById(flightId)) {
            flightRepository.deleteById(flightId);
        }
    }
}
