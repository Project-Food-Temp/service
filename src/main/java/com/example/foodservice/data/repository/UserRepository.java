package com.example.foodservice.data.repository;

import com.example.foodservice.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findUserByEmail(String email);

    boolean existsUserByEmail(String email);
    Optional<User> findUserByUsername(String username);
}
