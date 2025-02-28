package com.lhind.model.resource;

import jakarta.persistence.*;

import java.util.List;

public record UsersResource(String username, String password, String role, UserDetailsResource userDetailsResource) {

}

