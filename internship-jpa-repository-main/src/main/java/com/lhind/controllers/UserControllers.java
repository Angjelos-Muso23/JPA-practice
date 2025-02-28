package com.lhind.controllers;

import com.lhind.model.entity.Flight;
import com.lhind.model.entity.Users;
import com.lhind.model.resource.UsersResource;
import com.lhind.repository.UserRepository;
import com.lhind.services.UserServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lhind.controllers.UserControllers.USERS_BASE_PATH;

@RestController
@RequestMapping(USERS_BASE_PATH)
public class UserControllers {
    static final String USERS_BASE_PATH = "/users";

    private final UserRepository userRepository;
    private final UserServices userServices;

    public UserControllers(final UserRepository userRepository, final UserServices userServices) {
        this.userRepository = userRepository;
        this.userServices = userServices;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<Void> createUser(@RequestBody final Users user) {
        if (userRepository.existsById(user.getId())) {
            userServices.create(user);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok(userServices.getAllUsers());
    }

    @GetMapping(path = "/{userId}", produces = "application/json")
    public ResponseEntity<Users> getUserById(@PathVariable("userId") final Long userId) {
        return userServices.getUserById(userId).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/search", produces = "application/json")
    public ResponseEntity<List<Users>> getAllUsersByFlight(@RequestParam("flightId") final Long flightId) {
        return ResponseEntity.ok(userServices.getAllUsersByFlight(flightId));
    }

    @PutMapping(path = "{userId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> updateUser(@PathVariable("userId") final Long userId, @RequestBody final UsersResource user) {
        userServices.update(userId, user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{userId}", produces = "application/json")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") final Long userId) {
        userServices.delete(userId);
        return ResponseEntity.noContent().build();
    }
}
