package com.lhind.mapper;

import com.lhind.model.entity.UserDetails;
import com.lhind.model.entity.Users;
import com.lhind.model.resource.UserDetailsResource;
import com.lhind.model.resource.UsersResource;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UsersResource toResource(Users user) {
        if (user == null) return null;

        return new UsersResource(
            user.getUsername(),
            user.getPassword(),
            user.getRole(),
            toResource(user.getUserDetails())
        );
    }

    public UserDetailsResource toResource(UserDetails user) {
        if (user == null) return null;

        return new UserDetailsResource(
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getPhoneNumber()
        );
    }

    public void updateUserFromResource(UsersResource resource, Users user) {
        if (resource == null || user == null) return;

        if (resource.username() != null) user.setUsername(resource.username());
        if (resource.password() != null) user.setPassword(resource.password());
        if (resource.role() != null) user.setRole(resource.role());
        if (resource.userDetails() != null) {
            updateUserDetailsFromResource(resource.userDetails(), user.getUserDetails());
        }
    }

    public void updateUserDetailsFromResource(UserDetailsResource resource, UserDetails userDetails) {
        if (resource == null || userDetails == null) return;

        if (resource.firstName() != null) userDetails.setFirstName(resource.firstName());
        if (resource.lastName() != null) userDetails.setLastName(resource.lastName());
        if (resource.email() != null) userDetails.setEmail(resource.email());
        if (resource.phoneNumber() != null) userDetails.setPhoneNumber(resource.phoneNumber());
    }
}
