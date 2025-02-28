package com.lhind.repository;

import com.lhind.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    List<Users> findByBookingsFlight_Id(Long flight_id);

    boolean existsByUsername(String username);
}
