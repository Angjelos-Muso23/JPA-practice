package com.lhind.repository.impl;

import com.lhind.model.entity.Flight;
import com.lhind.repository.FlightRepository;
import com.lhind.util.FlightQuery;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class FlightRepositoryImpl implements FlightRepository {

    private final EntityManager entityManager;

    public FlightRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Flight> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Flight.class, id));
    }

    @Override
    public List<Flight> findAll() {
        return entityManager.createQuery(FlightQuery.FIND_ALL_FLIGHTS, Flight.class).getResultList();
    }

    @Override
    public void save(Flight flight) {
        entityManager.getTransaction().begin();
        if (flight.getId() != null) {
            findById(Long.valueOf(flight.getId())).ifPresent(existingflight -> {
                flight.setOrigin(flight.getOrigin());
                flight.setDestination(flight.getDestination());
                flight.setAirline(flight.getAirline());
                flight.setFlightNumber(flight.getFlightNumber());
                flight.setDepartureDate(flight.getDepartureDate());
                flight.setArrivalDate(flight.getArrivalDate());
                flight.setStatus(flight.getStatus());
            });
        } else {
            entityManager.persist(flight);
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(Flight flight) {
        if (flight.getId() != null) {
            entityManager.getTransaction().begin();
            findById(flight.getId()).ifPresent(entityManager::remove);
            entityManager.getTransaction().commit();
        }
    }
}
