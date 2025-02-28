package com.lhind.services;

import com.lhind.mapper.UserMapper;
import com.lhind.model.entity.Flight;
import com.lhind.model.entity.Users;
import com.lhind.model.resource.UsersResource;
import com.lhind.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServices(final UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll().stream().toList();
    }

    public Optional<Users> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public List<Users> getAllUsersByFlight(Long flightId) {
        return userRepository.findByBookingsFlight_Id(flightId).stream().toList();
    }

    public void create(Users user) {
        userRepository.save(user);
    }

    public void update(Long userId, UsersResource userResource) {
        userRepository.findById(userId).ifPresent(user -> {
            userMapper.updateUserFromResource(userResource, user);
            userRepository.save(user);
        });
    }

    public  void delete(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        }
    }
}
