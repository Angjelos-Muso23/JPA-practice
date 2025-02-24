package com.lhind.repository.impl;

import com.lhind.model.entity.UserDetails;
import com.lhind.repository.UserDetailsRepository;
import com.lhind.util.UserDetailsQuery;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class UserDetailsRepositoryImpl implements UserDetailsRepository {

    private final EntityManager entityManager;

    public UserDetailsRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<UserDetails> findById(Long id) {
        return Optional.ofNullable(entityManager.find(UserDetails.class, id));
    }

    @Override
    public List<UserDetails> findAll() {
        return entityManager.createQuery(UserDetailsQuery.FIND_ALL_USER_DETAILS, UserDetails.class).getResultList();
    }

    @Override
    public void save(UserDetails userDetails) {
        entityManager.getTransaction().begin();
        if (userDetails.getId() != null) {
            findById(Long.valueOf(userDetails.getId())).ifPresent(existingDetails -> {
                userDetails.setFirstName(userDetails.getFirstName());
                userDetails.setLastName(userDetails.getLastName());
                userDetails.setEmail(userDetails.getEmail());
                userDetails.setPhoneNumber(userDetails.getPhoneNumber());
            });
        } else {
            entityManager.persist(userDetails);
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(UserDetails userDetails) {
        if (userDetails.getId() != null) {
            entityManager.getTransaction().begin();
            findById(userDetails.getId()).ifPresent(entityManager::remove);
            entityManager.getTransaction().commit();
        }
    }
}
