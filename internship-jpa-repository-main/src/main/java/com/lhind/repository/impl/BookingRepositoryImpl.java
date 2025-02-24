package com.lhind.repository.impl;

import com.lhind.model.entity.Booking;
import com.lhind.repository.BookingRepository;
import com.lhind.util.BookingQuery;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class BookingRepositoryImpl implements BookingRepository {

    private final EntityManager entityManager;

    public BookingRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Booking> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Booking.class, id));
    }

    @Override
    public List<Booking> findAll() {
        return entityManager.createQuery(BookingQuery.FIND_ALL_BOOKINGS, Booking.class).getResultList();
    }

    @Override
    public void save(Booking booking) {
        entityManager.getTransaction().begin();
        if (booking.getId() != null) {
            findById(Long.valueOf(booking.getId())).ifPresent(existingBooking -> {
                booking.setBookingDate(booking.getBookingDate());
                booking.setStatus(booking.getStatus());
            });
        } else {
            entityManager.persist(booking);
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(Booking booking) {
        if (booking.getId() != null) {
            entityManager.getTransaction().begin();
            findById(booking.getId()).ifPresent(entityManager::remove);
            entityManager.getTransaction().commit();
        }
    }
}
