package com.lhind.mapper;

import com.lhind.model.entity.UserDetails;
import com.lhind.model.entity.Users;
import com.lhind.model.resource.UserDetailsResource;
import com.lhind.model.resource.UsersResource;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static Users toEntity(UsersResource resource) {
        if (resource == null) return null;

        Users user = new Users();
        user.setUsername(resource.username());
        user.setPassword(resource.password());
        user.setRole(resource.role());

        if (resource.userDetailsResource() != null) {
            user.setUserDetails(toUserDetailsEntity(resource.userDetailsResource()));
        }

        return user;
    }

    private static UserDetails toUserDetailsEntity(UserDetailsResource resource) {
        if (resource == null) return null;

        UserDetails userDetails = new UserDetails();
        userDetails.setFirstName(resource.firstName());
        userDetails.setLastName(resource.lastName());
        userDetails.setEmail(resource.email());
        userDetails.setPhoneNumber(resource.phoneNumber());

        return userDetails;
    }

    public static void updateUserFromResource(UsersResource resource, Users user) {
        if (resource == null || user == null) return;

        if (resource.username() != null) user.setUsername(resource.username());
        if (resource.password() != null) user.setPassword(resource.password());
        if (resource.role() != null) user.setRole(resource.role());
        if (resource.userDetailsResource() != null) {
            updateUserDetailsFromResource(resource.userDetailsResource(), user.getUserDetails());
        }
    }

    public static void updateUserDetailsFromResource(UserDetailsResource resource, UserDetails userDetails) {
        if (resource == null || userDetails == null) return;

        if (resource.firstName() != null) userDetails.setFirstName(resource.firstName());
        if (resource.lastName() != null) userDetails.setLastName(resource.lastName());
        if (resource.email() != null) userDetails.setEmail(resource.email());
        if (resource.phoneNumber() != null) userDetails.setPhoneNumber(resource.phoneNumber());
    }
}
