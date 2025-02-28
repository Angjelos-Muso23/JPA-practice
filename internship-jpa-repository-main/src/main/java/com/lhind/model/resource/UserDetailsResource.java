package com.lhind.model.resource;


import jakarta.persistence.*;

public record UserDetailsResource(String firstName, String lastName, String email, String phoneNumber) {

}
