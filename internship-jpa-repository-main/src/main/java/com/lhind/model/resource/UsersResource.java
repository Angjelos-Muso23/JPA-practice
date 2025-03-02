package com.lhind.model.resource;

public record UsersResource(String username, String password, String role, UserDetailsResource userDetails) {

}

