package com.lhind.services;

import com.lhind.mapper.UserMapper;
import com.lhind.model.entity.UserDetails;
import com.lhind.model.entity.Users;
import com.lhind.model.resource.UsersResource;
import com.lhind.repository.UserDetailsRepository;
import com.lhind.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserDetailsRepository userDetailsRepository;

    public UserServices(final UserRepository userRepository, UserMapper userMapper, UserDetailsRepository userDetailsRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userDetailsRepository = userDetailsRepository;
    }

    public List<UsersResource> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toResource).toList();
    }

    public Optional<UsersResource> getUserById(Long userId) {
        return userRepository.findById(userId).map(userMapper::toResource);
    }

    public List<UsersResource> getAllUsersByFlight(Long flightId) {
        return userRepository.findByBookingsFlight_Id(flightId).stream().map(userMapper::toResource).toList();
    }

    public Users createUser(Users user, UserDetails userDetails) {
        Users savedUser = userRepository.save(user);
        if (userDetails != null) {
            userDetails.setUser(savedUser);
            userDetailsRepository.save(userDetails);
        }

        return savedUser;
    }

    public void save(final Long userId, final UsersResource userResource) {
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
