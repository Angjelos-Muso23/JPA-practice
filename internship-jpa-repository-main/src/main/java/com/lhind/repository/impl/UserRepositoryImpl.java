package com.lhind.repository.impl;

import com.lhind.model.entity.User;
import com.lhind.repository.UserRepository;
import com.lhind.util.UserQuery;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    private final EntityManager entityManager;

    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery(UserQuery.FIND_ALL_USERS, User.class).getResultList();
    }

    @Override
    public void save(User user) {
        entityManager.getTransaction().begin();
        if (user.getId() != null) {
            findById(Long.valueOf(user.getId())).ifPresent(existingDetails -> {
                user.setUsername(user.getUsername());
                user.setRole(user.getRole());
                user.setPassword(user.getPassword());
            });
        } else {
            entityManager.persist(user);
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(User user) {
        if (user.getId() != null) {
            entityManager.getTransaction().begin();
            findById(user.getId()).ifPresent(entityManager::remove);
            entityManager.getTransaction().commit();
        }
    }
}
