package com.lhind.controllers;

import com.lhind.model.entity.UserDetails;
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
    public ResponseEntity<Users> createUser(@RequestBody UsersResource request) {
        Users user = new Users();
        user.setUsername(request.username());
        user.setPassword(request.password());
        user.setRole(request.role());

        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(request.userDetails().email());
        userDetails.setFirstName(request.userDetails().firstName());
        userDetails.setLastName(request.userDetails().lastName());
        userDetails.setPhoneNumber(request.userDetails().phoneNumber());

        Users savedUser = userServices.createUser(user, userDetails);

        return ResponseEntity.ok(savedUser);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<UsersResource>> getAllUsers() {
        return ResponseEntity.ok(userServices.getAllUsers());
    }

    @GetMapping(path = "/{userId}", produces = "application/json")
    public ResponseEntity<UsersResource> getUserById(@PathVariable("userId") final Long userId) {
        return userServices.getUserById(userId).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/search", produces = "application/json")
    public ResponseEntity<List<UsersResource>> getAllUsersByFlight(@RequestParam("flightId") final Long flightId) {
        return ResponseEntity.ok(userServices.getAllUsersByFlight(flightId));
    }

    @PutMapping(path = "/{userId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> updateEmployee(@PathVariable("userId") final Long userId, @RequestBody final UsersResource usersResource) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }
        userServices.save(userId, usersResource);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{userId}", produces = "application/json")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") final Long userId) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }
        userServices.delete(userId);
        return ResponseEntity.noContent().build();
    }
}
