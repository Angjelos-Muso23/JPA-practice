package com.lhind.model.resource;

import com.lhind.model.entity.Flight;
import com.lhind.model.entity.Users;
import com.lhind.model.enums.Status;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.Date;

public record BookingResource(Date bookingDate, Status status, Users user, Flight flight) {

}

